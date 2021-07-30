package ua.com.nix.dao;

import ua.com.nix.model.*;

import java.util.List;

public interface OperationDao {
    void createOperation(Operation operation);

    Account getAccountById(Integer id);

    User getUserByEmail(String email);

    ExpenseCategory getExpenseCategoryByName(String expenseName);

    IncomeCategory getIncomeCategoryByName(String incomeName);

    void addOperationCategories(List<OperationCategory> operationCategories);

}
