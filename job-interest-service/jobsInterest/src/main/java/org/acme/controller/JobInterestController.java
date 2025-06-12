package org.acme.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.JobInterestDTO;
import org.acme.service.JobInterestService;

import java.util.List;

@Path("/api/jobinterest/")
public class JobInterestController {

    @Inject
    JobInterestService jobInterestService;

    @GET
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobInterestDTO> getAllOrders(){
        return jobInterestService.getAllOrders();
    }

    @POST
    @Transactional
    public Response saveNewOrder(JobInterestDTO orderDTO)
    {
        try{
            jobInterestService.saveOrder(orderDTO);
            return Response.ok().build();
        } catch(Exception e){
            e.printStackTrace();
            return Response.serverError().build();

        }
    }

}
