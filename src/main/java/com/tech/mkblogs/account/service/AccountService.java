package com.tech.mkblogs.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.account.mapper.AccountMapper;
import com.tech.mkblogs.account.repository.AccountMongoRepository;
import com.tech.mkblogs.exception.JWTApplcationException;
import com.tech.mkblogs.filtersearch.FilterSearch;
import com.tech.mkblogs.model.Account;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class AccountService  {

	@Autowired
	private AccountMongoRepository repository;
	
	@Autowired
	private FilterSearch mongoFilterSearch;
	
	
	public ResponseDTO<AccountDTO,ErrorObject> saveAccount(AccountDTO accountDTO) throws JWTApplcationException{
		log.info("Starting the saveAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		try {
			Account account = AccountMapper.INSTANCE.toAccount(accountDTO);
			account = repository.save(account);
			accountDTO = AccountMapper.INSTANCE.toAccountDTO(account);
			accountDTO.setAccountId(account.getId());
			log.info("Generated Primary Key : " + account.getId());
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(accountDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JWTApplcationException("SAVE_ERROR", e.getMessage());
		}
		log.info("Ended the saveAccount() method ");
		return responseDTO;
	}
	
	public ResponseDTO<AccountDTO,ErrorObject> updateAccount(AccountDTO accountDTO) throws JWTApplcationException {
		log.info("Starting the updateAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		try {
			Optional<Account> dbAccountOptional = repository.findById(accountDTO.getAccountId());
			if(dbAccountOptional.isPresent()) {
				Account dbAccount = dbAccountOptional.get();
				dbAccount = AccountMapper.INSTANCE.toAccountForUpdate(accountDTO, dbAccount);
				dbAccount = repository.save(dbAccount);
				accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(accountDTO);
			}else {
				String errorMsg = ("Entity Not Found " + accountDTO.getAccountId());				
				throw new JWTApplcationException("UPDATE_ERROR", errorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JWTApplcationException("UPDATE_ERROR", e.getMessage());
		}
		log.info("Ended the updateAccount() method ");
		return responseDTO;
	}
	
	public ResponseDTO<AccountDTO,ErrorObject> getAccount(Integer id) throws JWTApplcationException {
		AccountDTO accountDTO = null;
		log.info("Starting the getAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		try {
			Optional<Account> dbObjectExists = repository.findById(id);
			if(dbObjectExists.isPresent()) {
				Account dbAccount = dbObjectExists.get();
				accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(accountDTO);
			}else {
				String errorMsg = ("Entity Not Found " + id);
				throw new JWTApplcationException("GET_ERROR", errorMsg);
			}
		} catch (Exception e) {			
			String errorMsg = ("getAccount() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("GET_ERROR", errorMsg);
		}
		log.info("Ended the getAccount() method ");
		return responseDTO;
	}
	
	public ResponseDTO<String,ErrorObject> deleteAccount(Integer accountId) throws JWTApplcationException {
		String result = "";
		ResponseDTO<String,ErrorObject> responseDTO = new ResponseDTO<String,ErrorObject>();
		try {
			log.info("Starting the deleteAccount() method ");
			Optional<Account> optionalAccount = repository.findById(accountId);
			if(optionalAccount.isPresent()) {
				Account account = optionalAccount.get();
				repository.delete(account);
				result = "Success";
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(result);
			}else {
				String errorMsg = ("Entity Not Found " + accountId);				
				throw new JWTApplcationException("DELETE_ERROR", errorMsg);
			}
		}catch(Exception e) {			
			String errorMsg = ("deleteAccount() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("DELETE_ERROR", errorMsg);
		}
		log.info("Ended the deleteAccount() method ");
		return responseDTO;
	}
	
	public ResponseDTO<List<AccountDTO>,ErrorObject>  findByAccountName(String accountName) throws JWTApplcationException {
		ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>, ErrorObject>();
		List<AccountDTO> list = new ArrayList<AccountDTO>();
		log.info("Starting the findByAccountName() method ");
		try {
			List<Account> dbList =  repository.findByAccountName(accountName);
			for(Account dbAccount:dbList) {
				AccountDTO accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				list.add(accountDTO);
			}
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(list);
			responseDTO.setErrorObject(null);
		} catch (Exception e) {
			String errorMsg = ("findByAccountName() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("FIND_BY_ACCOUNT_NAME_ERROR", errorMsg);
		}
		log.info("Ended the findByAccountName() method ");
		return responseDTO;
	}
	
	public ResponseDTO<List<AccountDTO>,ErrorObject> getAllData() throws JWTApplcationException {
		ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>, ErrorObject>();
		List<AccountDTO> list = new ArrayList<AccountDTO>();
		log.info("Starting the getAllData() method ");
		try {
			List<Account> dbList = repository.findAll();
			for(Account dbAccount:dbList) {
				AccountDTO accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				list.add(accountDTO);
			}
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(list);
			responseDTO.setErrorObject(null);
		} catch (Exception e) {
			String errorMsg = ("getAllData() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("GET_ALL_DATA", errorMsg);
		}
		log.info("Ended the getAllData() method ");
		return responseDTO;
	}
	
	public ResponseDTO<List<AccountDTO>,ErrorObject> search(FilterDTO dto) throws JWTApplcationException {
		ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>, ErrorObject>();
		List<AccountDTO> list = new ArrayList<AccountDTO>();
		log.info("Starting the search() method ");
		try {
			List<Account> dbList = mongoFilterSearch.getFilterData(dto);
			for(Account dbAccount:dbList) {
				AccountDTO accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				list.add(accountDTO);
			}
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(list);
			responseDTO.setErrorObject(null);
		} catch (Exception e) {
			String errorMsg = ("search() Exception, cause :: " + e.getMessage());			
			throw new JWTApplcationException("SEARCH", errorMsg);
		}
		log.info("Ended the search() method ");
		return responseDTO;
	}
}
