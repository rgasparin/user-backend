package com.rgasparin.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<UserDTO> findAll() {
		List<User> entity = userRepository.findAll();
		return entity == null ? null : entity.stream().map(UserDTO::new).collect(Collectors.toList());
	}
	
	public UserDTO findById(Long id) {
		User entity = userRepository.findById(id).orElse(null);
		return entity == null ? null : new UserDTO(entity);
	}
	
	public UserDTO save(UserDTO dto) {
		User entity = userRepository.save(dto.toEntity());
		return entity == null ? null : new UserDTO(entity);
	}
	
	public void delete(Long id) {
		UserDTO dto = findById(id);
		if(dto == null) {
			throw new EntityNotFoundException("User not found!");
		}
		userRepository.delete(dto.toEntity());
	}
}
