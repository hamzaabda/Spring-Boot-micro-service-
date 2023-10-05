package tn.esprit.Authentication.Controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Authentication.JWT.TokenService;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.Requests.RegisterRequest;
import tn.esprit.Authentication.Services.AuthService;
import tn.esprit.Authentication.Services.EmailConfirmationTokenService;
import tn.esprit.Authentication.entities.EmailConfirmationToken;

import java.util.Optional;

@RestController
@RequestMapping("/")
@Slf4j
public class AuthentificationController {


    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthService authService;

    @Autowired
    private EmailConfirmationTokenService emailConfirmationTokenService;


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody  AuthenticationRequest authenticationRequest){
    return authService.authenticate(authenticationRequest);
    }

    @PostMapping("/register" )
    public ResponseEntity<?> register(@RequestBody RegisterRequest request)  {

        return ResponseEntity.ok(authService.register(request));
    }


    @GetMapping("/confirm")
    public void confirmUser(@RequestParam("token") String token) {
        Optional<EmailConfirmationToken> confirmationToken = emailConfirmationTokenService.getByToken(token);


        authService.confirmUser(confirmationToken.get());

    }


    @GetMapping("/validate")
    public String  validateToken(@RequestParam("token") String token) {
        tokenService.validateToken(token);
        return "Token is valid";
    }

}
