package com.fr.eql.ai115.bellybuddyback.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTGenerator {
  private static final String secretKey = SecretKeyGenerator.generateNewSecretKey();
  private static final Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
    String token = Jwts.builder()
      .setSubject(username)
      .setIssuedAt(currentDate)
      .setExpiration(expirationDate)
      .signWith(key, SignatureAlgorithm.HS512)
      .compact();
    System.out.println("New Key :");
    System.out.println(key);
    System.out.println("New Token :");
    System.out.println(token);
    return token;
  }

  public String getUsernameFromJWT(String token) {
    Claims claims = Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      throw new AuthenticationCredentialsNotFoundException("Expired or invalid JWT token", e.fillInStackTrace());
    }
  }
}
