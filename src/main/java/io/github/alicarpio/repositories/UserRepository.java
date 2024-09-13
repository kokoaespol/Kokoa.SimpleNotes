package alicarpio.repositories;

import alicarpio.entities.User;

public interface UserRepository {
    User findUserByEmail(String email);
    void save();
}
