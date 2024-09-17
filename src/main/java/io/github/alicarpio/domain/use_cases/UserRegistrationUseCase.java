package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.repositories.UserRepository;
import io.github.alicarpio.utils.PasswordUtil;

public class UserRegistrationUseCase  {
    private final UserRepository users;

    public UserRegistrationUseCase(UserRepository users) {
        this.users = users;
    }

    public void invoke(String name, String surname, String email, String password){
        String hashPassword = PasswordUtil.hashPassword(password);
        User user = new User(name,surname, email, hashPassword);
        users.save(user);
    }

}
