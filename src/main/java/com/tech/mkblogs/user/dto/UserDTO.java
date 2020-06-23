package com.tech.mkblogs.user.dto;

import java.time.LocalDateTime;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.tech.mkblogs.validation.First;
import com.tech.mkblogs.validation.PasswordsEqualConstraintValidator.ValidPassword;
import com.tech.mkblogs.validation.Second;
import com.tech.mkblogs.validation.Third;
import com.tech.mkblogs.validation.UniqueValueValidator.UniqueValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({First.class,Second.class,Third.class,UserDTO.class})
@ValidPassword(message = "{user.password.mustmatch}",groups = Third.class)
@UniqueValue(
	methodName = "findByUserName",
	className = "com.tech.mkblogs.user.service.UserService",
	message = "{user.username.alreadyexists}",groups = Default.class)

@Builder
public class UserDTO {

	private Integer id;
	
	@NotEmpty(message = "{user.username.notempty}",groups = {First.class})
	@Size(min = 2,max = 20,message = "{user.username.size}",groups = {Second.class})
	//@Pattern(regexp = "[A-Za-z]",message = "{user.username.pattern}",groups = {Second.class})
	private String loginName;
	
	@NotEmpty(message = "{user.password.notempty}",groups = {First.class})
	@Size(min = 2,max = 20,message = "{user.password.size}",groups = {Second.class})
	private String password;
	
	@NotEmpty(message = "{user.repeatpassword.notempty}",groups = {First.class})
	@Size(min = 2,max = 20,message = "{user.repeatpassword.size}",groups = {Second.class})
	private String repeatPassword;
	
	@NotEmpty(message = "{user.firstname.notempty}",groups = {First.class})
	@Size(min = 2,max = 50,message = "{user.firstname.size}",groups = {Second.class})
	private String firstName;
	
	@NotEmpty(message = "{user.lastname.notempty}",groups = {First.class})
	@Size(min = 2,max = 50,message = "{user.lastname.size}",groups = {Second.class})
	private String lastName;
	
	private String createdBy;
	private LocalDateTime createdTs;
	private String lastModifiedBy;
	private LocalDateTime lastModifiedTs;
	private Integer version;
	
	private Boolean encrypted;	
	private String role;
	
	private String createdName;	
	private String lastModifiedName;
}