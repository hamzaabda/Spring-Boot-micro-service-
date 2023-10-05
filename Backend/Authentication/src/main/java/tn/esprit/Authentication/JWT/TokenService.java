package tn.esprit.Authentication.JWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.Utils.JwtProperties;
import tn.esprit.Authentication.entities.UserPrincipal;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static tn.esprit.Authentication.Utils.JwtProperties.SECRET;

@Service
@Slf4j
public class TokenService {
    private AuthenticationManager authenticationManager;

    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    @Autowired
    public TokenService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
//    public String generateToken(Authentication authResult) {
//
//        // Grab principal
//        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
//        log.info(principal.getUsername());
//
//        // Create JWT Token
//        String token = JWT.create()
//                .withSubject(principal.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
//                .sign(HMAC512(SECRET.getBytes()));
//        return token;
//    }
//
//    public String generateRefreshToken(Authentication authResult) {
//        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
//
//        // Create a refresh token with a longer expiration time
//        String refreshToken = JWT.create()
//                .withSubject(principal.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME))
//                .sign(HMAC512(SECRET.getBytes()));
//        return refreshToken;
//    }

    public String generateToken(Authentication authResult) {
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("Role",principal.getAuthorities().toString());
        return createToken(claims, principal.getUsername());
    }

    public String generateRefreshToken(Authentication authResult) {
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
        principal.getAuthorities();
        Map<String, Object> claims = new HashMap<>();
        claims.put("Role",principal.getAuthorities().toString());
        return createRefreshToken(claims, principal.getUsername());
    }

    private String createRefreshToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes());
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

}