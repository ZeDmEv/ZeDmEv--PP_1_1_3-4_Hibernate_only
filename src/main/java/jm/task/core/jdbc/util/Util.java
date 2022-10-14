package jm.task.core.jdbc.util;

import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.persistence.Column;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger log = Logger.getLogger(Util.class.getName());
    private static final String url = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String user = "root";
    private static final String password = "root";
    private static Connection connection;
    private static SessionFactory factory;

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
            Configuration config = new Configuration();
            Properties settings = new Properties();
            settings.put("hibernate.id.new_generator_mappings", false);
            settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.URL, url);
            settings.put(Environment.USER, user);
            settings.put(Environment.PASS, password);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(Environment.SHOW_SQL, true);
            config.setProperties(settings);
            config.addAnnotatedClass(User.class);
            factory = config.buildSessionFactory();
        }
        return factory;
    }

}
