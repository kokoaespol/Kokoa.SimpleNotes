package io.github.alicarpio.features.users;

import io.github.alicarpio.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> users = new HashMap();

    @Override
    public User findUserByEmail(String email) {
        return users.values()
                .stream()
                .filter(user -> user.getEmail() == email)
                .findFirst()
                .orElse(null);
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }
}
