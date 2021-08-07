package jm.task.core.jdbc.util;


import java.sql.*;


public class Util {
    // реализуйте настройку соеденения с БД
    
    private final String URL = "jdbc:mysql://localhost:3306/db1";
    private final String USERNAME = "root";
    private final String PASSWORD = "r00tr00t";
    
    private Connection connection;
    
    public Util() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    public Connection getConnection() {
        return connection;
    }
}
