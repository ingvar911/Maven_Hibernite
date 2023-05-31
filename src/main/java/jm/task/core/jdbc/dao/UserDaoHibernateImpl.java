package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS User (ID INT AUTO_INCREMENT PRIMARY KEY, Name TEXT, LastName TEXT, Age TINYINT)";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS User";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.remove(user);
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createQuery("from User").getResultList();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "DELETE FROM User";
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.close();
    }
}
