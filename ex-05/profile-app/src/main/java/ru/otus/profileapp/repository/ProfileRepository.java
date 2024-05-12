package ru.otus.profileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.profileapp.model.ProfileEntity;

import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {
}