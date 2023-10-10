package com.example.apigateway.config;



import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = Arrays.asList(
            "/auth/register",
            "/auth/login",
            "/eureka",
            "/auth/social/google",
            "/auth/social/facebook",
            "/auth/getroles",
            "/adherant/CreateUser",
            "/api/user/adherant/getuserbyemail/"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}