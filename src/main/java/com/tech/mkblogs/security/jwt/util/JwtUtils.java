package com.tech.mkblogs.security.jwt.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tech.mkblogs.security.constants.JwtConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@Getter
public class JwtUtils {

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(String userName) {
		return Jwts.builder()
				.setSubject((userName))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException 
				| UnsupportedJwtException |IllegalArgumentException e) {
			log.error("Exception : {}", e.getMessage());
		}  

		return false;
	}
	
	 public String parseJwt(HttpServletRequest request) {
	    String headerAuth = request.getHeader(JwtConstants.AUTH_HEADER);

	    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(JwtConstants.BEARER)) {
	      return headerAuth.substring(7, headerAuth.length());
	    }

	    return null;
	  }
}
