package org.acme.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.dto.UserDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/user")
@RegisterRestClient
@ApplicationScoped
public interface UserClient {

    @GET
    @Path("/{id}")
    UserDTO findUserById(@PathParam("id") Long id);
}
