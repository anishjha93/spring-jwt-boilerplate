package com.springjwt.boilerplate.security.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springjwt.boilerplate.model.User;
import com.springjwt.boilerplate.utils.CommonUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * Created by anish on 2019-09-05.
 */

@Component
public class TokenHelper {

    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.secret}")
    public String SECRET;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.header}")
    private String AUTH_HEADER;

  
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String refreshToken(String token) {
        String refreshedToken;
        Date a = new Date();
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(a);
            refreshedToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith( SIGNATURE_ALGORITHM, SECRET )
                .compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String generateToken(String username) { 
        return Jwts.builder()
                .setIssuer( APP_NAME )
                .setSubject(username)
//                .setAudience("vdfbdvdfsf")
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith( SIGNATURE_ALGORITHM, SECRET )
                .compact();
    }


    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() { 
        long expiresIn = EXPIRES_IN;
        return new Date(new Date().getTime() + expiresIn * 1000);
    }

    public int getExpiredIn() { 
        return EXPIRES_IN; 
      }

    public Boolean validateToken(String token, UserDetails userDetails) {
//        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
//        final Date created = getIssuedAtDateFromToken(token);
        return (
                username != null &&
                username.equals(userDetails.getUsername()));
    }

//    private Boolean isCreatedBeforeLastPasswordReset(Date created) {
//        return (created != null);
//    }

    public String getToken( HttpServletRequest request ) {
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = getAuthHeaderFromHeader( request );
        if ( authHeader != null && authHeader.startsWith(CommonUtil.BEARER+" ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader( HttpServletRequest request ) {
        return request.getHeader(AUTH_HEADER);
    }

}