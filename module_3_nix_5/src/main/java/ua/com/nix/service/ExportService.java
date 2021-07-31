package ua.com.nix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.nix.dao.impl.ExportDao;
import ua.com.nix.model.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ExportService {
    private final ExportDao exportDao;
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public ExportService(Connection connection) {
        this.exportDao = new ExportDao(connection);
    }

    public void exportOperationsInPeriodToCsv(User user, Account account, LocalDateTime dateFrom, LocalDateTime dateTo, String filePath) {
        LOGGER_INFO.info("Start export operations from: " + dateFrom.toString() + " to: " + dateTo.toString() + " in csv file with path: " + filePath.toString());
        if (!user.getId().equals(account.getUser().getId())) {
            LOGGER_ERROR.error(" This user: " + user.getId() + " does not have such an account: " + account.getId());
            throw new RuntimeException(new IllegalAccessException("User hasn't account"));
        }
        exportDao.exportOperationsInPeriodToCsv(account, dateFrom.toInstant(ZoneOffset.UTC), dateTo.toInstant(ZoneOffset.UTC), filePath);
        LOGGER_INFO.info("End export operations from: " + dateFrom.toString() + " to: " + dateTo.toString() + " to csv file: " + filePath);
    }

    public User getUserByEmail(String email) {
        LOGGER_INFO.info("Start searching user with email: " + email);
        User user = exportDao.getUserByEmail(email);
        LOGGER_INFO.info("End searching user with email: " + email);
        return user;
    }
}
