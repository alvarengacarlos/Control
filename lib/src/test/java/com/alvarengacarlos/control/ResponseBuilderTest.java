package com.alvarengacarlos.control;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ResponseBuilderTest {

    private ResponseBuilder responseBuilder;

    @BeforeEach
    void beforeEach() {
        responseBuilder = new ResponseBuilder();
    }

    @Test
    void shouldCreateARequestWithoutNullFields() {
        ResponseBuilder.Response response = responseBuilder.build();

        Assertions.assertEquals(200, response.statusCode);
        Assertions.assertEquals(Map.of(), response.headers);
        Assertions.assertEquals("", response.body);
        Assertions.assertEquals(false, response.isBase64Encoded);
    }

    @Nested
    class WithStatusCode {

        @Test
        void shouldCreateARequestWithoutANewStatusCode() {
            ResponseBuilder.Response response = responseBuilder
                .withStatusCode(null)
                .build();

            Assertions.assertEquals(200, response.statusCode);
            Assertions.assertEquals(Map.of(), response.headers);
            Assertions.assertEquals("", response.body);
            Assertions.assertEquals(false, response.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewStatusCode() {
            ResponseBuilder.Response response = responseBuilder
                .withStatusCode(201)
                .build();

            Assertions.assertEquals(201, response.statusCode);
            Assertions.assertEquals(Map.of(), response.headers);
            Assertions.assertEquals("", response.body);
            Assertions.assertEquals(false, response.isBase64Encoded);
        }
    }

    @Nested
    class WithHeaders {

        @Test
        void shouldCreateARequestWithoutANewHeaders() {
            ResponseBuilder.Response response = responseBuilder
                .withHeaders(null)
                .build();

            Assertions.assertEquals(200, response.statusCode);
            Assertions.assertEquals(Map.of(), response.headers);
            Assertions.assertEquals("", response.body);
            Assertions.assertEquals(false, response.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewHeaders() {
            ResponseBuilder.Response response = responseBuilder
                .withHeaders(Map.of("Content-Type", "application/json"))
                .build();

            Assertions.assertEquals(200, response.statusCode);
            Assertions.assertEquals(
                Map.of("Content-Type", "application/json"),
                response.headers
            );
            Assertions.assertEquals("", response.body);
            Assertions.assertEquals(false, response.isBase64Encoded);
        }
    }

    @Nested
    class WithBody {

        @Test
        void shouldCreateARequestWithoutANewBody() {
            ResponseBuilder.Response response = responseBuilder
                .withBody(null)
                .build();

            Assertions.assertEquals(200, response.statusCode);
            Assertions.assertEquals(Map.of(), response.headers);
            Assertions.assertEquals("", response.body);
            Assertions.assertEquals(false, response.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewBody() {
            ResponseBuilder.Response response = responseBuilder
                .withBody("\"name\": \"John Doe\"")
                .build();

            Assertions.assertEquals(200, response.statusCode);
            Assertions.assertEquals(Map.of(), response.headers);
            Assertions.assertEquals("\"name\": \"John Doe\"", response.body);
            Assertions.assertEquals(false, response.isBase64Encoded);
        }
    }

    @Nested
    class WithIsBase64Encoded {

        @Test
        void shouldCreateARequestWithoutANewBase64Encoded() {
            ResponseBuilder.Response response = responseBuilder
                .withIsBase64Encoded(null)
                .build();

            Assertions.assertEquals(200, response.statusCode);
            Assertions.assertEquals(Map.of(), response.headers);
            Assertions.assertEquals("", response.body);
            Assertions.assertEquals(false, response.isBase64Encoded);
        }

        @Test
        void shouldCreateARequestWithANewBase64Encoded() {
            ResponseBuilder.Response response = responseBuilder
                .withIsBase64Encoded(true)
                .build();

            Assertions.assertEquals(200, response.statusCode);
            Assertions.assertEquals(Map.of(), response.headers);
            Assertions.assertEquals("", response.body);
            Assertions.assertEquals(true, response.isBase64Encoded);
        }
    }
}
