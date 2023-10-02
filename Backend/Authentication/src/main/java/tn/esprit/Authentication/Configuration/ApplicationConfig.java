//package tn.esprit.Authentication.Configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import tn.esprit.Authentication.Repository.UserAuthRepository;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.springframework.http.HttpHeaders.*;
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//import static org.springframework.http.HttpMethod.*;
//import static org.springframework.http.HttpMethod.PUT;
//import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApplicationConfig {
//
//    private final UserAuthRepository repository;
//
//
//
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("http://localhost:4300"); // Replace with the actual origin of your Angular app
//        corsConfiguration.addAllowedOrigin("https://accounts.google.com");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*"); // Allow all HTTP methods
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//
//        return new CorsFilter(source);
//    }
//
//
//}
