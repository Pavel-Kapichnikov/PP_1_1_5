package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_for_pp";
    private static final String DB_USER_NAME = "pavel_kap";
    private static final String DB_PASSWORD = "Alfa-Romeo008";

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено!");
            }
            return connection;
        } catch (SQLException e) {
            System.out.println("Не удалось загрузить класс драйвера" + e.getMessage());
            return null;
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение с БД закрыто!");
            } catch (SQLException e) {
                System.out.println("Не удалось закрыть соединение с БД");
            }
        }
    }

}
