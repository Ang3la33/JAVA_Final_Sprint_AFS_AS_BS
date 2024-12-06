package org.example;

import org.example.menu.RoleMenu;
import org.example.products.ProductService;
import org.example.users.UserService;
import org.example.users.Users;

import org.mindrot.jbcrypt.BCrypt; // Ensure BCrypt is available in your project

import java.util.Scanner;

public class EcomAppNew {

    public static void main(String[] args) {

        // Initialize services
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        RoleMenu roleMenu = new RoleMenu(productService, userService);

        System.out.println("Welcome to the E-Commerce Application!");
        Scanner scanner = new Scanner(System.in);
        Users loggedInUser = null;

        // Login loop
        while (loggedInUser == null) {
            System.out.println("Please log in:");
            System.out.print("Enter your email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine().trim();

            try {
                // Attempt to retrieve the user by email
                Users user = userService.getUserByEmail(email);
                if (user != null && BCrypt.checkpw(password, user.getUser_password())) {
                    loggedInUser = user;
                    System.out.println("Logged in! Welcome, " + user.getUser_username() + " (" + user.getUser_role() + ")");
                } else {
                    System.out.println("Invalid email or password. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error during login: " + e.getMessage());
            }
        }

        // Display menu for the logged-in user
        roleMenu.displayMenu(loggedInUser);
    }
}
