package com.tech.mkblogs.logging.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tech.mkblogs.model.UserActionLog;

@Repository
public interface LogRepository 
			extends MongoRepository<UserActionLog, Integer> {

	
}
