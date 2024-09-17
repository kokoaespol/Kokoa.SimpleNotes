package io.github.alicarpio.routes;

import com.google.gson.Gson;
import io.github.alicarpio.common.StandardResponse;
import io.github.alicarpio.domain.enums.StatusResponse;
import io.github.alicarpio.domain.use_cases.UserRegistrationUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class UserRoutes {
    private static final Logger logger = LoggerFactory.getLogger(UserRoutes.class);
    private final UserRegistrationUseCase userRegistrationUseCase;
    private final Gson gson;

    public UserRoutes(UserRegistrationUseCase userRegistrationUseCase) {
        this.userRegistrationUseCase = userRegistrationUseCase;
        this.gson = new Gson();
    }

    public void setupRoutes(){
        post("/users", (request, response) ->{
            try {
                var registrationReq = gson.fromJson(request.body(), UserRegistrationRequest.class);
                userRegistrationUseCase.invoke(
                        registrationReq.getName(),
                        registrationReq.getSurname(),
                        registrationReq.getEmail(),
                        registrationReq.getPassword()
                );
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "User created successfully"));
            }catch (Exception e){
                logger.error("Error occurred while creating user", e);
                response.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }
        });
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
