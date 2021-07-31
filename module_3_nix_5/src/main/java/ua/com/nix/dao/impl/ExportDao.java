package ua.com.nix.dao.impl;

import au.com.bytecode.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.nix.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExportDao {
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private final Connection connection;

    public ExportDao(Connection connection) {
        this.connection = connection;
    }

    public void exportOperationsInPeriodToCsv(Account account, Instant dateFrom, Instant dateTo, String filePath) {
        List<String[]> csvData = new ArrayList<>();
        String[] header = {"id", "result", "date"};
        csvData.add(header);

        List<Operation> operations = getOperationInPeriod(account, dateFrom, dateTo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        for (Operation operation : operations) {
            csvData.add(new String[]{
                    String.valueOf(operation.getId()), String.valueOf(operation.getResult()), formatter.format(operation.getDate())});
        }

        int totalIncome = getTotalIncomeInPeriod(account, dateFrom, dateTo);
        int balance = getBalanceInPeriod(account, dateFrom, dateTo);

        String[] secondHeader = {"total", "income", "balance"};
        try (Writer writer = new FileWriter(filePath); CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeAll(csvData);
            csvWriter.writeNext(secondHeader);
            csvWriter.writeNext(new String[]{dateFrom.toString() + " - " + dateTo.toString(), String.valueOf(totalIncome), String.valueOf(balance)});
        } catch (IOException e) {
            LOGGER_ERROR.error("File problem");
            throw new RuntimeException("Problem while working with a file");
        }
    }

    private List<Operation> getOperationInPeriod(Account account, Instant dateFrom, Instant dateTo) {
        LOGGER_INFO.info("Start getting operations from: " + dateFrom.toString() + " to: " + dateTo.toString());
        List<Operation> operations = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from operations op where op.account_id = ? and op.date between ? and ?")) {
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setTimestamp(2, Timestamp.from(dateFrom));
            preparedStatement.setTimestamp(3, Timestamp.from(dateTo));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Operation operation = new Operation(
                        resultSet.getInt("id"),
                        account,
                        resultSet.getTimestamp("date").toInstant(),
                        resultSet.getInt("result")
                );
                operations.add(operation);
            }
        } catch (SQLException e) {
            LOGGER_ERROR.error("Can't get information from account with id: " + account.getId());
            throw new RuntimeException("It is impossible to perform the operation");
        }
        return operations;
    }

    private int getBalanceInPeriod(Account account, Instant dateFrom, Instant dateTo) {
        int balance = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select abs(sum(result)) from operations op where " +
                        "op.account_id = ? and (op.date between ? and ?)")) {

            balance = preparationOfAnInvoice(account, dateFrom, dateTo, preparedStatement);

        } catch (SQLException e) {
            LOGGER_ERROR.error("Error getting balance in period from account with id: " + account.getId());
            throw new RuntimeException("It is impossible to perform the operation");
        }

        return balance;
    }

    private int getTotalIncomeInPeriod(Account account, Instant dateFrom, Instant dateTo) {
        int totalIncome = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select sum(result) from operations op where " +
                        "op.account_id = ? and (op.date between ? and ?) and op.result > 0")) {

            totalIncome = preparationOfAnInvoice(account, dateFrom, dateTo, preparedStatement);

        } catch (SQLException e) {
            LOGGER_ERROR.error("Error getting total income in period from account with id: " + account.getId());
            throw new RuntimeException("It is impossible to perform the operation");
        }

        return totalIncome;
    }

    private int preparationOfAnInvoice(Account account, Instant dateFrom, Instant dateTo, PreparedStatement preparedStatement) throws SQLException {
        int totalIncome;
        preparedStatement.setInt(1, account.getId());
        preparedStatement.setTimestamp(2, Timestamp.from(dateFrom));
        preparedStatement.setTimestamp(3, Timestamp.from(dateTo));

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        totalIncome = resultSet.getInt(1);
        return totalIncome;
    }
    public User getUserByEmail(String email){
        User user = null;
        User userTemp = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from users where email=?")) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Account> accounts = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    email = resultSet.getString("email");
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    userTemp = new User(id,firstName,lastName,email);
                    accounts.add(getAccountById(userTemp));
                    user = new User(id,firstName,lastName,email,accounts);

                }
            }
            catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    private Account getAccountById(User userTemp){
        Account account = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from accounts where user_id=?")) {
            preparedStatement.setLong(1, userTemp.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int balance = resultSet.getInt("balance");
                int user_id = resultSet.getInt("user_id");
                account = new Account(id, userTemp,balance);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }
}
