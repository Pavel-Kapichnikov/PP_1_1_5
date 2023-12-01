package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection;
    private static SessionFactory factory;
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

    public static SessionFactory getFactory() {
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
            System.out.println("Соединение с БД установлено с помощью Hibernate!");
            return  factory;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void closeFactory() {
        try {
            factory.close();
            System.out.println("Factory is closed");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

}
