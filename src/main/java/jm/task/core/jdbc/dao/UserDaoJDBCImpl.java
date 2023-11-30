package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public void createUsersTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                String sql = """
                        CREATE TABLE IF NOT EXISTS `users` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(45) NOT NULL,
                          `lastName` varchar(45) NOT NULL,
                          `age` int NOT NULL,
                          PRIMARY KEY (`id`)
                        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3""";
                statement.execute(sql);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("БД успешно создана с помощью JDBC");
    }

    public void dropUsersTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                String sql = "DROP TABLE IF EXISTS users;";
                statement.execute(sql);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("БД успешно уничтожена с помощью JDBC");
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format("INSERT INTO users (name, lastName, age) VALUES ('%s', '%s', %d)",
                        name, lastName, age);
                statement.execute(sql);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        try {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format("DELETE FROM users WHERE id = %d", id);
                statement.execute(sql);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Пользователь с id: " + id + " успешно удалён из БД с помощью JDBC");
    }

    public List<User> getAllUsers() {
        try {
            try (Statement statement = connection.createStatement()) {
                List<User> userList = new ArrayList<>();
                String sql = "SELECT * FROM users";
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");
                    byte age = resultSet.getByte("age");

                    User user = new User(name, lastName, age);
                    user.setId(id);
                    userList.add(user);
                }
                return userList;
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                String sql = "DELETE FROM users";
                statement.execute(sql);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Таблица успешно очищена с помощью JDBC");
    }
}
