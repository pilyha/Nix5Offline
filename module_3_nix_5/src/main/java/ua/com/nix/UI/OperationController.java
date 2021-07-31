package ua.com.nix.UI;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ua.com.nix.dao.impl.OperationDaoImpl;
import ua.com.nix.model.Account;
import ua.com.nix.model.Operation;
import ua.com.nix.model.OperationCategory;
import ua.com.nix.model.User;
import ua.com.nix.service.ExportService;
import ua.com.nix.service.OperationControlService;
import ua.com.nix.util.Connector;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class OperationController {
    private final String email;
    private final Session session;
    private final String userName;
    private final String password;


    public OperationController(Session session, String email, String userName, String password) {
        this.email = email;
        this.session = session;
        this.userName = userName;
        this.password = password;
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void financeInterface() throws IOException {
        MainLabel:
        while (true) {
            System.out.println("""
                    Enter task:
                    1 -> Create operation
                    2 -> Export data
                    0 -> Exit""");

            String choice = reader.readLine();
            switch (choice) {
                case "1": {
                    createOperation();
                    break;
                }
                case "2": {
                    exportDataToCSV();
                    break;
                }
                case "0": {
                    break MainLabel;
                }
                default:{
                    System.out.println("Incorrect choose,try again...");
                    break;
                }
            }
        }
    }

    private void createOperation() {
        OperationControlService operationControl = new OperationControlService(new OperationDaoImpl(session));
        try {
            session.getTransaction().begin();
            User user = operationControl.getUserByEmail(email);
            Account account = operationControl.getAccountById(user.getId());

            List<OperationCategory> expenseOperationsCategories = new ArrayList<>();
            List<OperationCategory> incomeOperationCategories = new ArrayList<>();
            System.out.println("""
                    Enter task:
                    1 -> Income operation
                    2 -> Expense operation
                    0 -> Return to menu""");
            String choose = reader.readLine();
            if (choose.equals("1")) {
                System.out.println("Enter name of income category");
                String nameCategory = reader.readLine();
                if (operationControl.getIncomeCategoryByName(nameCategory) != null) {
                    System.out.println("Enter the amount of added funds");
                    int funds = Integer.parseInt(reader.readLine());
                    Operation incomeOperation = new Operation(account, funds, Instant.now());
                    incomeOperationCategories.add(new OperationCategory(incomeOperation, operationControl.getIncomeCategoryByName(nameCategory), null));
                    incomeOperation.setOperationCategories(incomeOperationCategories);
                    operationControl.createOperation(incomeOperation, user);
                } else {
                    System.out.println("There is no such income category,try again...");
                }
            } else if (choose.equals("2")) {
                System.out.println("Enter name of expense category");
                String nameCategory = reader.readLine();
                if (operationControl.getExpenseCategoryByName(nameCategory) != null) {
                    System.out.println("Enter the amount of removed funds");
                    int funds = Integer.parseInt(reader.readLine());
                    Operation expenseOperation = new Operation(account, funds, Instant.now());
                    expenseOperationsCategories.add(new OperationCategory(expenseOperation, null, operationControl.getExpenseCategoryByName(nameCategory)));
                    expenseOperation.setOperationCategories(expenseOperationsCategories);
                    operationControl.createOperation(expenseOperation, user);
                } else {
                    System.out.println("There is no such expense category,try again...");
                }
            }
            else if (choose.equals("3")) {
            return;
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportDataToCSV() {
        try (Connection connection = Connector.createConnection(userName, password)) {
            ExportService exportService = new ExportService(connection);
            User user = exportService.getUserByEmail(email);
            System.out.println("Enter date from in format yyyy-MM-ddTHH:MM:SS :");
            String from = reader.readLine();
            LocalDateTime dateFrom;
            try {
                dateFrom = LocalDateTime.parse(from);
            }catch (DateTimeParseException e)
            {
                System.out.println("Error,enter correct date with help format");
                return;
            }

            System.out.println("Enter date to in format yyyy-MM-ddTHH:MM:SS :");
            String to = reader.readLine();
            LocalDateTime dateTo = LocalDateTime.parse(to);
            try {
                dateTo = LocalDateTime.parse(from);
            }catch (DateTimeParseException e)
            {
                System.out.println("Error,enter correct date with help format");
                return;
            }

            exportService.exportOperationsInPeriodToCsv(
                    user,
                    user.getAccounts().get(0),
                    dateFrom,
                    dateTo,
                    "data.csv");
        } catch (SQLException e) {
            throw new RuntimeException("Error connection");
        } catch (IOException e) {
            throw new RuntimeException("Incorrect input,try again..." + e.getMessage());
        }
    }
}
