package ru.otus.profileapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ru.otus.profileapp.exception.NotAuthorizedException;
import ru.otus.profileapp.model.ProfileRequest;
import ru.otus.profileapp.model.ProfileResponse;
import ru.otus.profileapp.service.ProfileService;

import java.util.Enumeration;
import java.util.UUID;

import static ru.otus.profileapp.utils.TokenUtils.getProfileIdFromPayload;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping(path = "/profiles")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UUID create(@RequestBody String email) {
        Assert.hasText(email, "email must not be blank");

        return profileService.create(email);
    }

    @PatchMapping(path = "/profiles/{id}")
    public void update(@PathVariable(name = "id") UUID id,
                       @RequestBody ProfileRequest profileRequest,
                       @RequestHeader("x-jwt-token") String token) {

        final UUID userProfileId = getProfileIdFromPayload(token);
        if (id.equals(userProfileId)) {
            profileService.update(id, profileRequest);
        } else {
            throw new NotAuthorizedException("Unauthorized!");
        }
    }

    @GetMapping(path = "/profiles/{id}")
    public ProfileResponse read(HttpServletRequest request,
                                @PathVariable(name = "id") UUID id,
                                @RequestHeader(name = "x-jwt-token") String token) {

        System.out.println("!!! HEADERS");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            log.info(key + " " + value);
        }

        final UUID userProfileId = getProfileIdFromPayload(token);
        log.info("extracted profileId: " + userProfileId);

        if (id.equals(userProfileId)) {
            return profileService.read(userProfileId);
        } else {
            throw new NotAuthorizedException("Unauthorized!");
        }
    }

}
