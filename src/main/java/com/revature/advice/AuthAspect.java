package com.revature.advice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.annonations.Authorized;
import com.revature.exceptions.LoginException;

@Aspect
@Component
public class AuthAspect {
	private static final Logger log = LoggerFactory.getLogger(AuthAspect.class);
	
	@Autowired
	private HttpServletRequest req;
	
	@Before("@annotation(authorized)")
	public void authenticate(Authorized authorized) {
		
		Cookie []cookies = req.getCookies();
		
		if ( cookies == null) {
			log.error("Not logged in.");
			throw new LoginException();
		}
		
		Cookie cookie = cookies[0];
		
		int userId = Integer.parseInt(cookie.getValue());
		
		MDC.put("event", "Authorized");
		log.info("User# " + userId + " is authorized.");
		
	}
}
