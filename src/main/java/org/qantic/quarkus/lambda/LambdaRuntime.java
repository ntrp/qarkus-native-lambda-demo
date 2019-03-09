package org.qantic.quarkus.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

public class LambdaRuntime {

    public static final Logger log = LoggerFactory.getLogger(LambdaRuntime.class);

    public static final String HEADER_REQUEST_ID = "lambda-runtime-aws-request-id";

    public static void main(String[] args) throws MalformedURLException {

        String lambdaRuntimeApi = System.getenv("AWS_LAMBDA_RUNTIME_API");

        log.info(lambdaRuntimeApi);

        LambdaAPIClient apiClient = RestClientBuilder.newBuilder()
                .baseUrl(new URL(lambdaRuntimeApi))
                .build(LambdaAPIClient.class);

        while (true) {

            String requestId = "init";
            try {

                Response next = apiClient.next();
                requestId = next.getHeaderString(HEADER_REQUEST_ID);
                apiClient.respond(requestId, process(next));

            } catch (RuntimeException r) {
                apiClient.error(requestId, r.getMessage());
            }
        }
    }

    private static APIGatewayProxyResponseEvent process(Response next) {

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody("{\"status\":\"ok\"}");
    }
}
