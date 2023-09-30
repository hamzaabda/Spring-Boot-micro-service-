package tn.esprit.Authentication.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import tn.esprit.Authentication.Interface.IAuthentificationService;
import tn.esprit.Authentication.Repository.UserAuthRepository;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.RegisterRequest;
import tn.esprit.Authentication.Services.EmailConfirmationTokenService;
import tn.esprit.Authentication.entities.EmailConfirmationToken;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class AuthentificationController {

    private final IAuthentificationService authenticationService;

    @Autowired
    private UserAuthRepository userRepository;

    private final EmailConfirmationTokenService emailConfirmationTokenService;


    @Autowired
    public AuthentificationController(IAuthentificationService authenticationService, EmailConfirmationTokenService emailConfirmationTokenService) {
        this.authenticationService = authenticationService;
        this.emailConfirmationTokenService = emailConfirmationTokenService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {

                return ResponseEntity.ok(authenticationService.authenticate(authRequest));


    }


    @PostMapping("/register" )
    public ResponseEntity<?> register(@RequestBody RegisterRequest request)  {

        return ResponseEntity.ok(authenticationService.register(request));
    }


    @GetMapping("/confirm")
    public void confirmUser(@RequestParam("token") String token) {
        Optional<EmailConfirmationToken> confirmationToken = emailConfirmationTokenService.getByToken(token);

//        if (!confirmationToken.isPresent()) {
//            // Redirect to the login page with an error message
//            return new RedirectView("http://localhost:4300/auth/login?emailConfirmed=false");
//        }

        authenticationService.confirmUser(confirmationToken.get());

        // Redirect to the login page with a success message
//        return new RedirectView("http://localhost:4300/auth/login?emailConfirmed=true");
    }

}
