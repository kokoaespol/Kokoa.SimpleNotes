package io.github.alicarpio.repositories;

import io.github.alicarpio.domain.models.User;

import java.util.UUID;

public interface UserRepository {

    void save(User user);
    User findUserByEmail(String email);


}
