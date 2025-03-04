package com.alvarengacarlos.www.control;

import java.util.Collections;
import java.util.Map;

public class RequestBuilder {

    private String httpMethod = "GET";
    private String path = "/";
    private Map<String, String> headers = Map.of();
    private Map<String, String> pathParameters = Map.of();
    private Map<String, String> queryStringParameters = Map.of();
    private String body = "";
    private Boolean isBase64Encoded = false;

    public class Request {

        public final String httpMethod;
        public final String path;
        public final Map<String, String> headers;
        public final Map<String, String> pathParameters;
        public final Map<String, String> queryStringParameters;
        public final String body;
        public final Boolean isBase64Encoded;

        private Request(
                String httpMethod,
                String path,
                Map<String, String> headers,
                Map<String, String> pathParameters,
                Map<String, String> queryStringParameters,
                String body,
                Boolean isBase64Encoded
        ) {
            this.httpMethod = httpMethod;
            this.path = path;
            this.headers = headers;
            this.pathParameters = pathParameters;
            this.queryStringParameters = queryStringParameters;
            this.body = body;
            this.isBase64Encoded = isBase64Encoded;
        }
    }

    public RequestBuilder withHttpMethod(String httpMethod) {
        if (httpMethod != null) {
            this.httpMethod = httpMethod;
        }
        return this;
    }

    public RequestBuilder withPath(String path) {
        if (path != null) {
            this.path = path;
        }
        return this;
    }

    public RequestBuilder withHeaders(Map<String, String> headers) {
        if (headers != null) {
            this.headers = Collections.unmodifiableMap(headers);
        }
        return this;
    }

    public RequestBuilder withPathParameters(
            Map<String, String> pathParameters
    ) {
        if (pathParameters != null) {
            this.pathParameters = Collections.unmodifiableMap(pathParameters);
        }
        return this;
    }

    public RequestBuilder withQueryStringParameters(
            Map<String, String> queryStringParameters
    ) {
        if (queryStringParameters != null) {
            this.queryStringParameters = Collections.unmodifiableMap(
                    queryStringParameters
            );
        }
        return this;
    }

    public RequestBuilder withBody(String body) {
        if (body != null) {
            this.body = body;
        }
        return this;
    }

    public RequestBuilder withIsBase64Encoded(Boolean isBase64Encoded) {
        if (isBase64Encoded != null) {
            this.isBase64Encoded = isBase64Encoded;
        }
        return this;
    }

    public Request build() {
        return new Request(
                httpMethod,
                path,
                headers,
                pathParameters,
                queryStringParameters,
                body,
                isBase64Encoded
        );
    }
}
