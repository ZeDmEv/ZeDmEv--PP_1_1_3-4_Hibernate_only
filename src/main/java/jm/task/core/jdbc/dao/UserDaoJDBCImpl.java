package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger log = Logger.getLogger(UserDao.class.getName());

    public void createUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("CREATE TABLE User( id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastname VARCHAR(255) NOT NULL, " +
                    "age TINYINT(4));");
            log.info("Таблица создана");
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("DROP TABLE User;");
            log.info("Таблица сброшена");
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?);";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql);) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            log.info("Пользователь сохранен");
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM User WHERE id = (?);";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            log.info("Пользователь удален");
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM User");
            while (result.next()) {
                users.add(new User(result.getString(2), result.getString(3),
                        result.getByte(4)));
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement();) {
            statement.execute("DELETE FROM User;");
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}
