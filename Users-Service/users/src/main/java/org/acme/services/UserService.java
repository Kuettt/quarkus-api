package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.UserDTO;
import org.acme.entity.UserEntity;
import org.acme.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;


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
    }

    public void UpdateUser(UserDTO userDTO, Long id)
    {
        UserEntity user = userRepository.findById(id);

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

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
        user.setRole(userEntity.getRole());


        return user;
    }

    private UserEntity mapDTOToEntity(UserDTO userDTO){
        UserEntity user = new UserEntity();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }



}
