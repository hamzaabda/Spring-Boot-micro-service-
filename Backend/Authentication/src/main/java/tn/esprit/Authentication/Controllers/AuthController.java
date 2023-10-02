//package tn.esprit.Authentication.Controllers;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import tn.esprit.Authentication.Repository.UserAuthRepository;
//import tn.esprit.Authentication.Requests.AuthenticationRequest;
//import tn.esprit.Authentication.Requests.RegisterRequest;
//import tn.esprit.Authentication.Services.EmailConfirmationTokenService;
//import tn.esprit.Authentication.entities.EmailConfirmationToken;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/")
//@Slf4j
//@CrossOrigin("http://localhost:4300/")
//public class AuthController {
//
//    private final IAuthentificationService authenticationService;
//
//    @Autowired
//    private UserAuthRepository userRepository;
//
//    private final EmailConfirmationTokenService emailConfirmationTokenService;
//
//
//    @Autowired
//    public AuthController(IAuthentificationService authenticationService, EmailConfirmationTokenService emailConfirmationTokenService) {
//        this.authenticationService = authenticationService;
//        this.emailConfirmationTokenService = emailConfirmationTokenService;
//    }
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
//
//                return ResponseEntity.ok(authenticationService.authenticate(authRequest));
//
//
//    }
//
//
//    @PostMapping("/register" )
//    public ResponseEntity<?> register(@RequestBody RegisterRequest request)  {
//
//        return ResponseEntity.ok(authenticationService.register(request));
//    }
//
//
//    @GetMapping("/confirm")
//    public void confirmUser(@RequestParam("token") String token) {
//        Optional<EmailConfirmationToken> confirmationToken = emailConfirmationTokenService.getByToken(token);
//
////        if (!confirmationToken.isPresent()) {
////            // Redirect to the login page with an error message
////            return new RedirectView("http://localhost:4300/auth/login?emailConfirmed=false");
////        }
//
//        authenticationService.confirmUser(confirmationToken.get());
//
//        // Redirect to the login page with a success message
////        return new RedirectView("http://localhost:4300/auth/login?emailConfirmed=true");
//    }
//    @GetMapping("/custom-logout")
//    public String customLogout(HttpServletRequest request, HttpServletResponse response) {
//        // Perform any additional logout actions here (e.g., invalidating sessions, clearing cookies)
//        // ...
//
//        // Logout the user
//
//        SecurityContextHolder.clearContext();
//        // Redirect to a custom URL after logout
//        return "redirect:/custom-login?logout";
//    }
//
////    @GetMapping("/currentuser")
////    public void currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken, HttpServletResponse response)
////            throws IOException {
////
////        // Get user attributes
////        Map<String, Object> userAttributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
////
////        // Perform any necessary processing with userAttributes
////        log.info(userAttributes.toString());
////        // Redirect to the dashboard URL
////        response.sendRedirect("http://localhost:4300/dashboard");
////    }
//
//
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        // Invalidate the user's session
//        log.info(SecurityContextHolder.getContext().toString());
//
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//
//        // Clear the SecurityContextHolder
//        SecurityContextHolder.clearContext();
//        log.info(SecurityContextHolder.getContext().toString());
//
//        return "Logged out successfully!";
//    }
//
//}
