package io.github.alicarpio.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static void initialize() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("Hibernate initialized successfully.");
        } catch (Throwable ex) {
            System.err.println("Failed to initialize Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        if (sessionFactory == null) {
            initialize();
        }
        return sessionFactory.openSession();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        System.out.println("Hibernate connection closed.");
    }
}
