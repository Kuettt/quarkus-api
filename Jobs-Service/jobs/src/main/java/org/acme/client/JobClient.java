package org.acme.client;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;



@RegisterRestClient(configKey = "job-api")
@ApplicationScoped
@Path("/")
public interface JobClient {

    @GET
    @Path("/jobs")
    JsonNode fetchJobs();

}
