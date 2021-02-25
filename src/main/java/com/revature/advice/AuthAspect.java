package com.revature.advice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
	
	@Around("@annotation(authorized)")
	public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {
		
		HttpSession session = req.getSession(false);
		
		if (session == null || session.getAttribute("my-key") == null) {
			log.error("Not logged in.");
			throw new LoginException("You need to login.");
		}
		
		
		Cookie cookie = (Cookie) session.getAttribute("my-key");
		int userId = Integer.parseInt(cookie.getValue());
		
		MDC.put("event", "Authorized");
		log.info("User# " + userId + "is authorized.");
		
		return pjp.proceed(pjp.getArgs());
	}
}
