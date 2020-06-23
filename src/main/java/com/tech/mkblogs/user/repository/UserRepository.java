package com.tech.mkblogs.user.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech.mkblogs.model.JwtUser;

@Repository
public interface UserRepository 
			extends MongoRepository<JwtUser, Integer> {


	JwtUser findByLoginName(String loginName);
	
	@Query("SELECT u FROM User u WHERE u.loginName = :loginName")
	public Collection<JwtUser> findAllByLoginName(@Param("loginName") String loginName);
}
