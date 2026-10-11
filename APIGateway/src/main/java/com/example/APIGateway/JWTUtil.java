package com.example.APIGateway;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;


@Component
public class JWTUtil {
    private static final String SECRET = "your-very-secure-256-bit-secret-key-for-comprinno";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isTokenValid(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
