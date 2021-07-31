package ua.com.nix;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.com.nix.UI.OperationController;
import ua.com.nix.util.HibernateConnector;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main.main");
        try (SessionFactory sessionFactory = HibernateConnector.createSessionFactory(args[1], args[2]);
             Session session = sessionFactory.openSession()) {
            OperationController controller = new OperationController(session, args[0], args[1], args[2]);
            controller.financeInterface();
        } catch (HibernateException e) {
            throw new RuntimeException("Error");
        } catch (IOException e) {
            throw new RuntimeException("Incorrect input,try again...");
        }
    }
}
