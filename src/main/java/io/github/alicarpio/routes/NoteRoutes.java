package io.github.alicarpio.routes;

import com.google.gson.Gson;
import io.github.alicarpio.common.StandardResponse;
import io.github.alicarpio.domain.enums.StatusResponse;
import io.github.alicarpio.domain.models.Note;
import io.github.alicarpio.domain.models.Tag;
import io.github.alicarpio.domain.use_cases.CreateNoteUseCase;
import io.github.alicarpio.domain.use_cases.ViewAllNotesUseCase;
import io.github.alicarpio.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static spark.Spark.*;

public class NoteRoutes {

    private static final Logger logger = LoggerFactory.getLogger(UserRoutes.class);
    private final Gson gson;
    private final CreateNoteUseCase createNoteUseCase;

    private final ViewAllNotesUseCase viewAllNotesUseCase;


    public NoteRoutes(CreateNoteUseCase createNoteUseCase, ViewAllNotesUseCase viewAllNotesUseCase) {
        this.createNoteUseCase = createNoteUseCase;
        this.viewAllNotesUseCase = viewAllNotesUseCase;
        this.gson = new Gson();
    }

    public void setupRoutes() {
        post("/note", ((request, response) -> {
            if (request.body() == null || request.body().isEmpty()) {
                halt(400, "Request body cannot be empty");
            }
            String token = request.headers("Authorization").substring(7);

            try {
                var creationReq = gson.fromJson(request.body(), NoteCreationRequest.class);
                var userEmail = JwtUtil.getSubjectFromToken(token);

                createNoteUseCase.invoke(
                        creationReq.getTitle(),
                        creationReq.getContent(),
                        userEmail
                );

                logger.info("Note created successfully");
                response.status(201);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Note created successfully"));
            } catch (Exception e) {
                logger.error("Error occurred while creating the note", e);
                response.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }
        }));

        get("/notes", ((request, response) -> {
            String token = request.headers("Authorization").substring(7);
            try {
                var userEmail = JwtUtil.getSubjectFromToken(token);
                var notes = viewAllNotesUseCase.invoke(userEmail);

                List<GetAllNotesResponse> notesDto = notes.stream()
                        .map(GetAllNotesResponse::new)
                        .toList();

                logger.info("Getting all notes for {}", userEmail);
                response.status(200);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(notesDto)));
            } catch (Exception ex) {
                logger.error("Error getting notes:" + ex.getMessage());
                response.status(500);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Error retrieving notes"));
            }
        }));
    }


    @Getter
    @NoArgsConstructor
    public class GetAllNotesResponse {
        private int id;
        private String title;
        private String content;
        private List<Tag> tags;

        public GetAllNotesResponse(Note note) {
            this.id = note.getId();
            this.title = note.getTitle();
            this.content = note.getContent();
            this.tags = note.getTags();
        }
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class NoteCreationRequest {
        private String title;
        private String content;
    }
}


