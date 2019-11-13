package com.springjwt.boilerplate.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.springjwt.boilerplate.api.AuthenticationResource;
import com.springjwt.boilerplate.model.User;
import com.springjwt.boilerplate.model.UserTokenState;
import com.springjwt.boilerplate.security.jwt.JwtAuthenticationRequest;
import com.springjwt.boilerplate.security.jwt.TokenHelper;
import com.springjwt.boilerplate.service.impl.CustomUserDetailsService;
import com.springjwt.boilerplate.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by anish on 2019-09-05.
 */

@Slf4j
@Controller
public class AuthenticationController implements AuthenticationResource {

    @Autowired
    TokenHelper tokenHelper;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public UserTokenState createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException, IOException {
    	log.info("Create Jwt Token");
    	final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // token creation
        User user = (User)authentication.getPrincipal();
        String jws = CommonUtil.BEARER + " "+tokenHelper.generateToken( user.getUsername());
        int expiresIn = tokenHelper.getExpiredIn();
        return new UserTokenState(jws, expiresIn);
    }

    @Override
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request) {
    	log.info("refresh jwt token");
        String authToken = tokenHelper.getToken( request );

        if (authToken != null) {
            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken);
            int expiresIn = tokenHelper.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(String newPassword, String oldPassword) {
    	log.info("change password");
        userDetailsService.changePassword(oldPassword, newPassword);
        Map<String, String> result = new HashMap<>();
        result.put( "result", "success" );
        return ResponseEntity.accepted().body(result);
    }
}