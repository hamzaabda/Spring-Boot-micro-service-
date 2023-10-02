package tn.esprit.Authentication.Services;

import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.JWT.TokenService;
import tn.esprit.Authentication.Repository.UserAuthRepository;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.entities.AppUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service

public class AuthService {


    private  final TokenService tokenService;
    private final UserAuthRepository userAuthRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(TokenService tokenService, UserAuthRepository userAuthRepository, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.userAuthRepository = userAuthRepository;
        this.authenticationManager = authenticationManager;
    }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Optional<AppUser> user = userAuthRepository.findAppUserByEmail(request.getEmail());
            if (!user.isPresent()) {
                return AuthenticationResponse.builder().errormessage("User With this Email Not found").build();
            }

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                    request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            AppUser user1 = user.get();

            if (user1.getIsEnabled() == null || !user1.getIsEnabled()) {
                return AuthenticationResponse.builder().errormessage("Confirm Email Before Login").build();
            } else {
                var access_token = tokenService.generateToken(authenticate);
                var refreshToken = tokenService.generateRefreshToken(authenticate);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refreshToken);

                return AuthenticationResponse.builder()
                        .tokens(tokens)
                        .successmessage("Authentification Successful")
                        .build();
            }
        }

        catch (BadCredentialsException ex) {
            return AuthenticationResponse.builder()
                    .errormessage("Invalid credentials")
                    .build();
        }
    }



    //    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        try {
//            Optional<AppUser> user = userAuthRepository.findAppUserByUsername(request.getUsername());
//            if (!user.isPresent()) {
//                return AuthenticationResponse.builder().errormessage("Username Not found").build();
//            }
//
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getUsername(),
//                            request.getPassword()
//                    )
//            );
//
//            AppUser user1 = user.get();
//
//            if (user1.getIsEnabled() == null || !user1.getIsEnabled()) {
//                return AuthenticationResponse.builder().errormessage("Confirm Email Before Login").build();
//            } else {
//                var access_token = jwtService.generateToken(user1);
//                var refreshToken = jwtService.generateRefreshToken(user1);
//
//                Map<String, String> tokens = new HashMap<>();
//                tokens.put("access_token", access_token);
//                tokens.put("refresh_token", refreshToken);
//
//                return AuthenticationResponse.builder()
//                        .tokens(tokens)
//                        .successmessage("Authentification Successful")
//                        .build();
//            }
//        }
//
//        catch (BadCredentialsException ex) {
//            return AuthenticationResponse.builder()
//                    .errormessage("Invalid credentials")
//                    .build();
//        }
//    }

















}
