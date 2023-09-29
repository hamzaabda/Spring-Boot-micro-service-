package tn.esprit.Authentication.Interface;

import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.Requests.RegisterRequest;

public interface IAuthentificationService {
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public AuthenticationResponse register(RegisterRequest registerRequest);

}
