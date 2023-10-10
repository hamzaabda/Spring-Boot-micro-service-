package tn.esprit.Authentication.Controllers;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.Authentication.DTO.TokenDTO;
import tn.esprit.Authentication.JWT.TokenService;
import tn.esprit.Authentication.Requests.AuthenticationRequest;
import tn.esprit.Authentication.Requests.AuthenticationResponse;
import tn.esprit.Authentication.Services.AuthService;
import tn.esprit.Authentication.Services.RoleService;
import tn.esprit.Authentication.Services.UserServiceImpl;
import tn.esprit.Authentication.entities.AppUser;
import tn.esprit.Authentication.entities.Role;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequestMapping("/social")
@RestController
@Slf4j
public class SocialController {

    @Value("${google.id}")
    private String idClient;

    @Value("${mySecret.password}")
    private String password;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    // http://localhost:8888/auth/api/google

    @PostMapping("/google")
    public ResponseEntity<AuthenticationResponse> loginWithGoogle(@RequestBody TokenDTO tokenDTO) throws IOException {

        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier.Builder ver = new GoogleIdTokenVerifier.Builder(transport,factory)
                .setAudience(Collections.singleton(idClient));

        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(),tokenDTO.getToken());

        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        String emailgoogle = payload.getEmail();
        String profilePictureUrl = (String) payload.get("picture");
        String firstName = (String) payload.get("given_name");
        String lastName = (String) payload.get("family_name");

        AppUser user = new AppUser();
        if(userService.ifemailExists(emailgoogle))
        {
            user = userService.getUserByEmail(emailgoogle);
            log.info("========> Email Exists");

        } else{
            user = createUser(emailgoogle,firstName,lastName,profilePictureUrl,firstName+lastName);
            user.setProfileimageurl(profilePictureUrl);
            restTemplate.postForEntity(
                    "http://localhost:9000/api/user/adherant/CreateUser",
                    user,
                    AppUser.class
            );
            log.info("========> Email Dont Exist");

        }

        ///////////////LOGIN///////////////
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(user.getEmail());
        authenticationRequest.setPassword(password);
        //////////////////////////////////


        return new ResponseEntity<AuthenticationResponse>(authService.authenticate(authenticationRequest),HttpStatus.OK);


    }



    @PostMapping("/facebook")
    public ResponseEntity<?> loginWithFacebook(@RequestBody TokenDTO tokenDTO) throws IOException {


        Facebook facebook = new FacebookTemplate(tokenDTO.getToken());
        String [] data = {"email","name","picture","birthday","cover"};


        User user = facebook.fetchObject("me",User.class,data);

        Map<String, Object> extraData = user.getExtraData();

        Map<String, Object> pictureData = (Map<String, Object>) extraData.get("picture");
        Map<String, Object> pictureUrlData = (Map<String, Object>) pictureData.get("data");
        String profilePictureUrl = (String) pictureUrlData.get("url");



        log.info(profilePictureUrl);
        String emailFacebook = user.getEmail();

        String nameFacebook = user.getName();

        String[] parts = nameFacebook.split(" ");

        String firstnamefacebook = parts[0];
        String lastnamefacebook = parts[1];


        AppUser userfacebook = new AppUser();

        if(userService.ifemailExists(emailFacebook))
        {
            userfacebook = userService.getUserByEmail(emailFacebook);
            log.info("========> Email Exists");

        } else{
            userfacebook = createUser(emailFacebook,firstnamefacebook,lastnamefacebook,profilePictureUrl,nameFacebook);

            log.info("========> Email Dont Exist");
            restTemplate.postForEntity(
                    "http://localhost:9000/api/user/adherant/CreateUser",
                    userfacebook,
                    AppUser.class
            );

        }

        ///////////////LOGIN///////////////
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(user.getEmail());
        authenticationRequest.setPassword(password);
        //////////////////////////////////


        return new ResponseEntity<AuthenticationResponse>(authService.authenticate(authenticationRequest),HttpStatus.OK);

    }


    private AppUser createUser(String email,String nom,String prenom,String profilepicURL,String username)
    {
        AppUser user = new AppUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setProfileimageurl(profilepicURL);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsEnabled(true);

        List<Role> roles = roleService.getRoles();
        ///Le 1 er role dans la DB PARTICIPANT
        user.getRoles().add(roles.get(0));
        return userService.createUser(user);

    }

}
