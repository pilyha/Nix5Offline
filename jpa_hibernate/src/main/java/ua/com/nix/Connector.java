package ua.com.nix;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connector {

    private static final SessionFactory sessionFactory = configureSessionFactory();

    private static SessionFactory configureSessionFactory() {
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

