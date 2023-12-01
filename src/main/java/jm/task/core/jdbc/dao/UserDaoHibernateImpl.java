package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory = Util.getFactory();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try {
            try (Session session = factory.getCurrentSession()) {
                String sql = """
                        CREATE TABLE IF NOT EXISTS `users` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(45) NOT NULL,
                          `lastName` varchar(45) NOT NULL,
                          `age` int NOT NULL,
                          PRIMARY KEY (`id`)
                        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3""";

                session.beginTransaction();
                session.createNativeQuery(sql).executeUpdate();
                session.getTransaction().commit();
            }
        } catch (HibernateException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("БД успешно создана с помощью Hibernate");
    }

    @Override
    public void dropUsersTable() {
        try {
            try (Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
                session.getTransaction().commit();
            }
        } catch (HibernateException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("БД успешно уничтожена с помощью Hibernate");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            try (Session session = factory.getCurrentSession()) {
                User user = new User(name, lastName, age);
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
            }
        } catch (HibernateException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных с помощью Hibernate");
    }

    @Override
    public void removeUserById(long id) {
        try {
            try (Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
                session.getTransaction().commit();
            }
        } catch (HibernateException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Пользователь с id: " + id + " успешно удалён из БД с помощью Hibernate");
    }

    @Override
    public List<User> getAllUsers() {
        try {
            try (Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                List<User> userList = session.createQuery("from User").getResultList();
                session.getTransaction().commit();
                return userList;
            }
        } catch (HibernateException | NullPointerException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try {
            try (Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                session.createQuery("delete User").executeUpdate();
                session.getTransaction().commit();
            }
        } catch (HibernateException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Таблица успешно очищена с помощью Hibernate");
    }
}
