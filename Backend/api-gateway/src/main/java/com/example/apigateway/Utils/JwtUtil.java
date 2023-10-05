package com.example.apigateway.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

import static java.security.KeyRep.Type.SECRET;

@Component
public class JwtUtil {




    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }



    private Key getSignKey() {
        return Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes());
    }

}