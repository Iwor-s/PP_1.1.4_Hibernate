package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    private static UserService service;
    
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        
        service = new UserServiceImpl();
        service.createUsersTable();
        
        addUser("Name1", "Surname1", (byte) 11);
        addUser("Name2", "Surname2", (byte) 22);
        addUser("Name3", "Surname3", (byte) 33);
        addUser("Name4", "Surname4", (byte) 44);
        
        for (User user : service.getAllUsers()) {
            System.out.println(user);
        }
        
        service.cleanUsersTable();
        service.dropUsersTable();
    }
    
    public static void addUser(String name, String lastName, byte age) {
        service.saveUser(name, lastName, age);
        System.out.printf("User с именем %s добавлен в базу данных\n", name);
    }
}
