package com.rgasparin.user;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//library to implement automatic the methods getter and setters and toString
@Data
//implements contructor without argments
@NoArgsConstructor
//implements contructor with all variables
@AllArgsConstructor
//Anotacao necessaria para dizer ao spring que esta classe é uma entity
@Entity
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//identificando o id da entidade
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String cpf;
	private String nome;
}
