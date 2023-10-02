package tn.esprit.Authentication.Services;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.JWT.TokenService;
import tn.esprit.Authentication.Repository.UserAuthRepository;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.Requests.RegisterRequest;
import tn.esprit.Authentication.entities.AppUser;
import tn.esprit.Authentication.entities.EmailConfirmationToken;
import tn.esprit.Authentication.entities.Role;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.time.LocalDateTime;
import java.util.*;


@Service

public class AuthService {


    private final TokenService tokenService;
    private final UserAuthRepository userAuthRepository;
    private final AuthenticationManager authenticationManager;

    private final EmailConfirmationTokenService emailConfirmationTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailSenderService emailSenderService;

    public AuthService(TokenService tokenService, UserAuthRepository userAuthRepository, AuthenticationManager authenticationManager, EmailConfirmationTokenService emailConfirmationTokenService) {
        this.tokenService = tokenService;
        this.userAuthRepository = userAuthRepository;
        this.authenticationManager = authenticationManager;
        this.emailConfirmationTokenService = emailConfirmationTokenService;
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
        } catch (BadCredentialsException ex) {
            return AuthenticationResponse.builder()
                    .errormessage("Invalid credentials")
                    .build();
        } catch (NonUniqueResultException ex) {
            // Handle the NonUniqueResultException here
            return AuthenticationResponse.builder()
                    .errormessage("Non-unique result")
                    .build();

        }

        catch (IncorrectResultSizeDataAccessException ex) {
            // Handle the NonUniqueResultException here
            return AuthenticationResponse.builder()
                    .errormessage("Non-unique result")
                    .build();

        }
    }










    public AuthenticationResponse register(RegisterRequest registerRequest) {
        try {

            Optional<AppUser> existinguser = userAuthRepository.findAppUserByUsername(registerRequest.getUsername());
            if(existinguser.isPresent())
            {
                return AuthenticationResponse.builder()
                        .errormessage("User already Exisits").build();
            }

            Optional<AppUser> existingemail = userAuthRepository.findAppUserByEmail(registerRequest.getEmail());
            if(existingemail.isPresent())
            {
                return AuthenticationResponse.builder()
                        .errormessage("Email already Exisits").build();
            }

            Role selectedRole = roleService.findbyid(registerRequest.getRoleid())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));
            ///Le 1 er role dans la DB PARTICIPANT

            var appuser = AppUser.builder()
                    .prenom(registerRequest.getFirstname())
                    .nom(registerRequest.getLastname())
                    .email(registerRequest.getEmail())
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .isEnabled(false)
                    .roles(new HashSet<>(Collections.singletonList(selectedRole)))
                    .build();
            userAuthRepository.save(appuser);

            String token = UUID.randomUUID().toString();
            EmailConfirmationToken confirmationToken = new EmailConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    appuser
            );
            emailConfirmationTokenService.saveConfirmationToken(confirmationToken);

            String link = "http://localhost:9000/authentication-service/auth/confirm?token=" + token;
            emailSenderService.send(
                    registerRequest.getEmail(),
                    "Confirmer votre Courriel.",
                    emailSenderService.buildEmailWithLogo(
                            appuser.getEmail(),
                            "Merci de vous être inscrit. Veuillez cliquer sur le lien ci-dessous pour activer votre compte:",
                            link
                    )
            );

            return AuthenticationResponse.builder()
                    .successmessage("registration successful")
                    .build();

        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .errormessage("An error occurred during registration")
                    .build();
        }
    }



    public void confirmUser(EmailConfirmationToken confirmationToken) {
        final AppUser appuser = confirmationToken.getUser();
        appuser.setIsEnabled(true);
        userAuthRepository.save(appuser);
        emailConfirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
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


















