package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.JobDTO;
import org.acme.service.JobService;

import java.awt.*;
import java.util.List;

@Path("/api/jobs")
public class JobController {

    @Inject
    JobService jobService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobDTO> findAllJobs(){
        return jobService.findAllJobs();
    }

    @POST
    @Transactional
    public Response registerJob(JobDTO jobDTO)
    {
        try{
            jobService.RegisterNewJob(jobDTO);
            return Response.ok("{\"message\": \"Work added\"}").build();
        }catch(Exception e)
        {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @POST
    @Transactional
    @Path("/sync")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAndSaveJobsFromApi(){
        try {
            jobService.fetchAndSaveJobsFromApi();
            return Response.ok("{\"message\": \"Dados sincronizados com sucesso\"}").build();
        } catch(Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }
}
