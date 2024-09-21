package io.github.alicarpio.routes;

import com.google.gson.Gson;
import io.github.alicarpio.common.StandardResponse;
import io.github.alicarpio.domain.enums.StatusResponse;
import io.github.alicarpio.domain.use_cases.CreateNoteUseCase;
import io.github.alicarpio.domain.use_cases.UserLogInUseCase;
import io.github.alicarpio.repositories.UserRepository;
import io.github.alicarpio.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class NoteRoutes {

    private static final Logger logger = LoggerFactory.getLogger(UserRoutes.class);
    private final Gson gson;
    private final CreateNoteUseCase createNoteUseCase;


    public NoteRoutes(CreateNoteUseCase createNoteUseCase) {
        this.createNoteUseCase = createNoteUseCase;
        this.gson = new Gson();
    }

    public void setupRoutes(){
        post("/note", ((request, response) -> {
            if (request.body() == null || request.body().isEmpty()) {
                halt(400, "Request body cannot be empty");
            }
            String token = request.headers("Authorization").substring(7);

            try{
                var creationReq = gson.fromJson(request.body(),NoteCreationRequest.class);
                var userEmail = JwtUtil.getSubjectFromToken(token);

                createNoteUseCase.invoke(
                        creationReq.getTitle(),
                        creationReq.getContent(),
                        userEmail
                );

                logger.info("Note created successfully");
                response.status(201);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,"Note created successfully"));
            }catch (Exception e){
                logger.error("Error occurred while creating the note", e);
                response.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }
        }));
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class NoteCreationRequest {
        private String title;
        private String content;
    }
}


