package ru.otus.ex01.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/health")
    public ResponseEntity<ResponseModel> getResponse() {
        return ResponseEntity.ok(new ResponseModel("OK"));
    }
}
