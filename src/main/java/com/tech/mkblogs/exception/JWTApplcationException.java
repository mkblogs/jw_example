package com.tech.mkblogs.exception;

public class JWTApplcationException extends Exception {

	private static final long serialVersionUID = 1L;

	String errorCode;
	String errorMessage;
	
	public JWTApplcationException() {
		this.errorCode = "";
		this.errorMessage = "";
	}
	
    public JWTApplcationException(String errorCode,String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
