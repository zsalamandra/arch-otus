package ru.otus.profileapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.profileapp.model.ProfileEntity;
import ru.otus.profileapp.model.ProfileRequest;
import ru.otus.profileapp.model.ProfileResponse;
import ru.otus.profileapp.repository.ProfileRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public UUID create(String email) {
        final ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setEmail(email);

        profileRepository.save(profileEntity);

        return profileEntity.getId();
    }

    @Transactional
    public void update(final UUID id, final ProfileRequest profile) {

        final ProfileEntity profileEntity = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found!"));

        profileEntity.setFullName(profile.getFullName());
        profileEntity.setAge(profile.getAge());
        profileEntity.setFirstName(profile.getFirstName());
        profileEntity.setLastName(profile.getLastName());
        profileEntity.setPassportNumber(profile.getPassportNumber());
    }

    public ProfileResponse read(final UUID id) {

        final ProfileEntity profileEntity = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found!"));

        return ProfileResponse.builder()
                .email(profileEntity.getEmail())
                .age(profileEntity.getAge())
                .fullName(profileEntity.getFullName())
                .firstName(profileEntity.getFirstName())
                .lastName(profileEntity.getFullName())
                .passportNumber(profileEntity.getPassportNumber())
                .build();
    }
}
