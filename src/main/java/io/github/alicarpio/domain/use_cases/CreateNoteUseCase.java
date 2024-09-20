package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Note;
import io.github.alicarpio.domain.validations.exceptions.FailedToRegisterException;
import io.github.alicarpio.repositories.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CreateNoteUseCase {
    private final NoteRepository notes;
    private static final Logger logger = LoggerFactory.getLogger(UserLogInUseCase.class);


    public CreateNoteUseCase(NoteRepository notes) {
        this.notes = notes;
    }

    public void invoke(String title, String content) throws FailedToRegisterException {
        Objects.requireNonNull(content, "Content cannot be null");

        Note newNote = new Note(title, content);
        logger.info("Successfully created note with id: {}", newNote.getId());
        notes.save(newNote);
    }

}
