package io.github.alicarpio.domain.validations;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.validations.exceptions.PasswordTooShortException;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;


public class UserValidator implements Validator<User> {

    private static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    public void validate(User entity) throws ValidationException {
        validatePassword(entity.getPassword());
    }

    private void validatePassword(String password) throws PasswordTooShortException {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordTooShortException(MIN_PASSWORD_LENGTH);
        }
    }
}


