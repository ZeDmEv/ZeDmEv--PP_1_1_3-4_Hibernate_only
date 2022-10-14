package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserDaoHibernateImpl implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    @Override
    public void createUsersTable() {
        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User( id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, lastname VARCHAR(255) NOT NULL, " +
                    "age TINYINT(4));";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
    @Override
    public void dropUsersTable() {
        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS User;";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            log.warning("Произошла ошибка: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
            cq.from(User.class);
            users = session.createQuery(cq).getResultList();
            session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            log.warning("Произошла ошибка: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            log.warning("Произошла ошибка: " + e.getMessage());
        }
    }
}
