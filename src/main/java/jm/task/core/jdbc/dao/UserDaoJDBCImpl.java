package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("CREATE TABLE User( id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastname VARCHAR(255) NOT NULL, " +
                    "age TINYINT(4));");
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("DROP TABLE User;");
            System.out.println("Таблица сброшена");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("insert into User (name, lastName, age) values ('" + name + "', '" +
                    lastName + "', " + age + ");");
            System.out.println("Пользователь сохранен");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("delete from User where id = " + id + ";");
            System.out.println("Пользователь удален");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement statement = Util.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM User");
            while (result.next()) {
                users.add(new User(result.getString(2), result.getString(3),
                        result.getByte(4)));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("DELETE FROM User;");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
