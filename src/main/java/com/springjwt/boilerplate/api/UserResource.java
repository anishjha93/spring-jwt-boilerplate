package com.springjwt.boilerplate.api;


import java.security.Principal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springjwt.boilerplate.model.User;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users")
public interface UserResource {

	    @GET
	    @Path("/{userId}")
	    public User loadById(Long userId );

	    @GET
	    @Path("/all")
	    public ResponseEntity<?> loadAll();


	    @GET
	    @Path("whoami")
	    @RequestMapping("/whoami")
	    public User user(Principal user);
	    
	    @POST
	    @Path("/save")
	    public User addUser(User user);
}
