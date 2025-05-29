package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.UserDTO;
import org.acme.services.UserService;

import java.util.List;

@Path("/api/user")
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> findAllUsers(){
        return userService.findAllUsers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public UserDTO findUserById(@PathParam("id") Long id){
        return userService.findUserById(id);
    }


    @POST
    @Transactional
    public Response createUser(UserDTO userDTO)
    {
        try{
            userService.createUser(userDTO);
            return Response.ok("{\\\"message\\\": \\\"Sucess\\\"}").build();
        } catch(Exception e)
        {
            e.printStackTrace();
            return Response.serverError().build();

        }
    }



    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id ,UserDTO userDTO)
    {
        try{
            userService.UpdateUser(userDTO, id);
            return Response.accepted().build();
        } catch(Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id)
    {
        try{
            userService.deleteUser(id);
            return Response.ok().build();
        } catch(Exception e){
            e.printStackTrace();
            return Response.serverError().build();
        }
    }



}
