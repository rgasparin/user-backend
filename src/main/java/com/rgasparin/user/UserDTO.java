package com.rgasparin.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//library to implement automatic the methods getter and setters and toString
@Data
//implements contructor without argments
@NoArgsConstructor
//implements contructor with all variables
@AllArgsConstructor
public class UserDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String cpf;
	private String name;
	
	public UserDTO(User entity) {
		this(
				entity.getId(),
				entity.getCpf(),
				entity.getNome()
			);
	}
	
	public User toEntity() {
		return new User(this.id, this.cpf, this.name);
	}
}
