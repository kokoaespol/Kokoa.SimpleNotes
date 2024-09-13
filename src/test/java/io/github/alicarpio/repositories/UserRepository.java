package io.github.alicarpio.repositories;

import io.github.alicarpio.domain.User;

public interface UserRepository {
    User findUserByEmail(String email);
    void save();
}
