package com.tech.mkblogs.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalTime;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.stereotype.Component;

import com.tech.mkblogs.user.dto.ChangePasswordDTO;
import com.tech.mkblogs.user.dto.UserDTO;
import com.tech.mkblogs.validation.PasswordsEqualConstraintValidator.ValidPassword;

import lombok.extern.log4j.Log4j2;


@Component
@Log4j2
public class PasswordsEqualConstraintValidator implements ConstraintValidator<ValidPassword, Object> {
	
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.ANNOTATION_TYPE,ElementType.TYPE })
	@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
	public @interface ValidPassword {
		 String message() default "The passwords must match";
		 Class<?>[] groups() default {};
		 Class<? extends Payload>[] payload() default {};
	}
	
	@Override
    public void initialize(ValidPassword validPassword) {
    }

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		log.info("| Request Time - Start - isValid() " + LocalTime.now());
		boolean valid = true;
        try{
        	if(value instanceof UserDTO) {
        		UserDTO userDTO = (UserDTO) value;
        		String firstObj = userDTO.getPassword();
            	String secondObj = userDTO.getRepeatPassword();
        		return isValidPasswords(firstObj,secondObj);
        	}else if(value instanceof ChangePasswordDTO) {
        		ChangePasswordDTO dto = (ChangePasswordDTO) value;
        		String firstObj = dto.getPassword();
            	String secondObj = dto.getRepeatPassword();
            	return isValidPasswords(firstObj,secondObj);
        	}
        	
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return valid;
	}
	
	/**
	 * 
	 * @param userDTO
	 * @return
	 */
	public Boolean isValidPasswords(String firstObj,String secondObj) {
        Boolean isValid =  firstObj == null && secondObj == null 
        		|| firstObj != null && 
        		firstObj.equalsIgnoreCase(secondObj);
        return isValid;
		
	}
}
