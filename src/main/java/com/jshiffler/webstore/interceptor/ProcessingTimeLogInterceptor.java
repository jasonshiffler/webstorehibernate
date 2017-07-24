package com.jshiffler.webstore.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ProcessingTimeLogInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = Logger.getLogger(ProcessingTimeLogInterceptor.class);

	// Makes note of the time before request is sent to controller
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		
		return true;
	}

	// Diffs the time before and after the request was handled and writes to the log
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		String queryString = request.getQueryString() == null ? "" : "?" + request.getQueryString();
		String path = request.getRequestURL() + queryString;
		
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		
		LOGGER.info(String.format("%s milliseconds taken to process the request %s.", (endTime - startTime), path));
	}
		
		
	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	
		//NO operation
		
	}
	
	
	
	
	
	
	
}
