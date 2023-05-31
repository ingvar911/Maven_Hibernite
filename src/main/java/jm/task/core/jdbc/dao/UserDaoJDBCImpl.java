package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Util.getConnect().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Users (ID INT AUTO_INCREMENT PRIMARY KEY, Name TEXT, LastName TEXT, Age TINYINT);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Util.getConnect().createStatement().executeUpdate("DROP TABLE IF EXISTS Users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement pstmt = null;
        try {
            pstmt = Util.getConnect().prepareStatement("INSERT INTO Users (Name, LastName, Age) Values (?, ?, ?)");
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        PreparedStatement pstmt = null;
        try {
            pstmt = Util.getConnect().prepareStatement("DELETE FROM Users WHERE ID=?;");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = Util.getConnect().prepareStatement("SELECT * FROM Users");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("Name");
                String lastName = resultSet.getString("LastName");
                byte age = resultSet.getByte("Age");
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        Statement statement = null;
        try {
            statement = Util.getConnect().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.executeUpdate("DELETE FROM Users;");
        } catch (SQLSyntaxErrorException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
