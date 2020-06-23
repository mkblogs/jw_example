package com.tech.mkblogs.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tech.mkblogs.exception.JWTApplcationException;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.user.dto.ChangePasswordDTO;
import com.tech.mkblogs.user.dto.UserDTO;
import com.tech.mkblogs.user.filter.UserFilterDTO;
import com.tech.mkblogs.user.service.UserService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class UserController {

	@Autowired
	UserService service;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/user")
	public ResponseEntity<ResponseDTO<UserDTO,ErrorObject>> saveUser(@Valid @RequestBody UserDTO userDTO) throws JWTApplcationException {
		log.info(" saveUser() method ");
		ResponseDTO<UserDTO,ErrorObject> responseDTO = new ResponseDTO<UserDTO,ErrorObject>();
		responseDTO = service.saveUser(userDTO);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/user")
	public ResponseEntity<ResponseDTO<UserDTO,ErrorObject>> updateUser(@Valid @RequestBody UserDTO userDTO) throws JWTApplcationException {
		log.info(" updateUser() method ");
		ResponseDTO<UserDTO,ErrorObject> responseDTO = new ResponseDTO<UserDTO,ErrorObject>();
		responseDTO = service.updateUser(userDTO);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{userId}")  
	public ResponseEntity<ResponseDTO<String,ErrorObject>> deleteUser(@PathVariable("userId") Integer userId) throws JWTApplcationException {
		log.info(" deleteUser() method ");
		ResponseDTO<String,ErrorObject> responseDTO = new ResponseDTO<String,ErrorObject>();
		responseDTO = service.deleteUser(userId);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<ResponseDTO<UserDTO,ErrorObject>> getUser(@PathVariable("userId") Integer userId) throws JWTApplcationException {
		log.info(" getUser() method ");
		ResponseDTO<UserDTO,ErrorObject> responseDTO = new ResponseDTO<UserDTO,ErrorObject>();
		responseDTO = service.getUser(userId);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user") 
	public ResponseEntity<ResponseDTO<List<UserDTO>,ErrorObject>> search(
			@RequestParam("userName") String username,
			@RequestParam("firstName") String firstname,
			@RequestParam("lastName") String lastname) throws JWTApplcationException {
		
		log.info(" search() method ");
		UserFilterDTO dto = new UserFilterDTO();
		dto.setUsername(username);
		dto.setFirstName(firstname);
		dto.setLastName(lastname);
		
		ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = new ResponseDTO<List<UserDTO>,ErrorObject>();
		responseDTO = service.search(dto);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/user/changepassword")
	public ResponseEntity<ResponseDTO<ChangePasswordDTO,ErrorObject>> 
				changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) throws JWTApplcationException {
		log.info(" ChangePasswordDTO() method ");
		ResponseDTO<ChangePasswordDTO,ErrorObject> responseDTO = new ResponseDTO<ChangePasswordDTO,ErrorObject>();
		responseDTO = service.changePassword(changePasswordDTO);
		return ResponseEntity.ok().body(responseDTO);
	}
}
