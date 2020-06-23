package com.tech.mkblogs.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tech.mkblogs.model.Account;
import com.tech.mkblogs.model.BaseModel;
import com.tech.mkblogs.model.JwtUser;
import com.tech.mkblogs.model.UserActionLog;
import com.tech.mkblogs.sequence.SequenceGeneratorService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class BaseModelListener extends AbstractMongoEventListener<BaseModel> {

	@Autowired
	private SequenceGeneratorService sequenceGenerator;
	
	
	@Override
    public void onBeforeConvert(BeforeConvertEvent<BaseModel> event) {
		log.info("Starting the onBeforeConvert() method ");
		String system = "SYSTEM";
		
        if (event.getSource().getId() == null 
        		|| event.getSource().getId() < 1) {
        	
        	String createdBy = "";
        	if(event.getSource() instanceof Account) {
        		event.getSource().setId(sequenceGenerator.generateSequence(Account.SEQUENCE_NAME));
        	}else if(event.getSource() instanceof JwtUser) {
        		event.getSource().setId(sequenceGenerator.generateSequence(JwtUser.SEQUENCE_NAME));
        	}else if(event.getSource() instanceof UserActionLog) {
        		event.getSource().setId(sequenceGenerator.generateSequence(UserActionLog.SEQUENCE_NAME));
        	}
        	
        	try {
    			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    			System.out.println(authentication);
    			createdBy = (String) authentication.getPrincipal();
    		}catch(Exception e) {
    			//e.printStackTrace();
    			createdBy = system;
    		}
        	event.getSource().setCreatedBy(createdBy);
        	event.getSource().setCreatedName(createdBy);
        }else {
        	String lastModifiedBy = "";
        	try {
    			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    			System.out.println(authentication);
    			lastModifiedBy = (String) authentication.getPrincipal();
    		}catch(Exception e) {
    			//e.printStackTrace();
    			lastModifiedBy = system;
    		}
        	event.getSource().setLastModifiedBy(lastModifiedBy);
        	event.getSource().setLastModifiedName(lastModifiedBy);
        }
    }
}
