package tn.esprit.Authentication.JWT;
import com.auth0.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.Utils.JwtProperties;
import tn.esprit.Authentication.entities.UserPrincipal;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
@Service
@Slf4j
public class TokenService {
    private AuthenticationManager authenticationManager;

    @Autowired
    public TokenService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    public String generateToken(Authentication authResult) {

        // Grab principal
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
        log.info(principal.getUsername());

        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
        return token;
    }

    public String generateRefreshToken(Authentication authResult) {
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

        // Create a refresh token with a longer expiration time
        String refreshToken = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
        return refreshToken;
    }

}