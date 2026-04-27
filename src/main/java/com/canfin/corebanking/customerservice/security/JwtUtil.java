package com.canfin.corebanking.customerservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private Key key;

    private static final String TOKEN_TYPE_CLAIM = "type";
    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(String username, String role) {
        return buildToken(username, role, ACCESS_TOKEN, accessTokenExpiration);
    }

    public String generateRefreshToken(String username, String role) {
        return buildToken(username, role, REFRESH_TOKEN, refreshTokenExpiration);
    }

    // Keep backward compatibility
    public String generateToken(String username, String role) {
        return generateAccessToken(username, role);
    }

    private String buildToken(String username, String role, String type, long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim(TOKEN_TYPE_CLAIM, type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String getTokenType(String token) {
        return (String) getClaims(token).get(TOKEN_TYPE_CLAIM);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        try {
            return ACCESS_TOKEN.equals(getTokenType(token));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            return REFRESH_TOKEN.equals(getTokenType(token));
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
