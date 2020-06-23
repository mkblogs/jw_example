package com.tech.mkblogs.onstartup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.mkblogs.model.Authorities;
import com.tech.mkblogs.model.JwtUser;
import com.tech.mkblogs.user.repository.UserRepository;

@Component
public class DBInit {

	@Autowired
	UserRepository userRepository;	
	
	public void loadUsers() {
		if(userRepository.count() == 0) {
			JwtUser plainUser = getMYSQLPlainUser();
			userRepository.save(plainUser);
			JwtUser plainAdmin = getMYSQLPlainAdmin();
			userRepository.save(plainAdmin);
		}
	}
	
	private JwtUser getMYSQLPlainUser() {
		JwtUser user = new JwtUser();
		user.setLoginName("user");
		user.setPassword("password");
		user.setFirstName("First Mysql");
		user.setLastName("Last Mysql");
		user.setEnabled(Boolean.FALSE);
		user.setAccountExpired(Boolean.FALSE);	
		user.setEncrypted(Boolean.FALSE);
		Authorities authorities = new Authorities();
		authorities.setAuthority("ROLE_USER");
		user.addAuthorities(authorities);
		return user;
	}
	
	private JwtUser getMYSQLPlainAdmin() {
		JwtUser user = new JwtUser();
		user.setLoginName("admin");
		user.setPassword("password");
		user.setFirstName("Admin Mysql");
		user.setLastName("Admin Mysql");
		user.setEnabled(Boolean.FALSE);
		user.setEncrypted(Boolean.FALSE);
		Authorities authorities = new Authorities();
		authorities.setAuthority("ROLE_ADMIN");
		user.addAuthorities(authorities);
		return user;
	}
}
