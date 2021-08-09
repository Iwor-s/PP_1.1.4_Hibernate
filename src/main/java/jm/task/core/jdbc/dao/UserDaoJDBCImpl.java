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
        try (
                Connection connection = new Util().getConnection();
                Statement st = connection.createStatement()
        ) {
            st.executeUpdate(SQLUser.CREATE_TABLE.QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (
                Connection connection = new Util().getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(SQLUser.DROP_TABLE.QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (
                Connection connection = new Util().getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(SQLUser.INSERT.QUERY)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (
                Connection connection = new Util().getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(SQLUser.DELETE_ID.QUERY)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (
                Connection connection = new Util().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet data = statement.executeQuery(SQLUser.SELECT.QUERY);
            while (data.next()) {
                User user = new User(
                        data.getString("name"),
                        data.getString("lastName"),
                        data.getByte("age"));
                user.setId(data.getLong("id"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (
                Connection connection = new Util().getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(SQLUser.DELETE_ALL.QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private enum SQLUser {
        CREATE_TABLE("CREATE TABLE IF NOT EXISTS `users` (" +
                "`id` SERIAL, " +
                "`name` VARCHAR(45) NOT NULL, " +
                "`lastName` VARCHAR(45) NOT NULL, " +
                "`age` TINYINT UNSIGNED NOT NULL)"),
        DROP_TABLE("DROP TABLE IF EXISTS users"),
        INSERT("INSERT INTO users (id, name, lastName, age) " +
                "VALUES (DEFAULT, ?, ?, ?)"),
        SELECT("SELECT * FROM users"),
        DELETE_ID("DELETE FROM users WHERE id = (?)"),
        DELETE_ALL("DELETE FROM users");
        
        private final String QUERY;
        
        SQLUser(String query) {
            this.QUERY = query;
        }
    }
}
