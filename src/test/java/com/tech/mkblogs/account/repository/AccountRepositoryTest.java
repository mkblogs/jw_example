package com.tech.mkblogs.account.repository;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import com.tech.mkblogs.model.Account;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class AccountRepositoryTest {

	@Autowired
	AccountMongoRepository accountRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Before
    public void beforeEachTest() {
		log.info("==================================================================================");
		log.info("This is executed before each Test");
    }
	@After
    public void afterEachTest() {
		log.info("This is exceuted after each Test");
		log.info("==================================================================================");
    }
	@Test
	public void testRepositoryObject() {
		assertNotNull(accountRepository);
	}
	
	@Test
	public void testSaveObject() {
		Account account = Account.of("Test", "Savings", new BigDecimal(100));
		accountRepository.save(account);
		assertNotNull(account.getId());
	}
	
	@Test
	public void testUpdateObject() {
		Account account = Account.of("Test", "Savings", new BigDecimal(100));
		accountRepository.save(account);
		assertNotNull(account.getId());
		account.setAccountName("updated111");
		accountRepository.save(account);
	}
	
	@Test
	public void testCreateDeleteObject() {
		Account account = Account.of("TestDelete", "Savings", new BigDecimal(100));
		accountRepository.save(account);
		assertNotNull(account.getId());
		accountRepository.delete(account);
	}
	
	
	@Test
	public void testGetAllData() {
		List<Account> list = accountRepository.findAll();
		list.forEach(log::info);
	}
	
	
	@Test
	public void testGetFilterData() {
		String accountName = "TestNew";
		Account account = new Account();
		account.setAccountName(accountName);
		Example<Account> accountExample = Example.of(account, ExampleMatcher.matching());
		Iterable<Account> listValues = accountRepository.findAll(accountExample);
		listValues.forEach(log::info);
	}
	
	@Test
	public void testGetFilterDataUsingMongoTemplate() {
		String accountName = "Test";
		String accountType = "Savings";
		String amount = "100";
		Query query = new Query();
		if(!StringUtils.isEmpty(accountName))
			query.addCriteria(Criteria.where("accountName").regex("^"+accountName+""));
		
		if(!StringUtils.isEmpty(accountType))
			query.addCriteria(Criteria.where("accountType").is(accountType));
		
		if(!StringUtils.isEmpty(amount))
			query.addCriteria(Criteria.where("amount").is(amount));
		
		List<Account> filterList = mongoTemplate.find(query, Account.class);
		filterList.forEach(log::info);
	}
	
}
