package com.tech.mkblogs.user.controller;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.user.dto.UserDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@WithMockUser(roles = "ADMIN")
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	String url = "/api/admin/user";
	
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
	public void testObject() {
		assertNotNull(mvc);
	}
	
	protected <T> ResponseDTO<UserDTO, ErrorObject> mapFromJson(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<UserDTO, ErrorObject>> typeRef 
			= new TypeReference<ResponseDTO<UserDTO, ErrorObject>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	protected <T> ResponseDTO<UserDTO, List<ErrorObject>> mapFromJsonwithErrors(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<UserDTO, List<ErrorObject>>> typeRef 
			= new TypeReference<ResponseDTO<UserDTO, List<ErrorObject>>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	protected <T> ResponseDTO<List<UserDTO>, ErrorObject> mapFromJsonwithList(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<List<UserDTO>, ErrorObject>> typeRef 
			= new TypeReference<ResponseDTO<List<UserDTO>, ErrorObject>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	protected <T> ResponseDTO<String, List<ErrorObject>> mapFromJsonForDelete(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<String, List<ErrorObject>>> typeRef 
			= new TypeReference<ResponseDTO<String, List<ErrorObject>>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	
	@Test
	public void testSaveObject() throws Exception {
		
		String jsonString = "{"
							+ " \"loginName\": \"testcontroller\"," 
							+ " \"password\": \"password\","
							+ " \"repeatPassword\": \"password\","
							+ " \"firstName\": \"first\"," 
							+ " \"lastName\": \"last\","
							+ " \"role\": \"ROLE_USER\""
							+ " } ";
		log.info(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<UserDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(responseValue);
			if(responseDTO.getIsError()) {
				responseDTO.getErrorObject().forEach(log::info);
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<UserDTO,ErrorObject> responseDTO = mapFromJson(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testUpdateObject() throws Exception {
		String jsonString = "{"
							+ " \"id\":5," 
							+ " \"loginName\": \"testcontrolupdate\"," 
							+ " \"password\": \"password\","
							+ " \"repeatPassword\": \"password\","
							+ " \"firstName\": \"first\"," 
							+ " \"lastName\": \"last\","
							+ " \"role\": \"ROLE_ADMIN\""
							+ " } ";
		log.info(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<UserDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(responseValue);
			if(responseDTO.getIsError()) {
				responseDTO.getErrorObject().forEach(log::info);
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<UserDTO,ErrorObject> responseDTO = mapFromJson(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testGetObject() throws Exception {
		String getURL = url + "/5";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(getURL).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<UserDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(responseValue);
			if(responseDTO.getIsError()) {
				responseDTO.getErrorObject().forEach(log::info);
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<UserDTO,ErrorObject> responseDTO = mapFromJson(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testDeleteObject() throws Exception {
		String deleteURL = url +"/5";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(deleteURL).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<String,List<ErrorObject>> responseDTO = mapFromJsonForDelete(responseValue);
			responseDTO.getErrorObject().forEach(log::info);			
		}else {
			ResponseDTO<String,List<ErrorObject>> responseDTO = mapFromJsonForDelete(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testGetFilterObject() throws Exception {
		String filterURL = url + "?userName=&firstName=&lastName=Last";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(filterURL).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		System.out.println(responseValue);
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<UserDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(responseValue);
			if(responseDTO.getIsError()) {
				responseDTO.getErrorObject().forEach((error) -> log.info(error));
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = mapFromJsonwithList(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				responseDTO.getSuccessObject().forEach(log::info);
			}
		}
	}
}
