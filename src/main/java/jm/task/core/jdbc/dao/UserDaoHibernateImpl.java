package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.resource.transaction.spi.TransactionStatus.*;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                String sql = "CREATE TABLE IF NOT EXISTS User (ID INT AUTO_INCREMENT PRIMARY KEY, Name TEXT, LastName TEXT, Age TINYINT)";
                Query query = session.createSQLQuery(sql).addEntity(User.class);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                String sql = "DROP TABLE IF EXISTS User";
                Query query = session.createSQLQuery(sql);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.save(new User(name, lastName, age));
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                User user = session.get(User.class, id);
                session.remove(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                userList = session.createQuery("from User").getResultList();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                String hql = "DELETE FROM User";
                Query query = session.createQuery(hql);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.getStatus() == ACTIVE || transaction.getStatus() == MARKED_ROLLBACK) {
                    transaction.rollback();
                }
            }
        }
    }
}
