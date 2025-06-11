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
import org.acme.dto.OrderDTO;
import org.acme.service.OrderService;

import java.util.List;

@Path("/api/orders/")
public class OrderController {

    @Inject
    OrderService orderService;

    @GET
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @POST
    @Transactional
    public Response saveNewOrder(OrderDTO orderDTO)
    {
        try{
            orderService.saveOrder(orderDTO);
            return Response.ok().build();
        } catch(Exception e){
            e.printStackTrace();
            return Response.serverError().build();

        }
    }

}
