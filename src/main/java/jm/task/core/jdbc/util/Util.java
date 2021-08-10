package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;


public class Util {
    // реаusersлизуйте настройку соеденения с БД
    
    private static final String URL = "jdbc:mysql://localhost:3306/db1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "r00tr00t";
    
    private static Connection jdbc;
    private static SessionFactory sessionFactory;
    
    
    public static Connection getJDBC() {
        if (jdbc == null) {
            try {
                jdbc = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return jdbc;
    }
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/db1");
                properties.setProperty(Environment.USER, "root");
                properties.setProperty(Environment.PASS, "r00tr00t");
                properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                
                configuration.setProperties(properties)
                        .addAnnotatedClass(User.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
