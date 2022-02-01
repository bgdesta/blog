package edu.miu.cs544.apigateway.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> publicApi= List.of(
            "/security/register",
            "/security/login"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> publicApi
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}