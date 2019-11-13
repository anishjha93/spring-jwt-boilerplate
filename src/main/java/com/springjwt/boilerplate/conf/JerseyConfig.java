package com.springjwt.boilerplate.conf;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.springjwt.boilerplate.controller.AuthenticationController;
import com.springjwt.boilerplate.controller.UserController;
import com.springjwt.boilerplate.exception.GlobalExceptionHandler;

@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
//		packages("com.budget.budgettracker");
		register(UserController.class);
		register(AuthenticationController.class);
		register(GlobalExceptionHandler.class);
	}
}
