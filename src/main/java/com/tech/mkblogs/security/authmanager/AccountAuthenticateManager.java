package com.tech.mkblogs.security.authmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.tech.mkblogs.security.db.DBAuthProvider;

@Component
public class AccountAuthenticateManager implements AuthenticationManager{

	@Autowired
	private DBAuthProvider provider;
	
	public AccountAuthenticateManager() {
		
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication auth = provider.authenticate(authentication);
		return auth;
	}
}
