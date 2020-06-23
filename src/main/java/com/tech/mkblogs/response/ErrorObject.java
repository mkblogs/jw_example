package com.tech.mkblogs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ErrorObject {

	String errorCode;
	String errorField;
	String errorMsg;
	
}
