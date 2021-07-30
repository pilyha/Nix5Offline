package ua.com.nix.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateConnector {
    private HibernateConnector() {

    }

    public static SessionFactory createSessionFactory(String username, String password) {
        Configuration configuration = new Configuration().
                setProperty("hibernate.connection.username", username).
                setProperty("hibernate.connection.password", password).
                configure();
        return configuration.buildSessionFactory();
    }

}
