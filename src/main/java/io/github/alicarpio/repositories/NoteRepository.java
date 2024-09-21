package io.github.alicarpio.repositories;

import io.github.alicarpio.domain.models.Note;
import io.github.alicarpio.domain.validations.exceptions.FailedToFetchException;
import io.github.alicarpio.domain.validations.exceptions.FailedToFindEntityException;
import io.github.alicarpio.domain.validations.exceptions.FailedToRegisterException;

import java.util.List;
import java.util.UUID;

public interface NoteRepository {
    void save(Note note) throws FailedToRegisterException;

    Boolean update(Note note);

    Boolean delete(int id);

    Note findById(int id) throws FailedToFindEntityException;

    List<Note> getAllNotes(UUID user_id) throws FailedToFetchException;

    List<Note> getNotesByTag(int tag_id);
}
