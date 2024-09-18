package io.github.alicarpio.domain.validations.exceptions;

public class InvalidCredentialsException extends ValidationException{
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
