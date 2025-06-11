package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.acme.client.JobClient;
import org.acme.client.UserClient;
import org.acme.dto.OrderDTO;
import org.acme.dto.UserDTO;
import org.acme.entity.OrderEntity;
import org.acme.repository.OrderRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    @RestClient
    private UserClient userClient;

    @Inject
    @RestClient
    private JobClient jobClient;

    public List<OrderDTO> getAllOrders()
    {
        List<OrderDTO> orders = new ArrayList<>();
        orderRepository.findAll().stream().forEach(requestLog ->
                orders.add(mapOrderEntityToDTO(requestLog)));

        return orders;
    }

    public void saveOrder(OrderDTO orderDTO){

        UserDTO userDTO =  userClient.findUserById(orderDTO.getUserId());

        if (userDTO.getUsername().equals(orderDTO.getUsername()) && jobClient.findJobById(orderDTO.getJobId()) != null) {
            orderRepository.persist(mapDTOToOrderEntity(orderDTO));
        } else {
            throw new NotFoundException();
        }

    }


    private OrderDTO mapOrderEntityToDTO(OrderEntity orderEntity){

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setUserId(orderEntity.getUserId());
        orderDTO.setUsername(orderEntity.getUsername());
        orderDTO.setUserEmail(orderEntity.getUserEmail());
        orderDTO.setJobId(orderEntity.getJobId());
        orderDTO.setJobTitle(orderEntity.getJobTitle());
        orderDTO.setJobUrl(orderEntity.getJobUrl());

        return orderDTO;
    }

    private OrderEntity mapDTOToOrderEntity(OrderDTO orderDTO)
    {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setUserId(orderDTO.getUserId());
        orderEntity.setUsername(orderDTO.getUsername());
        orderEntity.setUserEmail(orderDTO.getUserEmail());
        orderEntity.setJobId(orderDTO.getJobId());
        orderEntity.setJobTitle(orderDTO.getJobTitle());
        orderEntity.setJobUrl(orderDTO.getJobUrl());

        return orderEntity;
    }


}
