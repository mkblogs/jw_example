package com.tech.mkblogs.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class JwtUser extends BaseModel {

	@Transient
    public static final String SEQUENCE_NAME = "user_sequence";
	private String loginName;
	private String password;
	private LocalDateTime lastLogin;
	private Boolean accountExpired; 
	private Boolean accountLocked;
	private Boolean credentialsExpired;
	private Boolean enabled;
	
	private String firstName;
	private String lastName;
	
	private Boolean encrypted;
	
    private Set<Authorities> authorities = new HashSet<Authorities>(0);
	
	public void addAuthorities(Authorities authority) {
		getAuthorities().add(authority);
	}
	
	public void removeAuthorities(Authorities authority) {
		getAuthorities().remove(authority);
	}
	
	public String getRole() {
		Set<Authorities> list = getAuthorities();
		String roleString = "";
		if(list.size() > 0) {
			for(Authorities authorities :list) {
			  roleString += authorities.getAuthority() + ",";
			}
			roleString = roleString.substring(0, roleString.length()-1);
		}
		return roleString;
	}
}
