package io.github.alicarpio.routes;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.alicarpio.common.StandardResponse;
import io.github.alicarpio.domain.enums.StatusResponse;
import io.github.alicarpio.domain.use_cases.RefreshTokenUseCase;
import io.github.alicarpio.domain.use_cases.UserLogInUseCase;
import io.github.alicarpio.domain.use_cases.UserRegistrationUseCase;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.halt;
import static spark.Spark.post;

public class UserRoutes {
    private static final Logger logger = LoggerFactory.getLogger(UserRoutes.class);
    private final UserRegistrationUseCase userRegistrationUseCase;
    private final UserLogInUseCase userLogInUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final Gson gson;

    public UserRoutes
            (
                    UserRegistrationUseCase userRegistrationUseCase,
                    UserLogInUseCase userLogInUseCase,
                    RefreshTokenUseCase refreshTokenUseCase
            ) {
        this.userRegistrationUseCase = userRegistrationUseCase;
        this.userLogInUseCase = userLogInUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.gson = new Gson();
    }

    public void setupRoutes() {
        post("/register", (request, response) -> {
            if (request.body() == null || request.body().isEmpty()) {
                halt(400, "Request body cannot be empty");
            }

            try {
                var registrationReq = gson.fromJson(request.body(), UserRegistrationRequest.class);
                userRegistrationUseCase.invoke(
                        registrationReq.getName(),
                        registrationReq.getSurname(),
                        registrationReq.getEmail(),
                        registrationReq.getPassword()
                );
                logger.info("User created successfully");
                response.status(201);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "User created successfully"));
            } catch (Exception e) {
                logger.error("Error occurred while creating user", e);
                response.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }
        });

        post("/login", (req, res) -> {
            if (req.body() == null || req.body().isEmpty()) {
                halt(400, "Request body cannot be empty");
            }

            try {
                LoginRequest loginRequest = gson.fromJson(req.body(), LoginRequest.class);
                var tokens = userLogInUseCase.invoke(loginRequest.getEmail(), loginRequest.getPassword());
                JsonElement tokenJson = tokens.toJsonElement();
                res.type("application/json");
                res.status(200);
                logger.info("Token created successfully");
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, tokenJson));
            } catch (ValidationException ex) {
                logger.error("An error ocurred while logging in");
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, ex.getMessage()));
            }
        });

        post("/refresh", (req, res) -> {
            String refreshToken = req.headers("Refresh-Token");

            if (refreshToken == null) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Refresh token is required"));
            }

            try {
                String newToken = refreshTokenUseCase.execute(refreshToken);
                JsonElement tokenJson = new JsonPrimitive(newToken);
                res.type("application/json");
                res.status(200);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, tokenJson));
            } catch (Exception e) {
                res.status(401);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Invalid refresh token"));
            }
        });
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    class LoginRequest {
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserRegistrationRequest {
        private String name;
        private String surname;
        private String email;
        private String password;
    }
}
