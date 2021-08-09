package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
    
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
    
        userService.saveUser("Name1", "Surname1", (byte) 11);
        userService.saveUser("Name2", "Surname2", (byte) 22);
        userService.saveUser("Name3", "Surname3", (byte) 33);
        userService.saveUser("Name4", "Surname4", (byte) 44);
        
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
