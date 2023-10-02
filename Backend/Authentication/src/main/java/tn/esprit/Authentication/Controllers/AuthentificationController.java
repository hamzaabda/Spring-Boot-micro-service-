package tn.esprit.Authentication.Controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.Services.AuthService;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin("http://localhost:4300/")
public class AuthentificationController {



    @Autowired
    private AuthService authService;



    @PostMapping("/login")
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){

    return authService.authenticate(authenticationRequest);

    }
}
