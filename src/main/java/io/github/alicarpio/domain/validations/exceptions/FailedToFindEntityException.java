package io.github.alicarpio.domain.validations.exceptions;

public class FailedToFindEntityException extends ValidationException {
    public FailedToFindEntityException(String entity) {
        super(String.format("Failed to find %s", entity));
    }
}
