package io.github.alicarpio.features.users;
import com.google.gson.Gson;
import io.github.alicarpio.common.StandardResponse;
import io.github.alicarpio.entities.User;
import io.github.alicarpio.enums.StatusResponse;
import io.github.alicarpio.utils.PasswordUtil;
import spark.Request;
import spark.Response;

import static spark.Spark.*;
class UserApi {
    private final CreateUserHandler handler;
    private final Gson gson = new Gson();

    public UserApi(CreateUserHandler handler) {
        this.handler = handler;
    }

    public void create() {
        post("/users", this::createUser);
    }

    public Object createUser(Request req, Response res){
        CreateUserCommand userDto = gson.fromJson(req.body(), CreateUserCommand.class);
        var createdUser = handler.createUser(userDto);
        return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJson(createdUser)));
    }
}
class CreateUserHandler {
    private final UserRepository users;

    public CreateUserHandler(UserRepository users) {
        this.users = users;
    }
    // To-do: Add validation
    public User createUser(CreateUserCommand command){
        String password = PasswordUtil.hashPassword(command.password());
        return users.save(new User(command.name(), command.surname(), command.email(),password));
    }
}

record CreateUserCommand(String name, String surname, String email, String password) {}
