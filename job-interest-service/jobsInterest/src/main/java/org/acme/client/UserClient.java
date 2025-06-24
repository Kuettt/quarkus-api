package org.acme.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.UserDTO;
import org.acme.filter.UserClientFilter;
import org.acme.headers.UserClientHeader;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;



@RegisterRestClient(configKey= "api-user")
@RegisterClientHeaders(UserClientHeader.class)
@RegisterProvider(UserClientFilter.class)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface UserClient {

    @GET
    @Path("/{id}")
    UserDTO findUserById(@PathParam("id") Long id);
}
