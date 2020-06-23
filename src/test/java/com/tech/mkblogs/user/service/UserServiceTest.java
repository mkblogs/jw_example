package com.tech.mkblogs.user.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tech.mkblogs.exception.JWTApplcationException;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.user.dto.UserDTO;
import com.tech.mkblogs.user.filter.UserFilterDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Before
    public void beforeEachTest() {
		log.info("==================================================================================");
		log.info("This is executed before each Test");
    }
	@After
    public void afterEachTest() {
		log.info("This is exceuted after each Test");
		log.info("==================================================================================");
    }
	@Test
	public void testRepositoryObject() {
		assertNotNull(userService);
	}
	
	@Test
	public void testSaveObject() throws JWTApplcationException { 
		UserDTO userDTO = UserDTO.builder()
								 .loginName("ServiceLoginname17")
								 .password("second")
								 .firstName("Servicename")
								 .lastName("servicelastname")
								 .role("ROLE_USER")
								 .build();
		ResponseDTO<UserDTO,ErrorObject> responseDTO = userService.saveUser(userDTO);
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			log.info(responseDTO.getSuccessObject());
		}						 
	}
	
	@Test
	public void testUpdateObject() throws JWTApplcationException {
		UserDTO userDTO = UserDTO.builder()
								 .loginName("ServiceLogin")
								 .password("second")
								 .firstName("Servicename")
								 .lastName("servicelastname")
								 .role("ROLE_USER")
								 .build();
		
		ResponseDTO<UserDTO,ErrorObject> responseDTO = userService.saveUser(userDTO);
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			userDTO.setRole("ROLE_ADMIN");
			UserDTO dbObjectDTO = responseDTO.getSuccessObject();
			userDTO.setId(dbObjectDTO.getId());
			responseDTO = userService.updateUser(userDTO);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}	
		}						 
	}
	
	@Test
	public void testGetObject() throws JWTApplcationException {
		Integer userId = 5;
		ResponseDTO<UserDTO,ErrorObject> responseDTO = userService.getUser(userId);
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			UserDTO userDTO = responseDTO.getSuccessObject();
			log.info(userDTO);	
		}						 
	}
	
	@Test
	public void testGetAllObjects() throws JWTApplcationException {
		ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = userService.getAllData();
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			responseDTO.getSuccessObject().forEach(log::info);
		}						 
	}
	
	@Test
	public void testfilterObjects() throws JWTApplcationException {
		UserFilterDTO filterDTO = UserFilterDTO.builder()
				                               .firstName("Mysql")
				                               .username("")
				                               .lastName("")
				                               .build();
		ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = userService.search(filterDTO);
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			responseDTO.getSuccessObject().forEach(log::info);
		}						 
	}
}
