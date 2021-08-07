package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    
    private Util util;
    
    private static final String TABLE = "users";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `" + TABLE + "` (" +
            "`id` bigint NOT NULL AUTO_INCREMENT," +
            "`name` varchar(45) DEFAULT NULL," +
            "`lastName` varchar(45) DEFAULT NULL," +
            "`age` tinyint DEFAULT NULL," +
            "PRIMARY KEY (`id`)," +
            "UNIQUE KEY `users_id_uindex` (`id`))";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
    private static final String INSERT = "INSERT INTO " + TABLE + " VALUES(?, ?, ?, ?)";
    private static final String DELETE_ID = "DELETE FROM " + TABLE + " WHERE id = ";
    private static final String DELETE = "DELETE FROM " + TABLE;
    
    
    public UserServiceImpl() { }
    
    
    void open() throws SQLException {
        util = new Util();
    }
    void close() throws SQLException {
        if (util.getConnection() != null) {
            util.getConnection().close();
        }
    }
    
    public void createUsersTable() {
        try {
            open();
            Statement st = util.getConnection().createStatement();
            st.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException ignored) { }
        }
    }

    public void dropUsersTable() {
        try {
            open();
            Statement st = util.getConnection().createStatement();
            st.executeUpdate(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException ignored) { }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            open();
            PreparedStatement ps = util.getConnection().prepareStatement(INSERT);
            ps.setLong(1, 0);
            ps.setString(2, name);
            ps.setString(3, lastName);
            ps.setByte(4, age);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException ignored) { }
        }
    }

    public void removeUserById(long id) {
        try {
            open();
            Statement st = util.getConnection().createStatement();
            st.executeUpdate(DELETE_ID + id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException ignored) { }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            open();
            ResultSet data = util.getConnection().createStatement().executeQuery("SELECT * FROM " + TABLE);
            while (data.next()) {
                User user = new User(
                        data.getString("name"),
                        data.getString("lastName"),
                        data.getByte("age"));
                user.setId(data.getLong("id"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException ignored) { }
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            open();
            Statement st = util.getConnection().createStatement();
            st.executeUpdate(DELETE);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException ignored) { }
        }
    }
}
