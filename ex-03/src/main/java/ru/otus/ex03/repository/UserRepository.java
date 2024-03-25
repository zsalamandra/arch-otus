package ru.otus.ex03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.ex03.model.User;

@RepositoryRestResource(path = "user")
public interface UserRepository extends JpaRepository<User, Long> {
}
