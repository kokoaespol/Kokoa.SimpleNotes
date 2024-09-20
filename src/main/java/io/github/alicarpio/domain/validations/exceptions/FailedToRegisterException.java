package io.github.alicarpio.domain.validations.exceptions;

public class FailedToRegisterException extends ValidationException {
    public FailedToRegisterException(String entity) {
        super(String.format("Failed to register %s", entity));
    }
}
