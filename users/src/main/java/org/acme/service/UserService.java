package org.acme.service;

import org.acme.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.entity.UserEntity;

@ApplicationScoped
public class UserService {

	@Inject
	UserRepository userRepository;
	

	
	public List<UserDTO>findAllUsers()
	{
		
		List<UserDTO> users = new ArrayList<>();
		userRepository.findAll().stream().forEach(user -> {
			users.add(mapUserEntityToDTO(user));
		});
		
		return users;
	}
	
	public void CreateUser(UserDTO user) 
	{
		userRepository.persist(mapDTOToUserEntity(user));
	}
	
	public void UpdateUser(Long id,UserDTO user)
	{
		UserEntity userEntity = userRepository.findById(id);
		
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());
		userEntity.setPhone(user.getPhone());
		userEntity.setAge(user.getAge());
		
		userRepository.persist(userEntity);
	}
	
	public void DeleteUser(Long id)
	{
		userRepository.deleteById(id);
	}
	
	private UserDTO mapUserEntityToDTO(UserEntity userEntity) {
		UserDTO user = new UserDTO();
		
		user.setUsername(userEntity.getUsername());
		user.setPassword(userEntity.getPassword());
		user.setEmail(userEntity.getEmail());
		user.setPhone(userEntity.getPhone());
		user.setAge(userEntity.getAge());
		
		return user;
	}
	
	private UserEntity mapDTOToUserEntity(UserDTO userDTO)
	{
		UserEntity user = new UserEntity();
		
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		user.setAge(userDTO.getAge());
		
		return user;
		
	}
	
}
