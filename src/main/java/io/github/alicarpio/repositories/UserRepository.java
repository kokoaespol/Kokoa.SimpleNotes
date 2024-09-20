package io.github.alicarpio.repositories;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.validations.exceptions.FailedToFindEntityException;
import io.github.alicarpio.domain.validations.exceptions.FailedToRegisterException;

import java.util.UUID;

public interface UserRepository {

    void save(User user) throws FailedToRegisterException;
    User findUserByEmail(String email) throws FailedToFindEntityException;


}
