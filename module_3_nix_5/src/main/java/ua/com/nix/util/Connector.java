package ua.com.nix.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Connection connection = null;

    private Connector() {
    }

    public static Connection createConnection(String username, String password) {
        Properties props = LoadProperties.load();
        String url = props.getProperty("url");
        props.setProperty("user", username);
        props.setProperty("password", password);

        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.println("Could not connect to DB: " + e.getMessage());
        }
        return connection;
    }
}
