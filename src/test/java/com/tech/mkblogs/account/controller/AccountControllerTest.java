package com.tech.mkblogs.account.controller;

import static org.junit.Assert.assertNotNull;

import java.util.List;

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

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@WithMockUser(roles = "USER")
public class AccountControllerTest extends BaseControllerTest{

	@Autowired
	private MockMvc mvc;
	
	String url = "/api/user/account";
	
	
	@Test
	public void testObject() {
		assertNotNull(mvc);
	}
	
	@Test
	public void testSaveObject() throws Exception {
		String jsonString = "{" 
						//	+ " \"accountId\":1," 
							+ " \"name\": \"testcontrollerone1\"," 
							+ " \"type\": \"Savings\","
							+ " \"amount\": 120 " 
							+ " } ";
		log.info(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<AccountDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(responseValue);
			if(responseDTO.getIsError()) {
				responseDTO.getErrorObject().forEach(log::info);
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<AccountDTO,ErrorObject> responseDTO = mapFromJson(responseValue);
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
							+ " \"accountId\":9," 
							+ " \"name\": \"TestAccount_updateone\"," 
							+ " \"type\": \"Savings\","
							+ " \"amount\": 120 " 
							+ " } ";
		log.info(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<AccountDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(responseValue);
			if(responseDTO.getIsError()) {
				responseDTO.getErrorObject().forEach(log::info);
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<AccountDTO,ErrorObject> responseDTO = mapFromJson(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testDeleteObject() throws Exception {
		String deleteURL = url +"/3";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(deleteURL).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<String,List<ErrorObject>> responseDTO = mapFromJsonwithErrorForDelete(responseValue);
			if(responseDTO.getIsError()) {
				List<ErrorObject> errorList = responseDTO.getErrorObject();
				errorList.stream().forEach((error) -> log.info(error));
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<String,ErrorObject> responseDTO = mapFromJsonforDelete(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testGetObject() throws Exception {
		String getURL = url + "/8";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(getURL).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<AccountDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(responseValue);
			if(responseDTO.getIsError()) {
				List<ErrorObject> errorList = responseDTO.getErrorObject();
				errorList.stream().forEach((error) -> log.info(error));
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<AccountDTO,ErrorObject> responseDTO = mapFromJson(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testAllObject() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<List<AccountDTO>,List<ErrorObject>> responseDTO = mapFromJsonwithErrorForAllData(responseValue);
			if(responseDTO.getIsError()) {
				List<ErrorObject> errorList = responseDTO.getErrorObject();
				errorList.stream().forEach((error) -> log.info(error));
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = mapFromJsonWithList(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				List<AccountDTO> list = responseDTO.getSuccessObject();
				list.stream().forEach((account) -> log.info(account));
			}
		}
	}
	
	@Test
	public void testGetFilterObject() throws Exception {
		String url = "/api/user/account?accountName=TestAccount&accountType=&amount=";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responseValue = result.getResponse().getContentAsString();
		System.out.println(responseValue);
		log.info("Check the is error :: "+responseValue.contains("\"isError\":true"));
		if(responseValue.contains("\"isError\":true")) {
			ResponseDTO<List<AccountDTO>,List<ErrorObject>> responseDTO = mapFromJsonwithErrorForAllData(responseValue);
			if(responseDTO.getIsError()) {
				List<ErrorObject> errorList = responseDTO.getErrorObject();
				errorList.stream().forEach((error) -> log.info(error));
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = mapFromJsonWithList(responseValue);
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				List<AccountDTO> list = responseDTO.getSuccessObject();
				list.stream().forEach((account) -> log.info(account));
			}
		}
	}
}
