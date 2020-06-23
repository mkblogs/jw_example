package com.tech.mkblogs.logging;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.tech.mkblogs.logging.repository.LogRepository;
import com.tech.mkblogs.model.UserActionLog;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CommonRequestLoggingFilterConfig extends CommonsRequestLoggingFilter {
	
	@Autowired
	LogRepository logRepo;
	
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		log.info("Before Request :: "+message);
		UserActionLog logAction = new UserActionLog();
		Principal principal = request.getUserPrincipal();
		if(principal!= null) {
			logAction.setLoginName(principal.getName());
		}
		logAction.setIpAddress(request.getRemoteAddr());
		logAction.setVisitedPage(request.getRequestURI());
		logAction.setInput(message);
		logAction.setStartTime(LocalDateTime.now());
		
		request.setAttribute("logRequest", logAction);
	}
	
	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		log.info("After Request :: "+message);
		UserActionLog logAction = (UserActionLog) request.getAttribute("logRequest");
        
		logAction.setOutput(message);
		logAction.setEndTime(LocalDateTime.now());
		
		Duration duration = Duration.between(logAction.getStartTime(), logAction.getEndTime());
		Long timeTaken =  duration.toMillis();
		logAction.setTimeTaken(timeTaken);
		
		logRepo.save(logAction);
	}
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		 setIncludeQueryString(true);
	     setIncludePayload(true);
	     setMaxPayloadLength(10000);
	     setIncludeHeaders(false);
	     setAfterMessagePrefix("Account USER : ");
		super.afterPropertiesSet();
	}
}
