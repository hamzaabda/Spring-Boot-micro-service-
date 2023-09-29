package tn.esprit.Authentication.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.Authentication.Interface.IAuthentificationService;
import tn.esprit.Authentication.Repository.UserAuthRepository;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.RegisterRequest;
import tn.esprit.Authentication.entities.AppUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class AuthentificationController {

    private final IAuthentificationService authenticationService;

    @Autowired
    private UserAuthRepository userRepository;


    @Autowired
    public AuthentificationController(IAuthentificationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
        try {

            Optional<AppUser> user = userRepository.findAppUserByUsername(authRequest.getUsername());
            if (!user.isPresent()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Username Not Found");
                return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
            }
            else {
                return ResponseEntity.ok(authenticationService.authenticate(authRequest));
            }

        }  catch (BadCredentialsException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
        }
    }


    @PostMapping("/register" )
    public ResponseEntity<?> register(@RequestBody RegisterRequest request)  {

        return ResponseEntity.ok(authenticationService.register(request));
    }


}
