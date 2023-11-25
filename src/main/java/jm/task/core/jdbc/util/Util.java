package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private static Connection connection;
    public static void getConnection() {
        try {
            String URL = "jdbc:mysql://localhost:3306/db_for_pp";
            String USERNAME = "pavel_kap";
            String PASSWORD = "Alfa-Romeo008";

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено!");
            }
        } catch (SQLException e) {
            System.out.println("Не удалось загрузить класс драйвера");
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

    public static Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
