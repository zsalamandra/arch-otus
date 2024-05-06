package ru.otus.ex03.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/error")
public class ErrorController {

    @GetMapping
    public ResponseEntity<?> error() {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Simulated Error");
    }
}
