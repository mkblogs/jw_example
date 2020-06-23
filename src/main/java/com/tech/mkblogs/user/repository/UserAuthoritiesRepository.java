package com.tech.mkblogs.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tech.mkblogs.model.Authorities;

@Repository
public interface UserAuthoritiesRepository extends MongoRepository<Authorities, Integer> {

}
