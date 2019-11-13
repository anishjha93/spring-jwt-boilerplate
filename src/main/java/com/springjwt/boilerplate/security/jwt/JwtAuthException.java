package com.springjwt.boilerplate.security.jwt;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springjwt.boilerplate.model.AppResponse;

@RestControllerAdvice
public class JwtAuthException extends ResponseEntityExceptionHandler {

	@Context
	private HttpHeaders httpHeaders;
	
	@ExceptionHandler(AuthenticationException.class)
	//TODO Need To do modify this
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, HttpServletResponse response){
		AppResponse responseMap = new AppResponse();
	    responseMap.setName(response.getClass().getSimpleName());
	    responseMap.setMessage("User is not Authenticated");
	    responseMap.setStatus(401);
	    responseMap.setDescription(ex.getMessage());
	    return new ResponseEntity<AppResponse>(responseMap, null, HttpStatus.UNAUTHORIZED);
    }
}
