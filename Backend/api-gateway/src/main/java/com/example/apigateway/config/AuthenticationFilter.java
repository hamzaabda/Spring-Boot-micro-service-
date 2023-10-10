package com.example.apigateway.config;
import com.example.apigateway.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Objects;

//@Component
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
//
//    @Autowired
//    private RouteValidator validator;
//
//    //    @Autowired
////    private RestTemplate template;
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public AuthenticationFilter() {
//        super(Config.class);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            if (validator.isSecured.test(exchange.getRequest())) {
//                //header contains token or not
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    throw new RuntimeException("missing authorization header");
//                }
//
//                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//                if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                    authHeader = authHeader.substring(7);
//                }
//                try {
////                    //REST call to AUTH service
////                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
//                    System.out.println("Exchange : " +exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
//                    List<String> roles = jwtUtil.extractRoles(authHeader);
//                    System.out.println("Roles : " +roles.get(0));
//                    System.out.println("URL : " +exchange.getRequest().getURI().getPath());
//
//                    String uriPath = exchange.getRequest().getURI().getPath().replace("/api", "");
//
//                    jwtUtil.validateToken(authHeader);
//
//                    if (!(roles.contains("[ROLE_SUPERADMIN]") && uriPath.startsWith("/Superadmin"))) {
//                        System.out.println("invalid access Roles...!");
//                        throw new RuntimeException("un authorized access to application Roles");
//                    }
//
//                    if (jwtUtil.validateJwtToken(authHeader)==false) {
//                        throw new RuntimeException("Jwt token is not valid");
//                    }
//
//
//
//
//                } catch (Exception e) {
//                    System.out.println("invalid access...!");
//                    throw new RuntimeException("un authorized access to application");
//                }
//            }
//            return chain.filter(exchange);
//        });
//    }
//
//    public static class Config {
//
//    }
//}


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    //    @Autowired
//    private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                System.out.println("Exchange : " + Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0));
                List<String> roles = jwtUtil.extractRoles(authHeader);
                System.out.println("Roles : " +roles);
                System.out.println("URL : " +exchange.getRequest().getURI().getPath());

                ////////////Filer the path////////////////////////////
                String uriPath = exchange.getRequest().getURI().getPath().replace("/api", "");
                /////////////////////////////////////////////////////

                boolean hasSuperAdminRole = roles.stream().anyMatch(innerList -> innerList.contains("ROLE_SUPERADMIN"));
                boolean hasAdminRole = roles.stream().anyMatch(innerList -> innerList.contains("ROLE_ADMIN"));
                boolean hasParticipantRole = roles.stream().anyMatch(innerList -> innerList.contains("ROLE_PARTICIPANT"));
                boolean hasPartenaireRole = roles.stream().anyMatch(innerList -> innerList.contains("ROLE_PARTENAIRE"));
                boolean hasOrganisateurRole = roles.stream().anyMatch(innerList -> innerList.contains("ROLE_ORGANISATEUR"));

                System.out.println("hasAdminRole: "+hasAdminRole);
                System.out.println("hasSuperAdminRole: "+hasSuperAdminRole);
                System.out.println("hasParticipantRole: "+hasParticipantRole);
                System.out.println("hasPartenaireRole: "+hasPartenaireRole);
                System.out.println("hasOrganisateurRole: "+hasOrganisateurRole);

                /////////////////////////Securite ROLE + Path ///////////////////////////////////////////
                if (!jwtUtil.validateJwtToken(authHeader)) {
                    throw new RuntimeException("Jwt token is not valid");
                }
                else if (uriPath.startsWith("/user/admin/") && !hasSuperAdminRole) {
                    throw new RuntimeException("You Dont have access to this Super Admin ressource !!!");
                }



            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}