package com.tech.mkblogs.security.jwt.filter;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tech.mkblogs.model.Authorities;
import com.tech.mkblogs.model.JwtUser;
import com.tech.mkblogs.security.jwt.util.JwtUtils;
import com.tech.mkblogs.user.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	UserRepository userRepository;

	/**
	 * 1) JWT Token is in the form "Bearer token". Remove Bearer word and get the token
	 * 2) Once we got the token validate it.
	 * 3) If token is valid configure Spring Security to manually set
	 *  
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {		
		log.info(" doFilterInternal() method ");
		try {
			String jwt = jwtUtils.parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				JwtUser user = userRepository.findByLoginName(username);
				if(user != null) {
					Set<Authorities> userRights = user.getAuthorities();
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, 
				    		userRights.stream().map(x -> new SimpleGrantedAuthority(x.getAuthority())).collect(Collectors.toList()));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}
		filterChain.doFilter(request, response);
	}
}
