package com.app.quantitymeasurement.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key (minimum 256-bit required)
    private final String SECRET = "mysecretkeymysecretkeymysecretkey12345";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🔹 Generate Token
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Extract Username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // 🔹 Validate Token
    public boolean validateToken(String token, String username) {

        String extractedUsername = extractUsername(token);

        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // 🔹 Check Expiry
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 🔹 Get Claims
    private Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}