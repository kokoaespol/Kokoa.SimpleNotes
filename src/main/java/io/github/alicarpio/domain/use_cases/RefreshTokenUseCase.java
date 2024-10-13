package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.validations.exceptions.FailedToFindEntityException;
import io.github.alicarpio.domain.validations.exceptions.InvalidTokenException;
import io.github.alicarpio.repositories.UserRepository;
import io.github.alicarpio.utils.JwtUtil;

public class RefreshTokenUseCase {
    private final UserRepository users;

    public RefreshTokenUseCase(UserRepository users) {
        this.users = users;
    }

    public String execute(String refreshToken) throws FailedToFindEntityException, InvalidTokenException {
        if (JwtUtil.validateToken(refreshToken)) {
            String userEmail = JwtUtil.getSubjectFromToken(refreshToken);
            User user = users.findUserByEmail(userEmail);

            if (user != null) {
                return JwtUtil.generateRefreshToken(userEmail);
            }
        }
        throw new InvalidTokenException();
    }
}
