package io.github.alicarpio.domain.validations.exceptions;

public class UserNotFoundException extends ValidationException {

    public UserNotFoundException(String name) {
        super(String.format("%s user not found in db", name));
    }
}
