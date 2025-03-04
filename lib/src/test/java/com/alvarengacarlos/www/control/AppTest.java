package com.alvarengacarlos.www.control;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class AppTest {

    private App app;
    private APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent;

    @BeforeEach
    void beforeEach() {
        app = new App();
        apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent()
                .withResource("/{proxy+}")
                .withVersion("1.0");
    }

    @Nested
    class ShouldCreateARoute {

        @ParameterizedTest
        @MethodSource("pathProvider")
        void whenHttpMethodIsGet(String path) {
            Assertions.assertDoesNotThrow(()
                    -> app.get(path, request -> new ResponseBuilder().build())
            );
        }

        static Stream<String> pathProvider() {
            return Stream.of(
                    "/",
                    "/users",
                    "/users/{userId}",
                    "/users/computers/{computerId}"
            );
        }

        @ParameterizedTest
        @MethodSource("pathProvider")
        void whenHttpMethodIsPost(String path) {
            Assertions.assertDoesNotThrow(()
                    -> app.post(path, request -> new ResponseBuilder().build())
            );
        }

        @ParameterizedTest
        @MethodSource("pathProvider")
        void whenHttpMethodIsPut(String path) {
            Assertions.assertDoesNotThrow(()
                    -> app.put(path, request -> new ResponseBuilder().build())
            );
        }

        @ParameterizedTest
        @MethodSource("pathProvider")
        void whenHttpMethodIsPatch(String path) {
            Assertions.assertDoesNotThrow(()
                    -> app.patch(path, request -> new ResponseBuilder().build())
            );
        }

        @ParameterizedTest
        @MethodSource("pathProvider")
        void whenHttpMethodIsDelete(String path) {
            Assertions.assertDoesNotThrow(()
                    -> app.delete(path, request -> new ResponseBuilder().build())
            );
        }
    }

    @Nested
    class ShouldReturnApiGatewayProxyRequestEvent {

        @Nested
        class With502StatusCode {

            @Test
            void whenIntegrationIsNotAllowed() {
                apiGatewayProxyRequestEvent.withResource("/");

                APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                        = app.dispatch(apiGatewayProxyRequestEvent);

                Assertions.assertEquals(
                        Map.of(),
                        apiGatewayProxyResponseEvent.getHeaders()
                );
                Assertions.assertEquals(
                        502,
                        apiGatewayProxyResponseEvent.getStatusCode()
                );
                Assertions.assertEquals(
                        "",
                        apiGatewayProxyResponseEvent.getBody()
                );
                Assertions.assertFalse(
                        apiGatewayProxyResponseEvent.getIsBase64Encoded()
                );
            }

            @Test
            void whenVersionIsNotAllowed() {
                apiGatewayProxyRequestEvent.withVersion("2.0");

                APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                        = app.dispatch(apiGatewayProxyRequestEvent);

                Assertions.assertEquals(
                        Map.of(),
                        apiGatewayProxyResponseEvent.getHeaders()
                );
                Assertions.assertEquals(
                        502,
                        apiGatewayProxyResponseEvent.getStatusCode()
                );
                Assertions.assertEquals(
                        "",
                        apiGatewayProxyResponseEvent.getBody()
                );
                Assertions.assertFalse(
                        apiGatewayProxyResponseEvent.getIsBase64Encoded()
                );
            }
        }

        @ParameterizedTest
        @ValueSource(strings = {"OPTIONS", "TRACE", "HEAD", "CONNECT"})
        void with405StatusCode(String httpMethod) {
            apiGatewayProxyRequestEvent.withHttpMethod(httpMethod);

            APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                    = app.dispatch(apiGatewayProxyRequestEvent);

            Assertions.assertEquals(
                    Map.of(),
                    apiGatewayProxyResponseEvent.getHeaders()
            );
            Assertions.assertEquals(
                    405,
                    apiGatewayProxyResponseEvent.getStatusCode()
            );
            Assertions.assertEquals("", apiGatewayProxyResponseEvent.getBody());
            Assertions.assertFalse(
                    apiGatewayProxyResponseEvent.getIsBase64Encoded()
            );
        }

        @Test
        void with404StatusCode() {
            apiGatewayProxyRequestEvent.withHttpMethod("GET").withPath("/");

            APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                    = app.dispatch(apiGatewayProxyRequestEvent);

            Assertions.assertEquals(
                    Map.of(),
                    apiGatewayProxyResponseEvent.getHeaders()
            );
            Assertions.assertEquals(
                    404,
                    apiGatewayProxyResponseEvent.getStatusCode()
            );
            Assertions.assertEquals("", apiGatewayProxyResponseEvent.getBody());
            Assertions.assertFalse(
                    apiGatewayProxyResponseEvent.getIsBase64Encoded()
            );
        }

        @Nested
        class with200StatusCode {

            @Test
            void whenExistOneRoute() {
                apiGatewayProxyRequestEvent.withHttpMethod("GET").withPath("/");
                app.get("/", request -> new ResponseBuilder().build());

                APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                        = app.dispatch(apiGatewayProxyRequestEvent);

                Assertions.assertEquals(
                        Map.of(),
                        apiGatewayProxyResponseEvent.getHeaders()
                );
                Assertions.assertEquals(
                        200,
                        apiGatewayProxyResponseEvent.getStatusCode()
                );
                Assertions.assertEquals(
                        "",
                        apiGatewayProxyResponseEvent.getBody()
                );
                Assertions.assertFalse(
                        apiGatewayProxyResponseEvent.getIsBase64Encoded()
                );
            }

            @Test
            void whenExistTwoRoutes() {
                apiGatewayProxyRequestEvent
                        .withHttpMethod("GET")
                        .withPath("/users");
                app.get("/", request -> new ResponseBuilder().build());
                app.get("/users", request
                        -> new ResponseBuilder().withBody("users").build()
                );

                APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                        = app.dispatch(apiGatewayProxyRequestEvent);

                Assertions.assertEquals(
                        Map.of(),
                        apiGatewayProxyResponseEvent.getHeaders()
                );
                Assertions.assertEquals(
                        200,
                        apiGatewayProxyResponseEvent.getStatusCode()
                );
                Assertions.assertEquals(
                        "users",
                        apiGatewayProxyResponseEvent.getBody()
                );
                Assertions.assertFalse(
                        apiGatewayProxyResponseEvent.getIsBase64Encoded()
                );
            }

            @Test
            void whenExistThreeRoutes() {
                apiGatewayProxyRequestEvent
                        .withHttpMethod("GET")
                        .withPath("/users/1");
                app.get("/", request -> new ResponseBuilder().build());
                app.get("/users", request
                        -> new ResponseBuilder().withBody("users").build()
                );
                app.get("/users/{userId}", request
                        -> new ResponseBuilder().withBody("user 1").build()
                );

                APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                        = app.dispatch(apiGatewayProxyRequestEvent);

                Assertions.assertEquals(
                        Map.of(),
                        apiGatewayProxyResponseEvent.getHeaders()
                );
                Assertions.assertEquals(
                        200,
                        apiGatewayProxyResponseEvent.getStatusCode()
                );
                Assertions.assertEquals(
                        "user 1",
                        apiGatewayProxyResponseEvent.getBody()
                );
                Assertions.assertFalse(
                        apiGatewayProxyResponseEvent.getIsBase64Encoded()
                );
            }

            @Test
            void whenExistFourRoutes() {
                apiGatewayProxyRequestEvent
                        .withHttpMethod("GET")
                        .withPath("/users/computers/1");
                app.get("/", request -> new ResponseBuilder().build());
                app.get("/users", request
                        -> new ResponseBuilder().withBody("users").build()
                );
                app.get("/users/{userId}", request
                        -> new ResponseBuilder().withBody("user 1").build()
                );
                app.get("/users/computers/{computerId}", request
                        -> new ResponseBuilder().withBody("computer 1").build()
                );

                APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                        = app.dispatch(apiGatewayProxyRequestEvent);

                Assertions.assertEquals(
                        Map.of(),
                        apiGatewayProxyResponseEvent.getHeaders()
                );
                Assertions.assertEquals(
                        200,
                        apiGatewayProxyResponseEvent.getStatusCode()
                );
                Assertions.assertEquals(
                        "computer 1",
                        apiGatewayProxyResponseEvent.getBody()
                );
                Assertions.assertFalse(
                        apiGatewayProxyResponseEvent.getIsBase64Encoded()
                );
            }

            @ParameterizedTest
            @MethodSource("methodPathAndBodyProvider")
            void whenThereAreGetPostPutPatchAndDeleteRoutes(
                    String httpMethod,
                    String path,
                    String body
            ) {
                apiGatewayProxyRequestEvent
                        .withHttpMethod(httpMethod)
                        .withPath(path);
                app.get("/", request
                        -> new ResponseBuilder().withBody("homepage").build()
                );
                app.post("/users", request
                        -> new ResponseBuilder().withBody("new user").build()
                );
                app.get("/users", request
                        -> new ResponseBuilder().withBody("all users").build()
                );
                app.get("/users/{userId}", request
                        -> new ResponseBuilder().withBody("one user").build()
                );
                app.put("/users/{userId}", request
                        -> new ResponseBuilder().withBody("all fields").build()
                );
                app.patch("/users/{userId}", request
                        -> new ResponseBuilder().withBody("one field").build()
                );
                app.delete("/users/{userId}", request
                        -> new ResponseBuilder().withBody("remove user").build()
                );

                APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent
                        = app.dispatch(apiGatewayProxyRequestEvent);

                Assertions.assertEquals(
                        Map.of(),
                        apiGatewayProxyResponseEvent.getHeaders()
                );
                Assertions.assertEquals(
                        200,
                        apiGatewayProxyResponseEvent.getStatusCode()
                );
                Assertions.assertEquals(
                        body,
                        apiGatewayProxyResponseEvent.getBody()
                );
                Assertions.assertFalse(
                        apiGatewayProxyResponseEvent.getIsBase64Encoded()
                );
            }

            static Stream<Arguments> methodPathAndBodyProvider() {
                return Stream.of(
                        Arguments.arguments("GET", "/", "homepage"),
                        Arguments.arguments("POST", "/users", "new user"),
                        Arguments.arguments("GET", "/users", "all users"),
                        Arguments.arguments("GET", "/users/1", "one user"),
                        Arguments.arguments("PUT", "/users/1", "all fields"),
                        Arguments.arguments("PATCH", "/users/1", "one field"),
                        Arguments.arguments("DELETE", "/users/1", "remove user")
                );
            }
        }
    }
}
