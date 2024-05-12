package ru.otus.authapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.authapp.model.Health;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public Health health() {
        return Health.builder()
                .status(Health.HealthStatus.OK)
                .build();
    }
}
