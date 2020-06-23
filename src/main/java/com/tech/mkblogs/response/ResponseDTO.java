package com.tech.mkblogs.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO<S,E> {

	private Boolean isError;
	private S successObject;
	private E errorObject;
	
}
