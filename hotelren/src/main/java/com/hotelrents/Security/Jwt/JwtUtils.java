package com.hotelrents.Security.Jwt;
import java.security.Key;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.hotelrents.Security.HotelUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	
	@Value("${security.jwt.secret}")
	private String jwtSecret;
	
	@Value("${security.jwt.expirationTime}")
	private int jwtExpirationTime;
	
	public String generateJwtTokenForUser(Authentication authentication) {
		
		HotelUserDetails userPrincipal = (HotelUserDetails) authentication.getPrincipal();
		
		List<String> role = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		
		return Jwts.builder().setSubject(userPrincipal.getUsername())
			     .claim("roles ", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime()+jwtExpirationTime))
				.signWith(key(),SignatureAlgorithm.HS256).compact();
	}
	
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	
	public String getUserNameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
			return true;
		}
		catch(MalformedJwtException e) {
			logger.error("invalid jwt token"+ e.getMessage());
		}
		catch(ExpiredJwtException e) {
			logger.error("invalid jwt token"+ e.getMessage());

		}
		catch(UnsupportedJwtException e) {
			logger.error("invalid jwt token"+ e.getMessage());

				}
		catch(IllegalArgumentException e) {
			logger.error("invalid jwt token"+ e.getMessage());

		}
		return false;
	}

}

