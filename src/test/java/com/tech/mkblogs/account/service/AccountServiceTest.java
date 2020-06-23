package com.tech.mkblogs.account.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.exception.JWTApplcationException;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class AccountServiceTest {

	@Autowired
	AccountService service;
	
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
		assertNotNull(service);
	}
	
	@Test
	public void testSaveObject()  {
		AccountDTO accountDTO = AccountDTO.builder()
							.name("TestAccount")
							.type("Current")
							.amount(new BigDecimal("100"))
							.build();  
		
		ResponseDTO<AccountDTO, ErrorObject> responseDTO = null;
		try {
			responseDTO = service.saveAccount(accountDTO);
		} catch (JWTApplcationException e) {
			e.printStackTrace();
		}
		if(responseDTO != null && responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			log.info(responseDTO.getSuccessObject());
		}
	}
	
	@Test
	public void testUpdateObject() {
		AccountDTO accountDTO = AccountDTO.builder()
				            .accountId(1)
							.name("TestAccount_update")
							.type("Current")
							.amount(new BigDecimal("100"))
							.build(); 
		
		ResponseDTO<AccountDTO, ErrorObject> responseDTO = null;
		try {
			responseDTO = service.updateAccount(accountDTO);
		} catch (JWTApplcationException e) {
			e.printStackTrace();
		}
		if(responseDTO != null && responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			log.info(responseDTO.getSuccessObject());
		}
	}
	
	@Test
	public void testGetObject() {
		AccountDTO accountDTO = AccountDTO.builder()
							.name("TestAccount_update")
							.type("Current")
							.amount(new BigDecimal("100"))
							.build(); 
		
		ResponseDTO<AccountDTO, ErrorObject> responseDTO = null;
		try {
			responseDTO = service.saveAccount(accountDTO);
		} catch (JWTApplcationException e) {
			e.printStackTrace();
		}
		if(responseDTO != null && responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			log.info(responseDTO.getSuccessObject());
			AccountDTO dto = responseDTO.getSuccessObject();
			try {
				responseDTO = service.getAccount(dto.getAccountId());
			} catch (JWTApplcationException e) {
				e.printStackTrace();
			}
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				log.info(responseDTO.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testDeleteObject() {
		AccountDTO accountDTO = AccountDTO.builder()
							.name("TestAccount-delete")
							.type("Current")
							.amount(new BigDecimal("100"))
							.build(); 
		
		ResponseDTO<AccountDTO, ErrorObject> responseDTO = null;
		try {
			responseDTO = service.saveAccount(accountDTO);
		} catch (JWTApplcationException e) {
			e.printStackTrace();
		}
		if(responseDTO != null && responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			log.info(responseDTO.getSuccessObject());
			AccountDTO dto = responseDTO.getSuccessObject();
			ResponseDTO<String, ErrorObject> responseDelete = null;
			try {
				responseDelete = service.deleteAccount(dto.getAccountId());
			} catch (JWTApplcationException e) {
				e.printStackTrace();
			}
			if(responseDelete != null && responseDelete.getIsError()) {
				log.info(responseDelete.getErrorObject());
			}else {
				log.info(responseDelete.getSuccessObject());
			}
		}
	}
	
	@Test
	public void testGetAccountNameObject() {
		String accountName = "TestAccount_update";
		
		ResponseDTO<List<AccountDTO>, ErrorObject> responseDTO = null;
		try {
			responseDTO = service.findByAccountName(accountName);
		} catch (JWTApplcationException e) {
			e.printStackTrace();
		}
		if(responseDTO != null && responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			log.info(responseDTO.getSuccessObject());
			List<AccountDTO> dtoList = responseDTO.getSuccessObject();
			log.info(dtoList.size());
		}
	}
	
	@Test
	public void testGetAllAccountsObject() {
		ResponseDTO<List<AccountDTO>, ErrorObject> responseDTO = null;
		try {
			responseDTO = service.getAllData();
		} catch (JWTApplcationException e) {
			e.printStackTrace();
		}
		if(responseDTO != null && responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			log.info(responseDTO.getSuccessObject());
			List<AccountDTO> dtoList = responseDTO.getSuccessObject();
			log.info(dtoList.size());
		}
	}
	
	@Test
	public void testFilterObject() {
		FilterDTO dto = new FilterDTO();
		dto.setAccountName("test");
		dto.setFromSearch(true);
		ResponseDTO<List<AccountDTO>, ErrorObject> responseDTO = null;
		try {
			responseDTO = service.search(dto);
		} catch (JWTApplcationException e) {
			e.printStackTrace();
		}
		if(responseDTO != null && responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			log.info(responseDTO.getSuccessObject());
			List<AccountDTO> dtoList = responseDTO.getSuccessObject();
			log.info(dtoList.size());
		}
	}
}
