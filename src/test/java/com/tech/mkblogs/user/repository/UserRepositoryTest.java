package com.tech.mkblogs.user.repository;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tech.mkblogs.model.Authorities;
import com.tech.mkblogs.model.JwtUser;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
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
	public void testRepositoryObject() {
		assertNotNull(userRepository);
	}
	
	@Test
	public void testSaveObject() {
		JwtUser user = new JwtUser();
		user.setLoginName("Second");
		user.setPassword("first");
		user.setEncrypted(false);
		user.setFirstName("FirstName");
		user.setLastName("lastname");
		
		Authorities authorities = new Authorities();
		authorities.setAuthority("ROLE_USER");
		
		user.addAuthorities(authorities);	
			
		userRepository.save(user);
		assertNotNull(user.getId());
	}
	
	@Test
	public void testUpdateObject() {
		JwtUser user = new JwtUser();
		user.setLoginName("Second");
		user.setPassword("second");
		user.setEncrypted(false);
		user.setFirstName("second");
		user.setLastName("lastname");
		
		Authorities authorities = new Authorities();
		authorities.setAuthority("ROLE_USER");
		
		user.addAuthorities(authorities);		
		userRepository.save(user);
		assertNotNull(user.getId());
		user.setFirstName("second_update");
		userRepository.save(user);
	}
	
	@Test
	public void testUpdateObjectWithAuth() {
		JwtUser user = new JwtUser();
		user.setLoginName("AuthTestNew");
		user.setPassword("second");
		user.setEncrypted(false);
		user.setFirstName("authfirst");
		user.setLastName("authlastname");
		
		Authorities authorities = new Authorities();
		authorities.setAuthority("ROLE_USER");		
		user.addAuthorities(authorities);		
		
		userRepository.save(user);		
		//Modification
		user.setFirstName("authfirstnewupdate");
		user.removeAuthorities(authorities);
		authorities = new Authorities();
		authorities.setAuthority("ROLE_ADMIN");
		user.addAuthorities(authorities);	
		
		userRepository.save(user);
	}
	
	@Test
	public void testDeleteObject() {
		JwtUser user = new JwtUser();
		user.setLoginName("Delete");
		user.setPassword("second");
		user.setEncrypted(false);
		user.setFirstName("second");
		user.setLastName("lastname");
		
		Authorities authorities = new Authorities();
		authorities.setAuthority("ROLE_USER");		
		user.addAuthorities(authorities);		
		
		userRepository.save(user);
				
		userRepository.deleteById(user.getId());
	}
	
	@Test
	public void testGetAllData() {
		List<JwtUser> userList = userRepository.findAll();
		userList.forEach(log::info);
	}
	
	@Test
	public void getLogin() {
		String loginName = "AuthTestNew";		
		JwtUser user = userRepository.findByLoginName(loginName);
		log.info(user);
		
	}
}
