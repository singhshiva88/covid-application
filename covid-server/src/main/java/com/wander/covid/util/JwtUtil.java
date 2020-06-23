package com.wander.covid.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil
{
  @Value("${client.secret.key}")
  private String SECRET_KEY;

  public String generateToken(UserDetails userDetails)
  {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject)
  {
    Date current = new Date(System.currentTimeMillis());
    Date expirationTime = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(current).setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails)
  {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  public String extractUsername(String token)
  {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
  {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token)
  {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  public Date extractExpiration(String token)
  {
    return extractClaim(token, Claims::getExpiration);
  }

  public Boolean isTokenExpired(String token)
  {
    return extractExpiration(token).before(new Date());
  }
}
