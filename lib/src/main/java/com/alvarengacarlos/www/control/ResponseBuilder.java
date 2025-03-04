package com.alvarengacarlos.www.control;

import java.util.Collections;
import java.util.Map;

public class ResponseBuilder {

    private Integer statusCode = 200;
    private Map<String, String> headers = Map.of();
    private String body = "";
    private Boolean isBase64Encoded = false;

    public class Response {

        public final Integer statusCode;
        public final Map<String, String> headers;
        public final String body;
        public final Boolean isBase64Encoded;

        private Response(
                Integer statusCode,
                Map<String, String> headers,
                String body,
                Boolean isBase64Encoded
        ) {
            this.statusCode = statusCode;
            this.headers = headers;
            this.body = body;
            this.isBase64Encoded = isBase64Encoded;
        }
    }

    public ResponseBuilder withStatusCode(Integer statusCode) {
        if (statusCode != null) {
            this.statusCode = statusCode;
        }
        return this;
    }

    public ResponseBuilder withHeaders(Map<String, String> headers) {
        if (headers != null) {
            this.headers = Collections.unmodifiableMap(headers);
        }
        return this;
    }

    public ResponseBuilder withBody(String body) {
        if (body != null) {
            this.body = body;
        }
        return this;
    }

    public ResponseBuilder withIsBase64Encoded(Boolean isBase64Encoded) {
        if (isBase64Encoded != null) {
            this.isBase64Encoded = isBase64Encoded;
        }
        return this;
    }

    public Response build() {
        return new Response(statusCode, headers, body, isBase64Encoded);
    }
}
