package com.tech.mkblogs.security.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.mkblogs.exception.JWTApplcationException;
import com.tech.mkblogs.model.JwtUser;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.security.authmanager.AccountAuthenticateManager;
import com.tech.mkblogs.security.jwt.dto.JwtRequest;
import com.tech.mkblogs.security.jwt.util.JwtUtils;
import com.tech.mkblogs.user.dto.UserAuthDTO;
import com.tech.mkblogs.user.dto.UserSessionDTO;
import com.tech.mkblogs.user.mapper.UserMapper;
import com.tech.mkblogs.user.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AccountAuthenticateManager authManager;

	@PostMapping("/token")
	@Transactional
	public ResponseEntity<ResponseDTO<UserAuthDTO,ErrorObject>> generateToken(
			@RequestBody JwtRequest jwtRequest,HttpSession session) throws JWTApplcationException {
		
		Boolean isUser = false;
		Boolean isAdmin = false;
		Boolean isAuthenticated = false;
		log.info(" generateToken() method ");
		String loginName = jwtRequest.getUsername();
		String password  = jwtRequest.getPassword();
		
		ResponseDTO<UserAuthDTO,ErrorObject> responseDTO = new ResponseDTO<UserAuthDTO,ErrorObject>();
		
		authenticate(loginName, password);
		
		JwtUser loginUser = userRepository.findByLoginName(loginName);
		
		String token = jwtUtils.generateJwtToken(loginName);
        if(loginUser != null) {
    		UserSessionDTO userDTO = new UserSessionDTO();
    		userDTO = UserMapper.INSTANCE.toSessionUserDTO(loginUser);
    		if(userDTO.getAuthority() != null 
    				&& userDTO.getAuthority().equalsIgnoreCase("ROLE_USER")) {
    			isUser = Boolean.TRUE;
    		}else if("ROLE_ADMIN".equalsIgnoreCase(userDTO.getAuthority())) {
    			isAdmin = Boolean.TRUE;
    		}
    		isAuthenticated = Boolean.TRUE;
    		
    		loginUser.setLastLogin(LocalDateTime.now());
        	userRepository.save(loginUser);
    		
    		UserAuthDTO authDTO = UserAuthDTO.builder()
	                 .userName(loginName)
	                 .token(token)
	                 .isAuthenticated(isAuthenticated)
	                 .isUser(isUser)
	                 .isAdmin(isAdmin)
	                 .build();
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(authDTO);
        }else {        	
    		throw new JWTApplcationException("Authentiction Failed", "Authentiction Failed");
        }
		return ResponseEntity.ok().body(responseDTO);
	}
	
	private void authenticate(String loginName, String password) throws JWTApplcationException {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(loginName, password));
		} catch (DisabledException e) {
			throw new JWTApplcationException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new JWTApplcationException("INVALID_CREDENTIALS", e.getMessage());
		}
	}
}
