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
    
    private static final String URL = "jdbc:mysql://localhost:3306/db1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "r00tr00t";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    
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
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.setProperty(Environment.URL, URL);
            properties.setProperty(Environment.USER, USERNAME);
            properties.setProperty(Environment.PASS, PASSWORD);
            properties.setProperty(Environment.DIALECT, DIALECT);
            
            configuration.setProperties(properties)
                    .addAnnotatedClass(User.class);
            
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
