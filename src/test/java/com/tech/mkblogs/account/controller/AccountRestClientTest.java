package com.tech.mkblogs.account.controller;

import java.math.BigDecimal;
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

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AccountRestClientTest extends BaseControllerTest {

	public static final String REST_SERVICE_URI = "http://localhost:8080/api/user";
	
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
    	String username = "user";
    	String jwtToken = generateJwtToken(username);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + jwtToken);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
    
    @Test
    public void testSaveObject() {       
        String url = REST_SERVICE_URI+"/account";        
        AccountDTO accountDTO = AccountDTO.builder()
				.name("TestAccount")
				.type("Current")
				.amount(new BigDecimal("100"))
				.build();         
        HttpEntity<AccountDTO> request = new HttpEntity<AccountDTO>(accountDTO,getHeaders());
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        log.info(response.getBody());
    }
    
    @Test
    public void testUpdateObject() {       
        String url = REST_SERVICE_URI+"/account";        
        AccountDTO accountDTO = AccountDTO.builder()
        		.accountId(1)
				.name("TestAccount")
				.type("Current")
				.amount(new BigDecimal("200"))
				.build();         
        HttpEntity<AccountDTO> request = new HttpEntity<AccountDTO>(accountDTO,getHeaders());
        restTemplate.put(url, request);
    }
    
    @Test
    public void testDeleteObject() {
    	HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        String url = REST_SERVICE_URI+"/account/1";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        log.info(response.getBody());
    }
	
	@Test
    public void getAllData() throws Exception {
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        String url = REST_SERVICE_URI+"/account?accountName=test&accountType=&amount=";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        log.info("Check the is error :: "+response.getBody().contains("\"isError\":true"));
		if(response.getBody().contains("\"isError\":true")) {
			ResponseDTO<AccountDTO,List<ErrorObject>> responseDTO = mapFromJsonwithErrors(response.getBody());
			if(responseDTO.getIsError()) {
				responseDTO.getErrorObject().forEach(log::info);
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}else {
			ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = mapFromJsonWithList(response.getBody());
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				responseDTO.getSuccessObject().forEach(log::info);
			}
		}
    }
}
