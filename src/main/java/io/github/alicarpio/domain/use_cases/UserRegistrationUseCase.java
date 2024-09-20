package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.validations.Validator;
import io.github.alicarpio.domain.validations.exceptions.FailedToRegisterException;
import io.github.alicarpio.repositories.UserRepository;
import io.github.alicarpio.utils.PasswordUtil;

public class UserRegistrationUseCase  {
    private final UserRepository users;
    private final Validator<User> validator;


    public UserRegistrationUseCase(UserRepository users, Validator<User> validator)
    {
        this.users = users;
        this.validator = validator;
    }

    //TO-DO: Implement validations
    public void invoke(String name, String surname, String email, String password) throws FailedToRegisterException{
        String hashPassword = PasswordUtil.hashPassword(password);
        User user = new User(name,surname, email, hashPassword);
        users.save(user);
    }

}
