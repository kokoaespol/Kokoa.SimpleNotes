package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Note;
import io.github.alicarpio.domain.validations.exceptions.FailedToFetchException;
import io.github.alicarpio.domain.validations.exceptions.FailedToFindEntityException;
import io.github.alicarpio.repositories.NoteRepository;
import io.github.alicarpio.repositories.UserRepository;

import java.util.List;
import java.util.Objects;

public class ViewAllNotesUseCase {
    private final NoteRepository notes;
    private final UserRepository users;

    public ViewAllNotesUseCase(NoteRepository notes, UserRepository users) {
        this.notes = notes;
        this.users = users;
    }

    public List<Note> invoke(String email) throws FailedToFetchException, FailedToFindEntityException {
        Objects.nonNull("Email cannot be null");
        return notes.getAllNotes(users.findUserByEmail(email).getId());
    }
}
