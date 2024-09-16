package io.github.alicarpio.repositories;

import io.github.alicarpio.domain.models.User;

public interface UserRepository {

    User findUserByEmail(String email);
    void save(User user);

}
