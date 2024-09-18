package io.github.alicarpio.domain.validations.exceptions;

public class PasswordTooShortException extends ValidationException{
    public PasswordTooShortException(int length) {
        super(String.format("The password needs to be more than %d characters long", length));
    }
}
