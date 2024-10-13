package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.Dtos.TokenPair;
import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.validations.exceptions.InvalidCredentialsException;
import io.github.alicarpio.domain.validations.exceptions.UserNotFoundException;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;
import io.github.alicarpio.repositories.UserRepository;
import io.github.alicarpio.utils.JwtUtil;
import io.github.alicarpio.utils.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserLogInUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UserLogInUseCase.class);

    private final UserRepository users;

    public UserLogInUseCase(UserRepository users) {
        this.users = users;
    }

    public TokenPair invoke(String email, String password) throws ValidationException {
        User user = users.findUserByEmail(email);
        logger.info("Attempting login for email: {}", email);

        if (user == null) {
            throw new UserNotFoundException(email);
        }

        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }


        String accessToken = JwtUtil.generateAccessToken(email);
        String refreshToken = JwtUtil.generateRefreshToken(email);

        return new TokenPair(accessToken, refreshToken);
    }
}
