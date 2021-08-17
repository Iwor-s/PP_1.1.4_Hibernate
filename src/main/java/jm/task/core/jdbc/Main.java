package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    
    public static void main(String[] args) {
    
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
    
        userService.saveUser("Name1", "Surname1", (byte) 11);
        userService.saveUser("Name2", "Surname2", (byte) 22);
        userService.saveUser("Name3", "Surname3", (byte) 33);
        userService.saveUser("Name4", "Surname4", (byte) 44);
        
        userService.getAllUsers().forEach(System.out::println);
        
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
