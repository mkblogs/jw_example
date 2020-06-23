package com.tech.mkblogs.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.mkblogs.exception.JWTApplcationException;
import com.tech.mkblogs.filtersearch.FilterSearch;
import com.tech.mkblogs.model.JwtUser;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.user.dto.ChangePasswordDTO;
import com.tech.mkblogs.user.dto.UserDTO;
import com.tech.mkblogs.user.filter.UserFilterDTO;
import com.tech.mkblogs.user.mapper.UserMapper;
import com.tech.mkblogs.user.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	FilterSearch filterSearch;
	
	public ResponseDTO<UserDTO,ErrorObject> saveUser(UserDTO dto) throws JWTApplcationException{
		log.info("Starting the saveUser() method ");
		ResponseDTO<UserDTO,ErrorObject> responseDTO = new ResponseDTO<UserDTO,ErrorObject>();
		try {
			JwtUser user = UserMapper.INSTANCE.toModel(dto);
			if(user.getEncrypted() == null) {
				user.setEncrypted(Boolean.FALSE);
			}
			if(user.getEncrypted()) {
				 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				 user.setPassword(encoder.encode(user.getPassword()));
			}
			user = repository.save(user);
			dto = UserMapper.INSTANCE.toDTO(user);
			dto.setId(user.getId());
			log.info("Generated Primary Key : " + user.getId());
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(dto);
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = ("saveUser() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("SAVE_ERROR",errorMsg);
		}
		log.info("Ended the saveUser() method ");
		return responseDTO;
		
	}
	
	public ResponseDTO<UserDTO,ErrorObject> updateUser(UserDTO dto) throws JWTApplcationException{
		log.info("Starting the updateUser() method ");
		ResponseDTO<UserDTO,ErrorObject> responseDTO = new ResponseDTO<UserDTO,ErrorObject>();
		try {
			Optional<JwtUser> dbOptional = repository.findById(dto.getId());
			if(dbOptional.isPresent()) {
				JwtUser dbUser = dbOptional.get();
				dbUser = UserMapper.INSTANCE.toDTOForUpdate(dto, dbUser);
				dbUser = repository.save(dbUser);
				dto = UserMapper.INSTANCE.toDTO(dbUser);
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(dto);
			}else {
				String errorMsg = ("Entity Not Found " + dto.getId());
				throw new JWTApplcationException("UPDATE_ERROR",errorMsg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			String errorMsg = ("updateUser() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("UPDATE_ERROR",errorMsg);
		}
		log.info("Ended the updateUser() method ");
		return responseDTO;
	}
	
	public ResponseDTO<UserDTO,ErrorObject> getUser(Integer id) throws JWTApplcationException {
		log.info("Starting the getUser() method ");
		ResponseDTO<UserDTO,ErrorObject> responseDTO = new ResponseDTO<UserDTO,ErrorObject>();
		try {
			Optional<JwtUser> dbObjectExists = repository.findById(id);
			if(dbObjectExists.isPresent()) {
				JwtUser dbUser = dbObjectExists.get();
				UserDTO userDTO = UserMapper.INSTANCE.toDTO(dbUser);
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(userDTO);
			}else {
				String errorMsg = ("Entity Not Found " + id);
				throw new JWTApplcationException("GET_ERROR", errorMsg);
			}
		} catch (Exception e) {			
			String errorMsg = ("getUser() Exception, cause :: " + e.getMessage());			
			throw new JWTApplcationException("GET_ERROR", errorMsg);
		}
		log.info("Ended the getUser() method ");
		return responseDTO;
	}
	
	public ResponseDTO<String,ErrorObject> deleteUser(Integer userId) throws JWTApplcationException {
		String result = "";
		ResponseDTO<String,ErrorObject> responseDTO = new ResponseDTO<String,ErrorObject>();
		try {
			log.info("Starting the deleteUser() method ");
			Optional<JwtUser> optionalUser = repository.findById(userId);
			if(optionalUser.isPresent()) {
				JwtUser user = optionalUser.get();
				repository.delete(user);
				result = "Success";
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(result);
			}else {
				String errorMsg = ("Entity Not Found " + userId);
				throw new JWTApplcationException("DELETE_ERROR", errorMsg);
			}
		}catch(Exception e) {			
			String errorMsg = ("deleteUser() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("DELETE_ERROR", errorMsg);
		}
		log.info("Ended the deleteUser() method ");
		return responseDTO;
	}
	
	public ResponseDTO<List<UserDTO>,ErrorObject> findByUserName(String username) throws JWTApplcationException {
		ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = new ResponseDTO<List<UserDTO>, ErrorObject>();
		List<UserDTO> list = new ArrayList<UserDTO>();
		log.info("Starting the findByUserName() method ");
		try {
			Collection<JwtUser> dbList =  repository.findAllByLoginName(username);
			list.addAll(dbList.stream()
					.map(dbUser -> UserMapper.INSTANCE.toDTO(dbUser))
					.collect(Collectors.toList()));
			
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(list);
			responseDTO.setErrorObject(null);
		} catch (Exception e) {
			String errorMsg = ("findByUserName() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("FIND_BY_USER_NAME_ERROR", errorMsg);
		}
		log.info("Ended the findByUserName() method ");
		return responseDTO;
		
	}
	public ResponseDTO<List<UserDTO>,ErrorObject> getAllData() throws JWTApplcationException {
		ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = new ResponseDTO<List<UserDTO>, ErrorObject>();
		List<UserDTO> list = new ArrayList<UserDTO>();
		log.info("Starting the getAllData() method ");
		try {
			List<JwtUser> dbList = repository.findAll();
			list.addAll(dbList.stream()
					.map(dbUser -> UserMapper.INSTANCE.toDTO(dbUser))
					.collect(Collectors.toList()));
			
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
	public ResponseDTO<List<UserDTO>,ErrorObject> search(UserFilterDTO dto) throws JWTApplcationException {
		ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = new ResponseDTO<List<UserDTO>, ErrorObject>();
		List<UserDTO> list = new ArrayList<UserDTO>();
		log.info("Starting the search() method ");
		try {
			List<JwtUser> dbList = filterSearch.getUserFilterData(dto);
			list.addAll(dbList.stream()
					.map(dbUser -> UserMapper.INSTANCE.toDTO(dbUser))
					.collect(Collectors.toList()));
			
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
	
	public ResponseDTO<ChangePasswordDTO,ErrorObject> changePassword(ChangePasswordDTO dto) throws JWTApplcationException {
		log.info("Starting the changePassword() method ");
		System.out.println(dto);
		ResponseDTO<ChangePasswordDTO,ErrorObject> responseDTO = new ResponseDTO<ChangePasswordDTO,ErrorObject>();
		try {
			Integer userId = dto.getUserId();
			Optional<JwtUser> userOptional = repository.findById(userId);
			if(userOptional.isPresent()) {
				JwtUser user = userOptional.get();
				if(dto.getEncrypted() == null) {
					user.setEncrypted(Boolean.FALSE);
				}
				if(dto.getEncrypted()) {
					 user.setEncrypted(Boolean.TRUE);
					 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
					 user.setPassword(encoder.encode(dto.getPassword()));
				}else {
					user.setPassword(dto.getPassword());
				}
				user = repository.save(user);
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(dto);
			}else {
				String errorMsg = ("Entity Not Found " + userId);
				throw new JWTApplcationException("CHANE_PWD_ERROR", errorMsg);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = ("changePassword() Exception, cause :: " + e.getMessage());
			throw new JWTApplcationException("CHANE_PWD_ERROR", errorMsg);
		}
		log.info("Ended the changePassword() method ");
		return responseDTO;
	}
}
