package com.alvarengacarlos.www.control;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RequestBuilderTest {

    private RequestBuilder requestBuilder;

    @BeforeEach
    void beforeEach() {
        requestBuilder = new RequestBuilder();
    }

    @Test
    void shouldCreateARequestWithoutNullFields() {
        RequestBuilder.Request request = requestBuilder.build();

        Assertions.assertEquals("GET", request.httpMethod);
        Assertions.assertEquals("/", request.path);
        Assertions.assertEquals(Map.of(), request.headers);
        Assertions.assertEquals(Map.of(), request.pathParameters);
        Assertions.assertEquals(Map.of(), request.queryStringParameters);
        Assertions.assertEquals("", request.body);
        Assertions.assertEquals(false, request.isBase64Encoded);
    }

    @Nested
    class WithHttpMethod {

        @Test
        void shouldCreateARequestWithoutANewHttpMethod() {
            RequestBuilder.Request request = requestBuilder
                    .withHttpMethod(null)
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewHttpMethod() {
            RequestBuilder.Request request = requestBuilder
                    .withHttpMethod("POST")
                    .build();

            Assertions.assertEquals("POST", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }
    }

    @Nested
    class WithPath {

        @Test
        void shouldCreateARequestWithoutANewPath() {
            RequestBuilder.Request request = requestBuilder
                    .withPath(null)
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewPath() {
            RequestBuilder.Request request = requestBuilder
                    .withPath("/users")
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/users", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }
    }

    @Nested
    class WithHeaders {

        @Test
        void shouldCreateARequestWithoutANewHeaders() {
            RequestBuilder.Request request = requestBuilder
                    .withHeaders(null)
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewHeaders() {
            RequestBuilder.Request request = requestBuilder
                    .withHeaders(Map.of("Content-Type", "application/json"))
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(
                    Map.of("Content-Type", "application/json"),
                    request.headers
            );
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }
    }

    @Nested
    class WithPathParameters {

        @Test
        void shouldCreateARequestWithoutANewPathParameters() {
            RequestBuilder.Request request = requestBuilder
                    .withPathParameters(null)
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewPathParameters() {
            RequestBuilder.Request request = requestBuilder
                    .withPathParameters(Map.of("userId", "1"))
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(
                    Map.of("userId", "1"),
                    request.pathParameters
            );
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }
    }

    @Nested
    class WithQueryStringParameters {

        @Test
        void shouldCreateARequestWithoutANewQueryStringParameters() {
            RequestBuilder.Request request = requestBuilder
                    .withQueryStringParameters(null)
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewQueryStringParameters() {
            RequestBuilder.Request request = requestBuilder
                    .withQueryStringParameters(Map.of("page", "1"))
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(
                    Map.of("page", "1"),
                    request.queryStringParameters
            );
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }
    }

    @Nested
    class WithBody {

        @Test
        void shouldCreateARequestWithoutANewBody() {
            RequestBuilder.Request request = requestBuilder
                    .withBody(null)
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewBody() {
            RequestBuilder.Request request = requestBuilder
                    .withBody("{\"name\": \"John Doe\"}")
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("{\"name\": \"John Doe\"}", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }
    }

    @Nested
    class WithIsBase64Encoded {

        @Test
        void shouldCreateARequestWithoutANewBase64Encoded() {
            RequestBuilder.Request request = requestBuilder
                    .withIsBase64Encoded(null)
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(false, request.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewBase64Encoded() {
            RequestBuilder.Request request = requestBuilder
                    .withIsBase64Encoded(true)
                    .build();

            Assertions.assertEquals("GET", request.httpMethod);
            Assertions.assertEquals("/", request.path);
            Assertions.assertEquals(Map.of(), request.headers);
            Assertions.assertEquals(Map.of(), request.pathParameters);
            Assertions.assertEquals(Map.of(), request.queryStringParameters);
            Assertions.assertEquals("", request.body);
            Assertions.assertEquals(true, request.isBase64Encoded);
        }
    }
}
