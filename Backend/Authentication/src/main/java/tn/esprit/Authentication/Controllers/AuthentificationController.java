package tn.esprit.Authentication.Controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.Requests.RegisterRequest;
import tn.esprit.Authentication.Services.AuthService;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin("http://localhost:4300/")
public class AuthentificationController {



    @Autowired
    private AuthService authService;



    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody  AuthenticationRequest authenticationRequest){
    return authService.authenticate(authenticationRequest);
    }

    @PostMapping("/register" )
    public ResponseEntity<?> register(@RequestBody RegisterRequest request)  {

        return ResponseEntity.ok(authService.register(request));
    }
}
