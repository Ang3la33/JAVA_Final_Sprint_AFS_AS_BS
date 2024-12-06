package org.example;

import org.example.menu.RoleMenu;
import org.example.products.ProductService;
import org.example.users.UserService;
import org.example.users.Users;

import java.util.Scanner;

public class EcomApp {

    public static void main(String[] args) {
        // Initialize services
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        RoleMenu roleMenu = new RoleMenu(productService, userService);

        // Simulated login process
        System.out.println("Welcome to the E-commerce Application!");
        Scanner scanner = new Scanner(System.in);
        Users loggedInUser = null;

        // Example test user selection
        while (loggedInUser == null) {
            System.out.println("Please log in by selecting a role:");
            System.out.println("1. Buyer");
            System.out.println("2. Seller");
            System.out.println("3. Admin");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer
            switch (choice) {
                case 1:
                    // Simulate Buyer Login
                    loggedInUser = new Users(1, "test_buyer", "password", "buyer@test.com", "BUYER");
                    break;
                case 2:
                    // Simulate Seller Login
                    loggedInUser = new Users(2, "test_seller", "password", "seller@test.com", "SELLER");
                    break;
                case 3:
                    // Simulate Admin Login
                    loggedInUser = new Users(3, "test_admin", "password", "admin@test.com", "ADMIN");
                    break;
                case 4:
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }
        }

        // Display the menu for the logged-in user
        roleMenu.displayMenu(loggedInUser);
    }
}
