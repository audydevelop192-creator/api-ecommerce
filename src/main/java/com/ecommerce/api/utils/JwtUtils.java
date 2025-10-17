package com.ecommerce.api.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    // simpan key dalam bentuk Base64 panjang (>= 64 byte) di application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:7776000000}") // default 1 jam
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        // convert secret string ke SecretKey (harus cukup panjang / base64)
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Generate token
    public String generateToken(Integer userId, String username, String role, String email) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("role", role)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String getEmailFromToken(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public Integer getUserIdFromToken(String token) {
        return extractAllClaims(token).get("userId", Integer.class);
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT error: " + e.getMessage());
            return false;
        }
    }
}
