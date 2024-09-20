package io.github.alicarpio.domain.validations.exceptions;

public class FailedToFetchException extends ValidationException{
    public FailedToFetchException(String entity) {
        super(String.format("Failed to fetch %s", entity));
    }
}
