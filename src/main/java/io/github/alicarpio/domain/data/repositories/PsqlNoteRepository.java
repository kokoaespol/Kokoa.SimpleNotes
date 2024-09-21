package io.github.alicarpio.domain.data.repositories;

import io.github.alicarpio.domain.models.Note;
import io.github.alicarpio.domain.validations.exceptions.FailedToFetchException;
import io.github.alicarpio.domain.validations.exceptions.FailedToFindEntityException;
import io.github.alicarpio.domain.validations.exceptions.FailedToRegisterException;
import io.github.alicarpio.repositories.NoteRepository;
import io.github.alicarpio.routes.UserRoutes;
import io.github.alicarpio.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class PsqlNoteRepository implements NoteRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRoutes.class);

    @Override
    public void save(Note note) throws FailedToRegisterException {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(note);
                transaction.commit();
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                logger.error("Error registering note", ex);
                throw new FailedToRegisterException("note");
            }
        }
    }

    @Override
    public Boolean update(Note note) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Note note = session.get(Note.class, id);
                if (note != null) {
                    session.remove(note);
                    transaction.commit();
                    return true;
                } else {
                    return false;
                }
            } catch (Exception ex) {
                transaction.rollback();
                logger.error("An error occurred while deleting");
                return false;
            }
        }
    }

    @Override
    public Note findById(int id) throws FailedToFindEntityException {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                            "SELECT DISTINCT n FROM Note n " +
                                    "LEFT JOIN FETCH n.tags " +
                                    "WHERE n.id = :id", Note.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception ex) {
            logger.error("Error fetching note:");
            throw new FailedToFindEntityException("note");
        }
    }

    @Override
    public List<Note> getAllNotes(UUID user_id) throws FailedToFetchException {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                            "SELECT DISTINCT n FROM Note n " +
                                    "LEFT JOIN FETCH n.tags " +
                                    "WHERE n.user.id = :user_id", Note.class)
                    .setParameter("user_id", user_id)
                    .getResultList();
        } catch (Exception ex) {
            logger.error("Error fetching notes:");
            throw new FailedToFetchException("notes");
        }
    }

    @Override
    public List<Note> getNotesByTag(int tag_id) {
        return null;
    }
}
