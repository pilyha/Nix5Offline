package ua.com.nix;

import org.hibernate.HibernateException;
import ua.com.nix.model.*;
import ua.com.nix.service.ExportService;
import ua.com.nix.service.OperationControlService;
import ua.com.nix.util.Connector;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public void createOperation(EntityManager entityManager, User user) {
        OperationControlService operationControl = new OperationControlService(entityManager);
        try {
            entityManager.getTransaction().begin();
            Account account = operationControl.getAccountById(user.getId());

            List<OperationCategory> expenseOperationsCategories = new ArrayList<>();
            List<OperationCategory> incomeOperationCategories = new ArrayList<>();

            Operation incomeOperation = new Operation(account, 1280, Instant.now());
            incomeOperationCategories.add(new OperationCategory(incomeOperation, operationControl.getIncomeCategoryByName("Scholarship"), null));
            incomeOperation.setOperationCategories(incomeOperationCategories);
            operationControl.createOperation(incomeOperation, user);

            incomeOperation = new Operation(account, 5000, Instant.now());
            incomeOperationCategories.add(new OperationCategory(incomeOperation, operationControl.getIncomeCategoryByName("Salary"), null));
            incomeOperation.setOperationCategories(incomeOperationCategories);
            operationControl.createOperation(incomeOperation, user);


            Operation expenseOperation = new Operation(account, -370, Instant.now());
            expenseOperationsCategories.add(new OperationCategory(expenseOperation, null, operationControl.getExpenseCategoryByName("Cafes and Restaurants")));
            expenseOperationsCategories.add(new OperationCategory(expenseOperation, null, operationControl.getExpenseCategoryByName("Logistics")));
            expenseOperation.setOperationCategories(expenseOperationsCategories);
            operationControl.createOperation(expenseOperation, user);

            expenseOperation = new Operation(account, -600, Instant.now());
            expenseOperationsCategories.add(new OperationCategory(expenseOperation, null, operationControl.getExpenseCategoryByName("Leisure")));
            expenseOperation.setOperationCategories(expenseOperationsCategories);
            operationControl.createOperation(expenseOperation, user);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            entityManager.getTransaction().rollback();
        }
    }
    public void exportDataToCSV(User user) {
        try (Connection connection = Connector.getConnection()) {
            ExportService exportService = new ExportService(connection);

            exportService.exportOperationsInPeriodToCsv(
                    user,
                    user.getAccounts().get(0),
                    LocalDateTime.of(2021, 7, 30, 3, 0),
                    LocalDateTime.of(2021, 7, 31, 3, 0),
                    "data.csv");
        } catch (SQLException e) {
            throw new RuntimeException("Error connection");
        }
    }
}
