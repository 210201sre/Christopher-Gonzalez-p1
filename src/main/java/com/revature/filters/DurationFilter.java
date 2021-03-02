package com.revature.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class DurationFilter extends OncePerRequestFilter{
	
	private static Logger log = LoggerFactory.getLogger(DurationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		
		try {
			filterChain.doFilter(request, response);
		} finally {
			long endTime = System.currentTimeMillis();
			String duration = String.format("%d", endTime - startTime);
			
			MDC.put("duration", duration);
			log.info("Request has finished took {} milli secs to process", duration);
			MDC.clear();
		}
		
	}
}
