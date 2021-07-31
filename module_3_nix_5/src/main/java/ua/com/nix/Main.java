package ua.com.nix;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import ua.com.nix.UI.OperationController;
import ua.com.nix.model.User;
import ua.com.nix.service.OperationControlService;
import ua.com.nix.util.Connector;
import ua.com.nix.util.HibernateConnector;

import javax.persistence.EntityManager;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main.main");

        Connector.createConnection(args[1], args[2]);

        try (SessionFactory sessionFactory = HibernateConnector.createSessionFactory(args[1], args[2])) {
            EntityManager entityManager = sessionFactory.createEntityManager();
            OperationController controller = new OperationController(entityManager, args[0]);
            controller.financeInterface();
        } catch (HibernateException | IOException e) {
            throw new RuntimeException("Error");
        }
    }
}
