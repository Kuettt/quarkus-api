package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.client.UserClient;
import org.acme.dto.UserDTO;
import org.acme.entity.UserEntity;
import org.acme.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserClient userClient;

    public List<UserDTO> findAllUsers()
    {
        List<UserDTO> users = new ArrayList<>();

        userRepository.findAll().stream().forEach(user -> {
            users.add(mapUserEntityToDTO(user));
        });

        return users;
    }

    public UserDTO findUserById(Long id){
        UserEntity user = userRepository.findById(id);
        return mapUserEntityToDTO(user);

    }


    public void createUser(UserDTO userDTO)
    {
        userRepository.persist(mapDTOToEntity(userDTO));
        userClient.createUserInKeyCloak(userDTO.getUsername(), userDTO.getPassword());
    }

    public void UpdateUser(UserDTO userDTO, Long id)
    {
        UserEntity user = userRepository.findById(id);

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        userRepository.persist(user);
    }


    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }


    private UserDTO mapUserEntityToDTO(UserEntity userEntity)
    {
        UserDTO user= new UserDTO();

        user.setUsername(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());



        return user;
    }

    private UserEntity mapDTOToEntity(UserDTO userDTO){
        UserEntity user = new UserEntity();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }



}
