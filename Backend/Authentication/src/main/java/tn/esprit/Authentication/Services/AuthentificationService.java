package tn.esprit.Authentication.Services;


import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.Interface.IAuthentificationService;
import tn.esprit.Authentication.JWT.JwtService;
import tn.esprit.Authentication.Repository.UserAuthRepository;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.Requests.RegisterRequest;
import tn.esprit.Authentication.entities.AppUser;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AuthentificationService implements IAuthentificationService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Optional<AppUser> user = userAuthRepository.findAppUserByUsername(request.getUsername());
        if(!user.isPresent())
        {
            return AuthenticationResponse.builder().errormessage("Username Not found").build();
        }

        AppUser user1 = user.get();

         if(user1.getIsEnabled() == null || !user1.getIsEnabled())
        {
            return AuthenticationResponse.builder().errormessage("Confirm Email Before Login").build();
        }
        else
        {


            var access_token = jwtService.generateToken(user1);
            var refreshToken = jwtService.generateRefreshToken(user1);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token",access_token);
            tokens.put("refresh_token",refreshToken);

            return AuthenticationResponse.builder()
                    .tokens(tokens)
                    .successmessage("Authentification Successful")
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


                var appuser = AppUser.builder()
                        .prenom(registerRequest.getFirstname())
                        .nom(registerRequest.getLastname())
                        .email(registerRequest.getEmail())
                        .username(registerRequest.getUsername())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .isEnabled(false)
                        .role(registerRequest.getRole())
                        .build();

                userAuthRepository.save(appuser);


                return AuthenticationResponse.builder()
                        .successmessage("registration successful")
                        .build();

        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .errormessage("An error occurred during registration")
                    .build();
        }
    }

}
