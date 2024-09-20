package io.github.alicarpio.domain.data.repositories;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.validations.exceptions.FailedToFindEntityException;
import io.github.alicarpio.domain.validations.exceptions.FailedToRegisterException;
import io.github.alicarpio.repositories.UserRepository;
import io.github.alicarpio.routes.UserRoutes;
import io.github.alicarpio.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PsqlUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRoutes.class);

    @Override
    public void save(User user) throws FailedToRegisterException{
        try (Session session = HibernateUtil.getSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                session.persist(user);
                transaction.commit();
            }catch (Exception ex){
                if (transaction != null) {
                    transaction.rollback();
                }
                logger.error("Error registering user: " + user.getName(), ex);
                throw new FailedToRegisterException("user");
            }
        }
    }
    @Override
    public User findUserByEmail(String email) throws FailedToFindEntityException {
        try (Session session = HibernateUtil.getSession()){
           return session.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                   .setParameter("email", email.trim().toLowerCase())
                   .getSingleResult();
        } catch (Exception ex){
            logger.error("Error finding the user by email: " + email, ex);
            throw new FailedToFindEntityException("user");
        }
    }
}
