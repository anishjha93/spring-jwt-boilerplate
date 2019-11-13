package com.springjwt.boilerplate.api;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import com.springjwt.boilerplate.model.User;
import com.springjwt.boilerplate.model.UserTokenState;
import com.springjwt.boilerplate.security.jwt.JwtAuthenticationRequest;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("auth")
public interface AuthenticationResource {

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public UserTokenState createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException, IOException;
	
	@POST
	@Path("/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request
            );
	
	public ResponseEntity<?> changePassword(String newPassword, String oldPassword);
}
