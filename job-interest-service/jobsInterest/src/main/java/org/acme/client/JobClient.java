package org.acme.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.dto.JobDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/jobs")
@RegisterRestClient
@ApplicationScoped

public interface JobClient {

    @GET
    @Path("/{id}")
    JobDTO findJobById(@PathParam("id") Long id);

}
