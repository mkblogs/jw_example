package com.tech.mkblogs.user.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.user.dto.UserDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserSpringRestClientTest {

	public static final String REST_SERVICE_URI = "http://localhost:8080/api/admin/user";
	
	RestTemplate restTemplate = new RestTemplate(); 
	
	static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "jwtToken";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

	public static String generateJwtToken(String userName) {
		log.info("In side generate jwt token "+userName);
		return Jwts.builder()
				.setSubject((userName))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}
	
   /**
    * JWT Header Info
    * @return
    */
    protected static HttpHeaders getHeaders(){
    	String username = "admin";
    	String jwtToken = generateJwtToken(username);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + jwtToken);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
    
    @Test
    public void testSaveObject() {       
        String url = REST_SERVICE_URI;        
        UserDTO userDTO = UserDTO.builder()
				 .loginName("testboot")
				 .password("password")
				 .repeatPassword("password")
				 .firstName("Servicename")
				 .lastName("servicelastname")
				 .role("ROLE_USER")
				 .build();
        
        HttpEntity<UserDTO> request = new HttpEntity<UserDTO>(userDTO,getHeaders());
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        log.info(response.getBody());
    }
    
    
    @Test
    public void testUpdateObject() {       
        String url = REST_SERVICE_URI;        
        UserDTO userDTO = UserDTO.builder()
        		.id(6)
				 .loginName("testboot")
				 .password("password")
				 .repeatPassword("password")
				 .firstName("first name")
				 .lastName("last name")
				 .role("ROLE_ADMIN")
				 .build();       
        HttpEntity<UserDTO> request = new HttpEntity<UserDTO>(userDTO,getHeaders());
        restTemplate.put(url, request);
    }
   
    @Test
    public void testGetObject() {
    	HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        String url = REST_SERVICE_URI+"/6";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        log.info(response.getBody());
    }
    
    @Test
    public void testDeleteObject() {
    	HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        String url = REST_SERVICE_URI+"/6";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        log.info(response.getBody());
    }
    
    @Test
    public void search() throws Exception {
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        String url = REST_SERVICE_URI+ "?userName=test&firstName=&lastName=";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        log.info("Check the is error :: "+response.getBody().contains("\"isError\":true"));
		if(response.getBody().contains("\"isError\":true")) {
			ResponseDTO<UserDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(response.getBody());
			if(responseDTO.getIsError()) {
				responseDTO.getErrorObject().forEach(log::info);
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = mapFromJsonwithList(response.getBody());
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				responseDTO.getSuccessObject().forEach(log::info);
			}
		}
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
}
