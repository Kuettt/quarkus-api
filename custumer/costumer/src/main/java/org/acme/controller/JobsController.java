package org.acme.controller;

import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.service.JobsService;

import java.beans.Transient;
import java.lang.reflect.Parameter;
import java.util.List;

import org.acme.dto.JobsDTO;

@Path("/api/jobs")
public class JobsController {

	@Inject
	JobsService jobsService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<JobsDTO> findAllJobs() {
		return jobsService.findAllJobs();
	}
	
	
	@POST
	@Transactional
	public Response saveJob(JobsDTO jobDTO)
	{
		try {
			jobsService.createNewJob(jobDTO);
		}
		return Response.ok("{\\\"message\\\": \\\"Trabalho adicionado\\\"}").build();
		catch(Exception e){
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Path("/{id}")
	@Transactional
	public Response changeJob(@PathParam("id") Long id, JobsDTO jobDTO) {
		try {
			jobsService.changeJob(id, jobDTO);
			return Response.accepted().build();
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Transactional
	public Response DeleteJob(@PathParam("id") Long id)
	{
		try {
			jobsService.deleteJob(id);
			return Response.accepted().build();
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
	public Response fetchAndSaveJobsFromApi() {
		try
		{
			jobsService.fetchAndSaveJobsFromApi();
			return Response.ok("{\"message\": \"Dados sincronizados com sucesso\"}").build();
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"Dados sincronizados com sucesso\"}").build();
		}
	}
	
	
	
}
