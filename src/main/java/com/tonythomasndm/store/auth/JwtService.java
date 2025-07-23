package com.tonythomasndm.store.auth;

import com.tonythomasndm.store.users.User;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public com.tonythomasndm.store.auth.Jwt generateAccessToken(User user) {
        // paylaod should be less heavy
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public com.tonythomasndm.store.auth.Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private com.tonythomasndm.store.auth.Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new com.tonythomasndm.store.auth.Jwt(claims,jwtConfig.getSecretKey());

    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public com.tonythomasndm.store.auth.Jwt parse(String token) {
        try {
            var claims = getClaimsFromToken(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e){
            return null;
        }

    }
}
