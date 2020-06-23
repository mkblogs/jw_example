package com.tech.mkblogs.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tech.mkblogs.security.authmanager.AccountAuthenticateManager;
import com.tech.mkblogs.security.jwt.exceptionentrypoint.AuthEntryPointJwt;
import com.tech.mkblogs.security.jwt.filter.AuthTokenFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccountAuthenticateManager manager;
	
	@Autowired
	private AuthTokenFilter jwtRequestFilter;
   
  // Securing the urls and allowing role-based access to these urls.
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  disableCORSAndCSRF(http);
	  addAntMatchers(http);
	  addFilterClass(http);
	  entryPointExceptionHandling(http);
	  sessionCode(http);	  
  } 

  /**
   * 
   * @param http
   */
  private void disableCORSAndCSRF(HttpSecurity http) throws Exception {
	  http.cors();
	  http.csrf().disable();
  }
  
  private void sessionCode(HttpSecurity http) throws Exception {
	  http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  http.sessionManagement().maximumSessions(1);
  }
  
  protected void entryPointExceptionHandling(HttpSecurity http) throws Exception {
	  http.exceptionHandling().authenticationEntryPoint(new AuthEntryPointJwt());
  }

  	//Add a filter to validate the tokens with every request
  protected void addFilterClass(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }
  
  protected void addAntMatchers(HttpSecurity http) throws Exception {
	  http      
      .authorizeRequests()
      .antMatchers("/api/token").permitAll()
      .antMatchers("/api/admin/**").hasRole("ADMIN")
      .antMatchers("/api/user/**").hasAnyRole("USER","ADMIN");     
  }
  
  
  @Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return manager;
	}
 
}
