package jm.task.core.jdbc.util;

import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger log = Logger.getLogger(Util.class.getName());
    private static final String url = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String user = "root";
    private static final String password = "root";
    private static Connection connection;
    private static SessionFactory factory = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (!connection.isClosed()) {
                log.info("Есть коннект");
            }
        } catch (SQLException e) {
            log.warning("Нет коннекта, драйвер не запущен");
        }
        return connection;
    }
    public static SessionFactory getFactory() {
        if (factory == null) {
            factory = new Configuration()
                    .configure()
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return factory;
    }

}
