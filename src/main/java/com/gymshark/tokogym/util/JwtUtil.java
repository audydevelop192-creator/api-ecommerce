package com.gymshark.tokogym.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


public class JwtUtil {

    // Secret key (panjang minimal 256 bit / 32 karakter untuk HS256)
    private static final String SECRET_KEY = "iniRahasiaPanjangSekaliYangAmanBanget123456";

    // Kunci untuk enkripsi
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Membuat token JWT dari username
    public String generateToken(String username) {
        // Token berlaku selama 1 hari
        long EXPIRATION_TIME = 24 * 60 * 60 * 1000;
        return Jwts.builder()
                .setSubject(username) // isi utama token
                .setIssuedAt(new Date()) // waktu dibuat
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // kadaluarsa
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // tanda tangan
                .compact();
    }

    // Mendapatkan username dari token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Mengecek apakah token valid dan belum kadaluarsa
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Parsing claims dari token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
