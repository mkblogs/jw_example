package com.tech.mkblogs.filtersearch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.model.Account;
import com.tech.mkblogs.model.JwtUser;
import com.tech.mkblogs.user.filter.UserFilterDTO;

@Component
public class FilterSearch {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<Account> getFilterData(FilterDTO accountFilterDTO){
		String accountName = accountFilterDTO.getAccountName();
		String accountType = accountFilterDTO.getAccountType();
		String amount = accountFilterDTO.getAmount();
		Query query = new Query();
		if(!StringUtils.isEmpty(accountName))
			query.addCriteria(Criteria.where("accountName").regex("^"+accountName,"i"));
		
		if(!StringUtils.isEmpty(accountType))
			query.addCriteria(Criteria.where("accountType").is(accountType));
		
		if(!StringUtils.isEmpty(amount))
			query.addCriteria(Criteria.where("amount").is(amount));
		
		List<Account> filterList = mongoTemplate.find(query, Account.class);
		return filterList;
	}
	
	
	public List<JwtUser> getUserFilterData(UserFilterDTO filterDTO){
		String username  = filterDTO.getUsername();
		String firstName = filterDTO.getFirstName();
		String lastName  = filterDTO.getLastName();
		Query query = new Query();
		if(!StringUtils.isEmpty(username))
			query.addCriteria(Criteria.where("loginName").is(username));
		
		if(!StringUtils.isEmpty(firstName))
			query.addCriteria(Criteria.where("firstName").regex("^"+firstName+"","i"));
		
		if(!StringUtils.isEmpty(lastName))
			query.addCriteria(Criteria.where("lastName").regex("^"+lastName,"i"));
		
		List<JwtUser> filterList = mongoTemplate.find(query, JwtUser.class);
		return filterList;
	}
}
