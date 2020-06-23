package com.tech.mkblogs.account.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.account.service.AccountService;
import com.tech.mkblogs.exception.JWTApplcationException;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class AccountController {

	@Autowired
	AccountService service;
	
	@PostMapping("/account")
	public ResponseEntity<ResponseDTO<AccountDTO,ErrorObject>> saveAccount(@Valid @RequestBody AccountDTO accountDTO) throws JWTApplcationException {
		log.info(" saveAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		responseDTO = service.saveAccount(accountDTO);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/account")
	public ResponseEntity<ResponseDTO<AccountDTO,ErrorObject>> updateAccount(@Valid @RequestBody AccountDTO accountDTO) throws JWTApplcationException {
		log.info(" updateAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		responseDTO = service.updateAccount(accountDTO);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@DeleteMapping("/account/{accountId}")  
	public ResponseEntity<ResponseDTO<String,ErrorObject>> deleteAccount(@PathVariable("accountId") Integer accountId) throws JWTApplcationException {
		log.info(" deleteAccount() method ");
		ResponseDTO<String,ErrorObject> responseDTO = new ResponseDTO<String,ErrorObject>();
		responseDTO = service.deleteAccount(accountId);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/account/{accountId}")
	public ResponseEntity<ResponseDTO<AccountDTO,ErrorObject>> getAccount(@PathVariable("accountId") Integer accountId) throws JWTApplcationException {
		log.info(" getAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		responseDTO = service.getAccount(accountId);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/account") 
	public ResponseEntity<ResponseDTO<List<AccountDTO>,ErrorObject>> search(
			@RequestParam("accountName") String name,
			@RequestParam("accountType") String type,
			@RequestParam("amount") String amount) throws JWTApplcationException {
		
		log.info(" search() method ");
		FilterDTO dto = new FilterDTO();
		dto.setAccountName(name);
		dto.setAccountType(type);
		dto.setAmount(amount);
		ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>,ErrorObject>();
		responseDTO = service.search(dto);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
}
