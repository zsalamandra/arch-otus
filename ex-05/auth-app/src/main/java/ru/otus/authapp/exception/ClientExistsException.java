package ru.otus.authapp.exception;

public class ClientExistsException extends RuntimeException {

    public ClientExistsException(String message) {
        super(message);
    }
}
