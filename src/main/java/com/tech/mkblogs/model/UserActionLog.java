package com.tech.mkblogs.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserActionLog extends BaseModel{

	@Transient
    public static final String SEQUENCE_NAME = "user_auth_sequence";
		
	String loginName;
	String ipAddress;
	String visitedPage;	
	String input;	
	String output;
	LocalDateTime startTime;
	LocalDateTime endTime;
	Long timeTaken;
}
