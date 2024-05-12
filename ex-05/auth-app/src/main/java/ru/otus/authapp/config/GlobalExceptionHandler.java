package ru.otus.authapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.authapp.exception.InvalidPasswordException;
import ru.otus.authapp.exception.ClientExistsException;
import ru.otus.authapp.exception.ClientNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientExistsException.class)
    public ResponseEntity<?> handleUserExistsException(ClientExistsException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(ClientNotFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
