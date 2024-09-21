package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Note;
import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.validations.exceptions.FailedToFindEntityException;
import io.github.alicarpio.domain.validations.exceptions.FailedToRegisterException;
import io.github.alicarpio.repositories.NoteRepository;
import io.github.alicarpio.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CreateNoteUseCase {
    private final NoteRepository notes;
    private final UserRepository users;
    private static final Logger logger = LoggerFactory.getLogger(UserLogInUseCase.class);


    public CreateNoteUseCase(NoteRepository notes, UserRepository users) {
        this.notes = notes;
        this.users = users;
    }

    public void invoke(String title, String content, String userEmail) throws FailedToRegisterException, FailedToFindEntityException {
        Objects.requireNonNull(content, "Content cannot be null");
        Objects.requireNonNull(userEmail, "User email cannot be null");

        Note newNote = new Note(title, content);
        User user = users.findUserByEmail(userEmail);
        newNote.setUser(user);

        logger.info("Successfully created note with id: {}", newNote.getId());
        notes.save(newNote);
    }

}
