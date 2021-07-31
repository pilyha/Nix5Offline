package ua.com.nix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.nix.dao.OperationDao;
import ua.com.nix.dao.impl.OperationDaoImpl;
import ua.com.nix.model.*;

import java.util.List;

public class OperationControlService {
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");
    private final OperationDao operationDao;

    public OperationControlService(OperationDaoImpl operationDao) {
        this.operationDao = operationDao;
    }

    public void createOperation(Operation operation, User user) {
        LOGGER_INFO.info("Start adding operation to account.");
        if (operation == null || operation.getOperationCategories().size() == 0) {
            LOGGER_ERROR.error("Operation categories can't be null or empty...");
            throw new IllegalArgumentException("Operation hasn't category");
        }
        if (!user.getId().equals(operation.getAccount().getUser().getId())) {
            LOGGER_ERROR.error(" This user: " + user.getId() + " does not have such an account: " + operation.getAccount().getId());
            throw new RuntimeException(new IllegalAccessException("User hasn't account"));
        }
        if (operation.getResult() == 0) {
            LOGGER_ERROR.error("Incorrect operation result...");
            throw new IllegalArgumentException("Zero turn operations are not allowed");
        }
        if (operation.getAccount().getBalance() + operation.getResult() < 0) {
            LOGGER_ERROR.error("The balance cannot be lower than 0...");
            throw new IllegalArgumentException("Negative balance is not allowed");
        }

        operationDao.createOperation(operation);
        addOperationCategories(operation.getOperationCategories());

        if (operation.getType() == OperationType.INCOME && operation.getResult() < 0 || operation.getType() == OperationType.EXPENSE && operation.getResult() > 0) {
            LOGGER_ERROR.error("The type of operation must match the result...");
            throw new IllegalArgumentException("Operation type mismatch");
        }
        LOGGER_INFO.info("End adding operation to account. ");
    }

    public Account getAccountById(Integer id) {
        LOGGER_INFO.info("Start getting account by id: {}", id);
        Account account = operationDao.getAccountById(id);
        if (account == null) {
            LOGGER_ERROR.error("No such account exists: " + id);
            throw new IllegalArgumentException("No such account exists: ");
        }
        LOGGER_INFO.info("End getting account by id: {}", id);
        return account;
    }


    public User getUserByEmail(String email) {
        LOGGER_INFO.info("Start getting user by email: " + email);
        User user = operationDao.getUserByEmail(email);
        if (user == null) {
            LOGGER_ERROR.error("No such user exists: " + email);
            throw new IllegalArgumentException("No user with this email");
        }
        LOGGER_INFO.info("End getting user by email: " + email);
        return user;
    }


    public ExpenseCategory getExpenseCategoryByName(String expenseName) {
        LOGGER_INFO.info("Start getting expense category by name: " + expenseName);
        ExpenseCategory exCategory = operationDao.getExpenseCategoryByName(expenseName);
        if (exCategory == null) {
            LOGGER_ERROR.error("No such category exists with expenseName: " + expenseName);
            throw new IllegalArgumentException("No expense category with this name");
        }
        LOGGER_INFO.info("End getting expense category by name: " + expenseName);
        return exCategory;
    }


    public IncomeCategory getIncomeCategoryByName(String incomeName) {
        LOGGER_INFO.info("Start getting income category by incomeName: " + incomeName);
        IncomeCategory inCategory = operationDao.getIncomeCategoryByName(incomeName);
        if (inCategory == null) {
            LOGGER_ERROR.error("No such category exists with incomeName: " + incomeName);
            throw new IllegalArgumentException("No income category with this name");
        }
        LOGGER_INFO.info("End getting income category by name: " + incomeName);
        return inCategory;
    }


    public void addOperationCategories(List<OperationCategory> operationCategories) {
        LOGGER_INFO.info("Start adding operationCategories");
        for (OperationCategory operationCategory : operationCategories) {
            Operation operation = operationCategory.getOperation();
            IncomeCategory incomeCategory = operationCategory.getIncomeCategory();
            ExpenseCategory expenseCategory = operationCategory.getExpenseCategory();

            if (incomeCategory == null && expenseCategory == null) {
                LOGGER_ERROR.error("The operation must have at least 1 type...");
                throw new IllegalArgumentException("Must have at least 1 type");
            }
            if (incomeCategory != null && expenseCategory != null) {
                LOGGER_ERROR.error("The operation can only have 1 type...");
                throw new IllegalArgumentException("Can only have 1 type");
            }
            if (operation.getType() == null) {
                if (incomeCategory != null) {
                    operation.setType(OperationType.INCOME);
                } else {
                    operation.setType(OperationType.EXPENSE);
                }
            }
        }
        operationDao.addOperationCategories(operationCategories);
        LOGGER_INFO.info("End adding operationCategories");
    }
}

