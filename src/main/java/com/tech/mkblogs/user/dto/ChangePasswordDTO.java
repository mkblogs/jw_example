package com.tech.mkblogs.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.tech.mkblogs.validation.First;
import com.tech.mkblogs.validation.PasswordsEqualConstraintValidator.ValidPassword;
import com.tech.mkblogs.validation.Second;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidPassword(message = "{changepassword.password.mustmatch}",groups = Default.class)
public class ChangePasswordDTO {

	private Integer userId;
	
	@NotEmpty(message = "{changepassword.password.notempty}",groups = {First.class})
	@Size(min = 2,max = 20,message = "{changepassword.password.size}",groups = {Second.class})
	private String password;
	
	@NotEmpty(message = "{changepassword.repeatpassword.notempty}",groups = {First.class})
	@Size(min = 2,max = 20,message = "{changepassword.repeatpassword.size}",groups = {Second.class})
	private String repeatPassword;
	
	private Boolean encrypted;
}
