package com.tech.mkblogs.account.controller;

import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class BaseControllerTest {

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
	
	protected <T> ResponseDTO<AccountDTO, ErrorObject> mapFromJson(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<AccountDTO, ErrorObject>> typeRef 
			= new TypeReference<ResponseDTO<AccountDTO, ErrorObject>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	protected <T> ResponseDTO<String, ErrorObject> mapFromJsonforDelete(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<String, ErrorObject>> typeRef 
			= new TypeReference<ResponseDTO<String, ErrorObject>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	protected <T> ResponseDTO<AccountDTO, List<ErrorObject>> mapFromJsonwithErrors(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<AccountDTO, List<ErrorObject>>> typeRef 
			= new TypeReference<ResponseDTO<AccountDTO, List<ErrorObject>>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	protected <T> ResponseDTO<String, List<ErrorObject>> mapFromJsonwithErrorForDelete(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<String, List<ErrorObject>>> typeRef 
			= new TypeReference<ResponseDTO<String, List<ErrorObject>>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	protected <T> ResponseDTO<List<AccountDTO>, List<ErrorObject>> mapFromJsonwithErrorForAllData(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		 
		TypeReference<ResponseDTO<List<AccountDTO>, List<ErrorObject>>> typeRef 
			= new TypeReference<ResponseDTO<List<AccountDTO>, List<ErrorObject>>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
	
	protected <T> ResponseDTO<List<AccountDTO>, ErrorObject> mapFromJsonWithList(String json) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		TypeReference<ResponseDTO<List<AccountDTO>, ErrorObject>> typeRef 
			= new TypeReference<ResponseDTO<List<AccountDTO>, ErrorObject>>() {};
		
		return objectMapper.readValue(json, typeRef);
	}
}
