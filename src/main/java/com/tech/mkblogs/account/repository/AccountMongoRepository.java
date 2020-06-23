package com.tech.mkblogs.account.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tech.mkblogs.model.Account;

@Repository
public interface AccountMongoRepository extends MongoRepository<Account, Integer>{

	List<Account> findByAccountName(String accountName);
	List<Account> findByAmount(BigDecimal amount);
}
