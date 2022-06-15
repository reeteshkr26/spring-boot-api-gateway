package com.cg.gateway.filters;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {

	private Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${user.app.jwtSecret}")
	private String jwtSecret;

	public Claims getAllClaimsFromToken(String token) {
		logger.info("JWT Token: {}",token);
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

	public String getUsernameFromJwtToken(String token) {
		logger.info("JWT Token: {}",token);
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
}
