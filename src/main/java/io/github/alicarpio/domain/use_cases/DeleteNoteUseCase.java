package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Note;
import io.github.alicarpio.domain.validations.exceptions.FailedToFindEntityException;
import io.github.alicarpio.repositories.NoteRepository;

import java.util.Objects;

public class DeleteNoteUseCase {

    private final NoteRepository notes;


    public DeleteNoteUseCase(NoteRepository notes) {
        this.notes = notes;
    }

    public Boolean invoke(int noteId) throws FailedToFindEntityException {

        Objects.requireNonNull(noteId, "The note id cannot be null");

        Note note = notes.findById(noteId);

        if (note == null) {
            throw new FailedToFindEntityException("note with ID" + noteId);
        }

        return notes.delete(noteId);
    }
}
