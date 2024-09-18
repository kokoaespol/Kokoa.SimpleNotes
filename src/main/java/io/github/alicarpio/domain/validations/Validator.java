package io.github.alicarpio.domain.validations;

import io.github.alicarpio.domain.validations.exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;

}
