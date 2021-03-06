package ua.com.nix.dao.impl;

import org.hibernate.Session;
import ua.com.nix.dao.OperationDao;
import ua.com.nix.model.*;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class OperationDaoImpl implements OperationDao {

    private final Session session;

    public OperationDaoImpl(Session session) {
        this.session = session;
    }

    public void createOperation(Operation operation) {
        session.persist(operation);
        Query updateQuery = session.createQuery(
                "update Account account set account.balance = account.balance + :balance where account.id = :id");

        updateQuery.setParameter("balance", operation.getResult());
        updateQuery.setParameter("id", operation.getAccount().getId());

        updateQuery.executeUpdate();
    }

    public Account getAccountById(Integer id) {
        return session.find(Account.class, id);
    }

    public ExpenseCategory getExpenseCategoryByName(String name) {
        TypedQuery<ExpenseCategory> query = session.createQuery(
                "select category from ExpenseCategory category where category.name = :name",
                ExpenseCategory.class);

        query.setParameter("name", name);
        query.setMaxResults(1);

        ExpenseCategory expenseCategory;
        try {
            expenseCategory = query.getSingleResult();
            return expenseCategory;
        } catch (NoResultException e) {
            return null;
        }
    }

    public IncomeCategory getIncomeCategoryByName(String name) {
        TypedQuery<IncomeCategory> query = session.createQuery(
                "select category from IncomeCategory category where category.name = :name",
                IncomeCategory.class);

        query.setParameter("name", name);
        query.setMaxResults(1);

        IncomeCategory incomeCategory;
        try {
            incomeCategory = query.getSingleResult();
            return incomeCategory;
        } catch (NoResultException e) {
            return null;
        }
    }

    public User getUserByEmail(String email) {
        TypedQuery<User> query = session.createQuery(
                "select us from User us where us.email = :email",
                User.class);

        query.setParameter("email", email);
        query.setMaxResults(1);

        User user;
        try {
            user = query.getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    public void addOperationCategories(List<OperationCategory> operationCategories) {
        for (OperationCategory operationCategory : operationCategories) {
            session.persist(operationCategory);
        }
    }
}
