package ru.otus.profileapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileRequest {

    private String fullName;

    private Integer age;

    private String firstName;

    private String lastName;

    private String passportNumber;
}
