package io.github.alicarpio.features.users;

import io.github.alicarpio.entities.User;

public interface UserRepository {
    User findUserByEmail(String email);
    User save(User user);
}
