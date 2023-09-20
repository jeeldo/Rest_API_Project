package com.algaworks.algafood.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
//public class ApiDeprecationHandler implements HandlerInterceptor{
public class ApiRetirementHandler implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		
		/*
		 * 
		 * //		if(request.getRequestURI().startsWith("/v1/")) {
		 * 
		 * 
		if(request.getRequestURI().startsWith("/v1/cidades")) {
			response.setStatus(HttpStatus.GONE.value());
			return false;
		}
		*/
		return true;
	}
	
	/*
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getRequestURI().startsWith("/v1/")) {
			response.addHeader("X-Algafood-Deprecated", "Essa versão da API está depreciada e deixará de exitir em 10/04/2024.<br>"
					+ "Use a versão mais atual da API.");
		}
		return true;
	}
	*/
}
