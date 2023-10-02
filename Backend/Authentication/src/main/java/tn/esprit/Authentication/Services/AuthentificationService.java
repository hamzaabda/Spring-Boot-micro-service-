//package tn.esprit.Authentication.Services;
//
//
//import lombok.extern.slf4j.Slf4j;
//import lombok.var;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import tn.esprit.Authentication.Interface.IAuthentificationService;
//import tn.esprit.Authentication.JWT.JwtService;
//import tn.esprit.Authentication.Repository.UserAuthRepository;
//import tn.esprit.Authentication.Requests.AuthenticationRequest;
//import tn.esprit.Authentication.Requests.AuthenticationResponse;
//import tn.esprit.Authentication.Requests.RegisterRequest;
//import tn.esprit.Authentication.entities.AppUser;
//import tn.esprit.Authentication.entities.EmailConfirmationToken;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@Service
//public class AuthentificationService implements IAuthentificationService {
//
//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private UserAuthRepository userAuthRepository;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private  EmailConfirmationTokenService emailConfirmationTokenService;
//    @Autowired
//    private  EmailSenderService emailSenderService;
//
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
//
//    public AuthenticationResponse register(RegisterRequest registerRequest) {
//        try {
//
//            Optional<AppUser> existinguser = userAuthRepository.findAppUserByUsername(registerRequest.getUsername());
//            if(existinguser.isPresent())
//            {
//                return AuthenticationResponse.builder()
//                        .errormessage("User already Exisits").build();
//            }
//
//            Optional<AppUser> existingemail = userAuthRepository.findAppUserByEmail(registerRequest.getEmail());
//            if(existingemail.isPresent())
//            {
//                return AuthenticationResponse.builder()
//                        .errormessage("Email already Exisits").build();
//            }
//
//
//                var appuser = AppUser.builder()
//                        .prenom(registerRequest.getFirstname())
//                        .nom(registerRequest.getLastname())
//                        .email(registerRequest.getEmail())
//                        .username(registerRequest.getUsername())
//                        .password(passwordEncoder.encode(registerRequest.getPassword()))
//                        .isEnabled(false)
////                        .role(registerRequest.getRole())
//                        .build();
//
//                userAuthRepository.save(appuser);
//
//            String token = UUID.randomUUID().toString();
//            EmailConfirmationToken confirmationToken = new EmailConfirmationToken(
//                    token,
//                    LocalDateTime.now(),
//                    LocalDateTime.now().plusMinutes(15),
//                    appuser
//            );
//            emailConfirmationTokenService.saveConfirmationToken(confirmationToken);
//
//            String link = "http://localhost:8888/auth/confirm?token=" + token;
//            emailSenderService.send(
//                    registerRequest.getEmail(),
//                    "Confirmer votre Courriel.",
//                    emailSenderService.buildEmailWithLogo(
//                            appuser.getUsername(),
//                            "Merci de vous être inscrit. Veuillez cliquer sur le lien ci-dessous pour activer votre compte:",
//                            link
//                    )
//            );
//
//                return AuthenticationResponse.builder()
//                        .successmessage("registration successful")
//                        .build();
//
//        } catch (Exception e) {
//            return AuthenticationResponse.builder()
//                    .errormessage("An error occurred during registration")
//                    .build();
//        }
//    }
//
//
//
//    public void confirmUser(EmailConfirmationToken confirmationToken) {
//        final AppUser appuser = confirmationToken.getUser();
//        appuser.setIsEnabled(true);
//        userAuthRepository.save(appuser);
//        emailConfirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
//    }
//}
