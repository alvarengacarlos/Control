package com.alvarengacarlos.www.control;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    private final Map<String, RouteDefinition> routes = new HashMap<>();

    private class RouteDefinition {

        public final String path;
        public final Function<
            RequestBuilder.Request,
            ResponseBuilder.Response
        > executor;

        public RouteDefinition(
            String path,
            Function<RequestBuilder.Request, ResponseBuilder.Response> executor
        ) {
            this.path = path;
            this.executor = executor;
        }
    }

    public void get(
        String path,
        Function<RequestBuilder.Request, ResponseBuilder.Response> executor
    ) {
        createRoute(HttpMethod.GET, path, executor);
    }

    private void createRoute(
        HttpMethod httpMethod,
        String path,
        Function<RequestBuilder.Request, ResponseBuilder.Response> executor
    ) {
        routes.put(
            formatPath(httpMethod, path),
            new RouteDefinition(path, executor)
        );
    }

    private String formatPath(HttpMethod httpMethod, String path) {
        StringBuilder formattedPath = new StringBuilder();
        formattedPath.append("^");
        formattedPath.append(httpMethod);
        formattedPath.append(" ");
        formattedPath.append(
            path
                .toLowerCase()
                .trim()
                .replaceAll(
                    "\\{[^}]*\\}",
                    Matcher.quoteReplacement("([a-zA-Z0-9\\-]+)")
                )
        );
        formattedPath.append("$");

        return formattedPath.toString();
    }

    public void post(
        String path,
        Function<RequestBuilder.Request, ResponseBuilder.Response> executor
    ) {
        createRoute(HttpMethod.POST, path, executor);
    }

    public void put(
        String path,
        Function<RequestBuilder.Request, ResponseBuilder.Response> executor
    ) {
        createRoute(HttpMethod.PUT, path, executor);
    }

    public void patch(
        String path,
        Function<RequestBuilder.Request, ResponseBuilder.Response> executor
    ) {
        createRoute(HttpMethod.PATCH, path, executor);
    }

    public void delete(
        String path,
        Function<RequestBuilder.Request, ResponseBuilder.Response> executor
    ) {
        createRoute(HttpMethod.DELETE, path, executor);
    }

    public APIGatewayProxyResponseEvent dispatch(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent
    ) {
        String resource = apiGatewayProxyRequestEvent.getResource();
        String version = apiGatewayProxyRequestEvent.getVersion();
        String httpMethod = apiGatewayProxyRequestEvent.getHttpMethod();
        String path = apiGatewayProxyRequestEvent.getPath();
        Map<String, String> headers = apiGatewayProxyRequestEvent.getHeaders();
        Map<String, String> queryStringParameters =
            apiGatewayProxyRequestEvent.getQueryStringParameters();
        String body = apiGatewayProxyRequestEvent.getBody();
        Boolean isBase64Encoded =
            apiGatewayProxyRequestEvent.getIsBase64Encoded();

        if (!isIntegrationAllowed(resource) || !isVersionAllowed(version)) {
            return new APIGatewayProxyResponseEvent()
                .withHeaders(Map.of())
                .withStatusCode(502)
                .withBody("")
                .withIsBase64Encoded(false);
        }

        if (!isMethodAllowed(httpMethod)) {
            return new APIGatewayProxyResponseEvent()
                .withHeaders(Map.of())
                .withStatusCode(405)
                .withBody("")
                .withIsBase64Encoded(false);
        }

        Optional<String> optional = routes
            .keySet()
            .stream()
            .filter(formattedPath -> {
                Pattern pattern = Pattern.compile(formattedPath);
                Matcher matcher = pattern.matcher(httpMethod + " " + path);
                return matcher.find();
            })
            .findFirst();

        if (optional.isEmpty()) {
            return new APIGatewayProxyResponseEvent()
                .withHeaders(Map.of())
                .withStatusCode(404)
                .withBody("")
                .withIsBase64Encoded(false);
        }

        RouteDefinition routeDefinition = routes.get(optional.get());

        RequestBuilder.Request request = new RequestBuilder()
            .withHttpMethod(httpMethod)
            .withPath(path)
            .withHeaders(headers)
            .withPathParameters(mountPathParameters(path, routeDefinition.path))
            .withQueryStringParameters(queryStringParameters)
            .withBody(body)
            .withIsBase64Encoded(isBase64Encoded)
            .build();
        ResponseBuilder.Response response = routeDefinition.executor.apply(
            request
        );

        return new APIGatewayProxyResponseEvent()
            .withIsBase64Encoded(response.isBase64Encoded)
            .withStatusCode(response.statusCode)
            .withHeaders(response.headers)
            .withBody(response.body);
    }

    private Boolean isIntegrationAllowed(String resource) {
        return resource.equals("/{proxy+}") ? true : false;
    }

    private Boolean isVersionAllowed(String version) {
        return version.equals("1.0") ? true : false;
    }

    private Boolean isMethodAllowed(String httpMethod) {
        try {
            HttpMethod.valueOf(httpMethod);
            return true;
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    private Map<String, String> mountPathParameters(
        String path,
        String routeDefinitionPath
    ) {
        List<String> splitRouteDefinitionPath = Arrays.stream(
            routeDefinitionPath.split("/")
        ).toList();
        List<String> splitPath = Arrays.stream(path.split("/")).toList();

        List<String> keys = splitRouteDefinitionPath
            .stream()
            .filter(str -> !splitPath.contains(str))
            .map(str -> str.replaceAll("[{}]", ""))
            .toList();
        List<String> values = splitPath
            .stream()
            .filter(str -> !splitRouteDefinitionPath.contains(str))
            .toList();

        Map<String, String> pathParameters = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            pathParameters.put(keys.get(i), values.get(i));
        }

        return pathParameters;
    }
}
