package com.tech.mkblogs.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	public RestExceptionHandler() {
		
	}
   
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        
        String parameterName = ex.getParameterName();
        ErrorObject error = new ErrorObject();
        error.setErrorCode(parameterName);
        error.setErrorField(parameterName);
        error.setErrorMsg(parameterName);
        
        ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
        List<ErrorObject> errorList = new ArrayList<>();
        errorList.add(error);
        responseDTO.setIsError(true);
        responseDTO.setErrorObject(errorList);
        return buildResponseEntity(responseDTO);
    }
  
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        
    	ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
    	responseDTO.setIsError(true);
    	List<ErrorObject> errorList = new ArrayList<ErrorObject>();
        
        if(ex.getBindingResult().hasGlobalErrors()) {        	
        	List<ObjectError> list = ex.getBindingResult().getGlobalErrors();
        	errorList.addAll(list.stream()
        					.map(error -> ErrorObject.of(error.getCode(), error.getObjectName(), error.getDefaultMessage()))
        					.collect(Collectors.toList()));
        }else {
        	if(ex.getBindingResult().hasErrors()) {
        		List<FieldError> list = ex.getBindingResult().getFieldErrors();
        		
        		errorList.addAll(list.stream()
    					.map(error -> ErrorObject.of(error.getField(), error.getObjectName(), error.getDefaultMessage()))
    					.collect(Collectors.toList()));
            }
        }        
        responseDTO.setErrorObject(errorList);
        return buildResponseEntity(responseDTO);
    }
    
   
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
       
    	 String errorMsg = ex.getMessage();
         ErrorObject error = new ErrorObject();
         error.setErrorCode(errorMsg);
         error.setErrorField(errorMsg);
         error.setErrorMsg(errorMsg);
         
         ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
         List<ErrorObject> errorList = new ArrayList<>();
         errorList.add(error);
         responseDTO.setIsError(true);
         responseDTO.setErrorObject(errorList);
    	
        return buildResponseEntity(responseDTO);
    }


   
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
    		MethodArgumentTypeMismatchException ex,
            WebRequest request) {
    	
    	 String errorMsg = ex.getMessage();
         ErrorObject error = new ErrorObject();
         error.setErrorCode(errorMsg);
         error.setErrorField(errorMsg);
         error.setErrorMsg(errorMsg);
         
         ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
         List<ErrorObject> errorList = new ArrayList<>();
         errorList.add(error);
         responseDTO.setIsError(true);
         responseDTO.setErrorObject(errorList);
    	
        return buildResponseEntity(responseDTO);
    }
    
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, 
            HttpStatus status, WebRequest request) {
    	
    	 String errorMsg = ex.getMessage();
         ErrorObject error = new ErrorObject();
         error.setErrorCode(ex.getRequestURL());
         error.setErrorField(errorMsg);
         error.setErrorMsg(errorMsg);
         
         ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
         List<ErrorObject> errorList = new ArrayList<>();
         errorList.add(error);
         responseDTO.setIsError(true);
         responseDTO.setErrorObject(errorList);
    	
        return buildResponseEntity(responseDTO);
    }
    
   
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        
        String errorMsg = ex.getMessage();
        ErrorObject error = new ErrorObject();
        error.setErrorCode(errorMsg);
        error.setErrorField(errorMsg);
        error.setErrorMsg(errorMsg);
        
        ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
        List<ErrorObject> errorList = new ArrayList<>();
        errorList.add(error);
        responseDTO.setIsError(true);
        responseDTO.setErrorObject(errorList);
   	
       return buildResponseEntity(responseDTO);
    }

    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	 String errorMsg = ex.getMessage();
         ErrorObject error = new ErrorObject();
         error.setErrorCode(errorMsg);
         error.setErrorField(errorMsg);
         error.setErrorMsg(errorMsg);
         
         ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
         List<ErrorObject> errorList = new ArrayList<>();
         errorList.add(error);
         responseDTO.setIsError(true);
         responseDTO.setErrorObject(errorList);
    	
        return buildResponseEntity(responseDTO);
    }

   

   
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(
    		DataIntegrityViolationException ex,
            WebRequest request) {
    	
    	 String errorMsg = ex.getMessage();
         ErrorObject error = new ErrorObject();
         error.setErrorCode(errorMsg);
         error.setErrorField(errorMsg);
         error.setErrorMsg(errorMsg);
         
         ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
         List<ErrorObject> errorList = new ArrayList<>();
         errorList.add(error);
         responseDTO.setIsError(true);
         responseDTO.setErrorObject(errorList);
    	
        return buildResponseEntity(responseDTO);
    }
    
    @ExceptionHandler(value = JWTApplcationException.class)
    public ResponseEntity<Object> customException(JWTApplcationException exception) {
        ErrorObject error = new ErrorObject();
        error.setErrorCode(exception.errorCode);
        error.setErrorField(exception.errorCode);
        error.setErrorMsg(exception.errorMessage);
        
        ResponseDTO<Object, List<ErrorObject>> responseDTO = new ResponseDTO<>();
        List<ErrorObject> errorList = new ArrayList<>();
        errorList.add(error);
        responseDTO.setIsError(true);
        responseDTO.setErrorObject(errorList);
   	
       return buildResponseEntity(responseDTO);
    }

    private ResponseEntity<Object> buildResponseEntity(ResponseDTO<Object, List<ErrorObject>> responseDTO) {
        return ResponseEntity.ok().body(responseDTO);
    }
}
