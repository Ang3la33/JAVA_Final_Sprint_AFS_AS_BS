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

        System.out.println("Welcome to the E-Commerce Application!");
        Scanner scanner = new Scanner(System.in);
        Users loggedInUser = null;

        // Start by asking the user whether to register or login
        roleMenu.loginOrRegister(); // This is where the program first asks to register or login

        // After logging in or registering, we proceed to the role-based menu
        roleMenu.displayMenu(loggedInUser); // Display the menu once the user is logged in
    }
}
