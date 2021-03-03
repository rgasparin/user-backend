package com.rgasparin.user;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;

@WebMvcTest
public class UserControllerTest {
	
	
	private UserController userController;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	public UserControllerTest(UserController userController ) {
		this.userController = userController;
	}
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.userController);
	}
	

	
	@Test
	public void returnSuccess_WhenFindAllUser() {
		List<UserDTO> list = new ArrayList<>();
		list.add(new UserDTO(1L, "00000000171", "User Mock"));
		list.add(new UserDTO(2L, "00000000022", "User Mock2"));
		
		when(this.userService.findAll())
			.thenReturn(list);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/user/find-all")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returnSuccess_WhenFindUser() {
		
		when(this.userService.findById(1L))
			.thenReturn(new UserDTO(1L, "00000000171", "User Mock"));
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/user/{id}", 1L)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returnNotFound_WhenFindUser() {
		
		when(this.userService.findById(-1L))
			.thenReturn(null);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/user/{id}", -1L)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	/*@Test
	public void returnSuccess_WhenSaveUser() {

		UserDTO dto = new UserDTO(1L, "00000000171", "User Mock");
		when(this.userService.save(dto))
			.thenReturn(dto);
		
		given()
			.body(dto)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.put("/user")
		.then()
			.statusCode(HttpStatus.OK.value());
		
	}
	
	@Test
	public void returnBadRequest_WhenSaveUser() {
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.put("/user")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());

	}

	@Test
	public void returnSuccess_WhenDeleteUser() {

		
		given()
			.accept(ContentType.JSON)
		.when()
			.delete("/user/{id}", 1L)
		.then()
			.statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

	}*/

}
