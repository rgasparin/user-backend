package com.rgasparin.user;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//return object saving response http with json or xml
@RestController
//mapping the web request to spring Controller
@RequestMapping("user")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDTO.class);
	private static final String MSG_INTERNAL_ERROR = "Service received the request, but was unable to process due to an error.";
    private static final String MSG_PARAMETER_ERROR = "Error when validating some parameter informed";
    private static final String MSG_NOT_FOUND_ERROR = "Resource not found";
	
    private UserService userService;
    //inject service into class
    @Autowired
    public UserController(UserService userService) {
    	this.userService = userService;
	}
    //Api swagger
    @ApiOperation(value = "Service to return a list of users", response = UserDTO.class)
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "users successfully returned."),
			@ApiResponse(code = 412, message = MSG_PARAMETER_ERROR, response = String.class),
			@ApiResponse(code = 404, message = MSG_NOT_FOUND_ERROR),
			@ApiResponse(code = 500, message = MSG_INTERNAL_ERROR, response = String.class)
		})
    //mapping mathod get
	@GetMapping("/find-all")
	public ResponseEntity<Object> findAll() {
		try {
			List<UserDTO> userDTO = userService.findAll();
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch (Exception e) {
            LOGGER.error(MSG_INTERNAL_ERROR, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MSG_INTERNAL_ERROR);
        }
	}
    
    @ApiOperation(value = "Service to return a user by id", response = UserDTO.class)
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "users successfully returned."),
			@ApiResponse(code = 412, message = MSG_PARAMETER_ERROR, response = String.class),
			@ApiResponse(code = 404, message = MSG_NOT_FOUND_ERROR),
			@ApiResponse(code = 500, message = MSG_INTERNAL_ERROR, response = String.class)
		})
	@GetMapping("{id}")
	public ResponseEntity<Object> find(
			@ApiParam(value = "User id", required = true, name = "id")
			@PathVariable("id") Long id) {
		try {
			UserDTO userDTO = userService.findById(id);
			if(userDTO == null) {
				return new ResponseEntity<>(userDTO, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(MSG_INTERNAL_ERROR, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MSG_INTERNAL_ERROR);
        }
	}
    
    @ApiOperation(value = "Service to save a user", response = UserDTO.class)
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "users saved success."),
			@ApiResponse(code = 412, message = MSG_PARAMETER_ERROR, response = String.class),
			@ApiResponse(code = 404, message = MSG_NOT_FOUND_ERROR),
			@ApiResponse(code = 500, message = MSG_INTERNAL_ERROR, response = String.class)
		})
	@PutMapping
	public ResponseEntity<Object> save(
			@ApiParam(value = "User Object", required = true, name = "dto")
			@RequestBody UserDTO dto) {
		try {
			if (dto == null) {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
			}
			return new ResponseEntity<>(userService.save(dto), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(e.getMessage());
        }  catch (Exception e) {
            LOGGER.error(MSG_INTERNAL_ERROR, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MSG_INTERNAL_ERROR);
        }
	}
    
    @ApiOperation(value = "Service to delte a user", response = UserDTO.class)
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "users deteled success."),
			@ApiResponse(code = 412, message = MSG_PARAMETER_ERROR, response = String.class),
			@ApiResponse(code = 404, message = MSG_NOT_FOUND_ERROR),
			@ApiResponse(code = 500, message = MSG_INTERNAL_ERROR, response = String.class)
		})
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(
			@ApiParam(value = "User id", required = true, name = "id")
			@PathVariable("id")  Long id) {
		try {
			userService.delete(id);
			
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(e.getMessage());
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOGGER.error(MSG_INTERNAL_ERROR, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MSG_INTERNAL_ERROR);
        }
	}
}
