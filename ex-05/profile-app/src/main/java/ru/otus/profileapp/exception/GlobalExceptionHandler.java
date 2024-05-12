package ru.otus.profileapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<?> handleProfileNotFoundException(ProfileNotFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<?> handleNotAuthorizedException(NotAuthorizedException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
