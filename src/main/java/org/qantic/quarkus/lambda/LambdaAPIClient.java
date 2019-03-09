package org.qantic.quarkus.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/2018-06-01/runtime")
public interface LambdaAPIClient {

    @Path("/invocation/next")
    @GET
    Response next();

    @Path("/invocation/{:requestId}/response")
    @POST
    void respond(String requestId, APIGatewayProxyResponseEvent event);

    @Path("/invocation/{:requestId}/error")
    @POST
    void error(String requestId, String body);
}
