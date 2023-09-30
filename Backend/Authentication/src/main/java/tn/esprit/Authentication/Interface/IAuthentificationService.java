package tn.esprit.Authentication.Interface;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.Requests.RegisterRequest;
import tn.esprit.Authentication.entities.EmailConfirmationToken;

public interface IAuthentificationService {
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public AuthenticationResponse register(RegisterRequest registerRequest);
    public void confirmUser(EmailConfirmationToken confirmationToken);

}
