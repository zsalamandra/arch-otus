package ru.otus.authapp.service;

import ru.otus.authapp.model.LoginRequest;
import ru.otus.authapp.model.RegisterRequest;

import java.util.Map;
import java.util.UUID;

public interface AuthService {

    Map<String, UUID> register(RegisterRequest registerRequest);
    String login(LoginRequest loginRequest);
}
