package org.acme.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import java.util.Map;

@RegisterRestClient(configKey = "keycloak-client")
public interface KeycloakClient {
    @POST
    @Path("/realms/master/protocol/openid-connect/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Map<String, Object> getToken(@FormParam("grant_type") String grantType,
                                 @FormParam("client_id") String clientId,
                                 @FormParam("username") String username,
                                 @FormParam("password") String password);

    @POST
    @Path("/admin/realms/{realm}/users")
    @Consumes(MediaType.APPLICATION_JSON)
    void createUser(@HeaderParam("Authorization") String authorization, @PathParam("realm") String realm,
                    Map<String, Object> user);


}
