package ru.otus.authapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.authapp.model.LoginRequest;
import ru.otus.authapp.model.RegisterRequest;
import ru.otus.authapp.service.AuthService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public Map<String, UUID> register(@RequestBody RegisterRequest registerRequest) {
        Map<String, UUID> ids = authService.register(registerRequest);

        return ids;
    }

    @PostMapping(value = "/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String token = authService.login(loginRequest);

        response.setHeader("x-auth-token", token);
        response.setHeader("x-jwt-token", token);
        response.setHeader("x-username", loginRequest.getLogin());

        return Map.of("token", token);
    }

    @GetMapping({"/auth/istio/**"})
    public void istio(HttpServletRequest request, HttpServletResponse response) {
        log.info("Method: GET");
        response.setHeader("x-auth-token", request.getHeader("x-auth-token"));
        log.info("Token: " + request.getHeader("x-auth-token"));
    }

    @PatchMapping({"/auth/istio/**"})
    public void istioPatch(HttpServletRequest request, HttpServletResponse response) {
        log.info("Method: PATCH");
        response.setHeader("x-auth-token", request.getHeader("x-auth-token"));
        log.info("Token: " + request.getHeader("x-auth-token"));
    }

}
