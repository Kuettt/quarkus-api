package org.acme.controller;

import org.acme.service.UserService;

import io.quarkus.security.User;
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

import java.util.List;

import org.acme.dto.UserDTO;

@Path("/api/users")
public class userController {

	@Inject
	UserService userService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> findAllUsers()
	{
		return userService.findAllUsers();
	}
	
	@POST
	@Transactional
	public Response saveUser(UserDTO userDTO)
	{
		try {
			userService.CreateUser(userDTO);
			return Response.ok("{\\\"message\\\": \\\"Usuário registrado\\\"}").build();
			
		} catch(Exception ext)
		{
			ext.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Path("/{id}")
	@Transactional
	public Response UpdateUser(@PathParam("id") Long id, UserDTO userDTO) {
		try {
			userService.UpdateUser(id, userDTO);
			return Response.accepted("{\\\"message\\\": \\\"Usuário atualizado\\\"}").build();
		} catch (Exception ext)
		{
			ext.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Transactional
	@Path("/{id}")
	public Response DeleteUser(@PathParam("id") Long id)
	{
		try {
			userService.DeleteUser(id);
			return Response.ok("{\\\"Mensagem\\\": \\\"Usuário deletado\\\"}").build();
		} catch(Exception ext)
		{
			ext.printStackTrace();
			return Response.serverError().build();
		}
	}
	
}
