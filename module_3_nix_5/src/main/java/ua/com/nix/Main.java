package ua.com.nix;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import ua.com.nix.model.User;
import ua.com.nix.service.OperationControlService;
import ua.com.nix.util.HibernateConnector;

import javax.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main.main");
        Solution solution = new Solution();


        try (SessionFactory sessionFactory = HibernateConnector.createSessionFactory(args[1], args[2])) {
            EntityManager entityManager = sessionFactory.createEntityManager();
            OperationControlService operationControl = new OperationControlService(entityManager);
            User user = operationControl.getUserByEmail(args[0]);
            solution.createOperation(entityManager, user);
            solution.exportDataToCSV(user);
        } catch (HibernateException e) {
            throw new RuntimeException("Error");
        }
    }
}
