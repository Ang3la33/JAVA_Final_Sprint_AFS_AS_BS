package org.example;

import org.example.database.DatabaseInitializer;
import org.example.users.UserService;
import org.example.users.Users;

public class EcomApp {
    private static UserService userService = new UserService();

    public static void main(String[] args) {
        // Initialize the database
        DatabaseInitializer.initializeDatabase();

        try {
            userService.getAllUsers();
//            Users user = new Users("AStevenson", "secure_password", "a@a.com", "Buyer");
//            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
