package com.helloworld;

import com.alvarengacarlos.control.App;
import com.alvarengacarlos.control.ResponseBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.Map;

public class Handler
    implements
        RequestHandler<
            APIGatewayProxyRequestEvent,
            APIGatewayProxyResponseEvent
        > {

    private final App app;

    public Handler() {
        app = new App();
        Map<String, String> defaultHeaders = Map.of(
            "Content-Type",
            "text/plain"
        );
        app.get("/hello", request ->
            //Some logic...
            new ResponseBuilder()
                .withHeaders(defaultHeaders)
                .withStatusCode(200)
                .withBody("Hello, World!")
                .build()
        );
    }

    public APIGatewayProxyResponseEvent handleRequest(
        final APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
        final Context context
    ) {
        return app.dispatch(apiGatewayProxyRequestEvent);
    }
}
