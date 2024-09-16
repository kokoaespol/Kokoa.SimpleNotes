package io.github.alicarpio.domain.data.repositories;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.repositories.UserRepository;

public class PsqlUserRepository implements UserRepository {
    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public void save(User user) {

    }
}
