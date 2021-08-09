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
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQLUser.CREATE_TABLE.QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQLUser.DROP_TABLE.QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement(SQLUser.INSERT.QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.printf("User с именем %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement(SQLUser.DELETE_ID.QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
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
        try (Statement statement = Util.getConnection().createStatement()) {
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
