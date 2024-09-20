package io.github.alicarpio.repositories;

import io.github.alicarpio.domain.models.Note;
import io.github.alicarpio.domain.validations.exceptions.FailedToFetchException;
import io.github.alicarpio.domain.validations.exceptions.FailedToRegisterException;

import java.util.List;

public interface NoteRepository {
    void save(Note note) throws FailedToRegisterException;

    Boolean update(Note note);

    Boolean delete(int note_id);

    List<Note> getAllNotes() throws FailedToFetchException;

    List<Note> getNotesByTag(int tag_id);
}
