package ru.otus.profileapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {

    private String email;

    private String fullName;

    private Integer age;

    private String firstName;

    private String lastName;

    private String passportNumber;
}
