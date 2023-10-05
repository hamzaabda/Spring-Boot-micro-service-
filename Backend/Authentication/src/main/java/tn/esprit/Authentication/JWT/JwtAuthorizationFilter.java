package tn.esprit.Authentication.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tn.esprit.Authentication.Repository.UserAuthRepository;
import tn.esprit.Authentication.Utils.JwtProperties;
import tn.esprit.Authentication.entities.AppUser;
import tn.esprit.Authentication.entities.UserPrincipal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;



public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserAuthRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserAuthRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException, IOException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    // Bearer
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        //gdhdfgdgffdfggfgfdff

        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX,"");

        if (token != null) {
            // parse the token and validate it
            String email = getEmailFromToken(token);

            // Search in the DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (email != null) {
                AppUser user = userRepository.findAppUserByEmail(email).orElse(null);
                UserPrincipal principal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email,null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }

    private String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JwtProperties.SECRET.getBytes()) // Use your secret key
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}