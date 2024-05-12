package ru.otus.authapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest {

    private String email;
    private String login;
    private String password;
}