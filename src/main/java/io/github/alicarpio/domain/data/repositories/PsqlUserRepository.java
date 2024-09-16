package io.github.alicarpio.domain.data.repositories;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.repositories.UserRepository;
import io.github.alicarpio.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PsqlUserRepository implements UserRepository {
    @Override
    public User findUserByEmail(String email) {
        try (Session session = HibernateUtil.getSession()){
           return session.createQuery("FROM User WHERE email = :email",User.class)
                   .setParameter("email", email)
                   .uniqueResult();
        }

    }

    @Override
    public void save(User user) {
        try (Session session = HibernateUtil.getSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                session.persist(user);
                transaction.commit();
            }catch (Exception ex){
                if (transaction == null){
                    ex.printStackTrace();
                }
                transaction.rollback();
            }
            }

    }
}
