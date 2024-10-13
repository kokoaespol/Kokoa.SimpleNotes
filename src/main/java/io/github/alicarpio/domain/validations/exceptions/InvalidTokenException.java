package io.github.alicarpio.domain.validations.exceptions;

public class InvalidTokenException extends ValidationException {
    public InvalidTokenException() {
        super("No valid token provided");
    }
}
