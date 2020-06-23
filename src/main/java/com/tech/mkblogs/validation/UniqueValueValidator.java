package com.tech.mkblogs.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.validation.UniqueValueValidator.UniqueValue;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@SuppressWarnings("unchecked")
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object>{

	@Autowired
    private ApplicationContext applicationContext;
	
    private String className;
    private String methodName;
	
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
	@Constraint(validatedBy = UniqueValueValidator.class)
	public @interface UniqueValue {
		String message() default "Already exists value";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	    
	    String methodName();
	    String className();
	}
	

	@Override
    public void initialize(UniqueValue unique) {
		this.methodName = unique.methodName();
		this.className = unique.className();
	}
	 
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		log.info("| Request Time - Start - isValid() " + LocalTime.now());
		if(value instanceof AccountDTO) {
			AccountDTO inputValue = (AccountDTO) value;
			return isValidAccount(inputValue, context);
		}
		/*
		else if(value instanceof UserDTO) {
			UserDTO inputValue = (UserDTO) value;
			return isValidUser(inputValue, context);
		}*/
		return true;
	}
	
	
	public boolean isValidAccount(AccountDTO inputValue, ConstraintValidatorContext context) {
		log.info("| Request Time - Start - isValidAccount() " + LocalTime.now());
		boolean flag = true;
		if(inputValue.getAccountId() == null) {
			flag = isValidObjectForSave(inputValue, context);
		}else {
			flag = isValidObjectForUpdate(inputValue, context);
		}
		return flag;
	}
	
	protected boolean isValidObjectForSave(AccountDTO inputValue,ConstraintValidatorContext context) {
		boolean statusFlag = true; //Default Success
		try {
			Class<?> clazz = Class.forName(this.className);
			Method method = clazz.getMethod(this.methodName, String.class);
			Object serviceObject = applicationContext.getBean(clazz);
			
			ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = 
					(ResponseDTO<List<AccountDTO>,ErrorObject>)  method.invoke(serviceObject, inputValue.getName()); 
			
			if(responseDTO.getIsError()) { //error
				statusFlag = false; //Failure
			}else {
				List<AccountDTO> dbAccountList = responseDTO.getSuccessObject();
				if(dbAccountList.size() > 0)
	    			statusFlag = false; //Failure
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return statusFlag;
	}
	
	protected boolean isValidObjectForUpdate(AccountDTO inputValue,ConstraintValidatorContext context) {
		boolean statusFlag = true; //Default Success
		try {
			Class<?> clazz = Class.forName(this.className);
			Object serviceObject = applicationContext.getBean(clazz);
			Method method = serviceObject.getClass().getMethod(this.methodName, String.class);
			
			ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = 
					(ResponseDTO<List<AccountDTO>,ErrorObject>)  method.invoke(serviceObject, inputValue.getName()); 
			
			if(responseDTO.getIsError()) { //error
				statusFlag = false; //Failure
			}else {
				List<AccountDTO> dbAccountList = responseDTO.getSuccessObject();
				
				AccountDTO existsObject = dbAccountList.stream()
						                               .filter(account -> account.getAccountId() != inputValue.getAccountId())
						                               .findAny()
						                               .orElse(null);
				
				if (existsObject != null)
					statusFlag = false; //Failure
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return statusFlag;
	}
	
	/**
	 * =======================================================================================================================**/
	/*
	public boolean isValidUser(UserDTO inputValue, ConstraintValidatorContext context) {
		log.info("| Request Time - Start - isValidUser() " + LocalTime.now());
		boolean flag = true;
		if(inputValue.getId() == null) {
			flag = isValidUserForSave(inputValue, context);
		}else {
			flag = isValidUserForUpdate(inputValue, context);
		}
		return flag;
	}
	
	protected boolean isValidUserForSave(UserDTO inputValue,ConstraintValidatorContext context) {
		boolean statusFlag = true; //Default Success
		try {
			Class<?> clazz = Class.forName(this.className);
			Method method = clazz.getMethod(this.methodName, String.class);
			Object serviceObject = applicationContext.getBean(clazz);
			
			ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = 
					(ResponseDTO<List<UserDTO>,ErrorObject>)  method.invoke(serviceObject, inputValue.getLoginName()); 
			
			if(responseDTO.getIsError()) { //error
				statusFlag = false; //Failure
			}else {
				List<UserDTO> dbAccountList = responseDTO.getSuccessObject();
				if(dbAccountList.size() > 0)
	    			statusFlag = false; //Failure
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return statusFlag;
	}
	
	protected boolean isValidUserForUpdate(UserDTO inputValue,ConstraintValidatorContext context) {
		boolean statusFlag = true; //Default Success
		try {
			Class<?> clazz = Class.forName(this.className);
			Object serviceObject = applicationContext.getBean(clazz);
			Method method = serviceObject.getClass().getMethod(this.methodName, String.class);
			
			ResponseDTO<List<UserDTO>,ErrorObject> responseDTO = 
					(ResponseDTO<List<UserDTO>,ErrorObject>)  method.invoke(serviceObject, inputValue.getLoginName()); 
			
			if(responseDTO.getIsError()) { //error
				statusFlag = false; //Failure
			}else {
				List<UserDTO> dbUserList = responseDTO.getSuccessObject();
				
				UserDTO existsObject = dbUserList.stream()
                        							.filter(account -> account.getId() != inputValue.getId())
                        							.findAny()
                        							.orElse(null);
				if (existsObject != null)
					statusFlag = false; //Failure
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return statusFlag;
	}
	*/
	 
}
