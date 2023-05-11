package com.example.myProject.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


//Contail method to generate token
//Validate token
//IsExpire token

@Component
public class JwtUtil {

//	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
//	private static final long serialVersionUID = -2550185165626007488L;
	
	@Value("${jwt.authentication.jwtExpirationMs}")
	private long jwtExpirationMs;
	
	@Value("${jwt.authentication.jwtSecret}")
	private String jwtSecret;

//	retrieve username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//    retrieve expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
//    retrieve all claim from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

//    to check token expiration
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

//    to generate token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

//    to validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            if(tokensRepository.findByToken(authToken)!=null)
//                throw new ValidationException("Invalid session!! User logged in another session");
//            return true;
//        } catch (SignatureException e) {
//            logger.error("Invalid JWT signature: {}", e.getMessage());
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
//        } catch (ValidationException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//        }
//
//        return false;
//    }
    
    public boolean validateJwtToken(String jwtToken) {
		return false;
	}
    
}
