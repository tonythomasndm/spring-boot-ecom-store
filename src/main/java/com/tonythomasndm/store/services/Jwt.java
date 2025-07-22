package com.tonythomasndm.store.services;

import com.tonythomasndm.store.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {
    private final Claims claims;
    private final SecretKey key;

    public Jwt(Claims claims, SecretKey key) {
        this.claims = claims;
        this.key = key;
    }

    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public Long getUserId(){
        return Long.valueOf(claims.getSubject());
    }

    public Role getRole() {
        return Role.valueOf(claims.get("role", String.class));
    }

    public String toString(){
        return Jwts.builder().claims(claims).signWith(key).compact();
    }
}
