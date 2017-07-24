package com.jshiffler.webstore.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PromoCodeInterceptor extends HandlerInterceptorAdapter{

	private String promoCode;
	private String errorRedirect;
	private String offerRedirect;
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
	IOException {
	
		String givenPromoCode = request.getParameter("promo");
		
		if(promoCode.equals(givenPromoCode)){
			response.sendRedirect(request.getContextPath() + "/" + offerRedirect);
		} 
		
		else {
			response.sendRedirect(errorRedirect); 
			}
	   
		return false;
		
	}
	
	public void setPromoCode(String promoCode){
		this.promoCode = promoCode;
	}

	/**
	 * @return the offerRedirect
	 */
	public String getOfferRedirect() {
		return offerRedirect;
	}

	/**
	 * @param offerRedirect the offerRedirect to set
	 */
	public void setOfferRedirect(String offerRedirect) {
		this.offerRedirect = offerRedirect;
	}

	/**
	 * @param errorRedirect the errorRedirect to set
	 */
	public void setErrorRedirect(String errorRedirect) {
		this.errorRedirect = errorRedirect;
	}
	
	
	
	
	
}
