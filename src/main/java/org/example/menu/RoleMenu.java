package org.example.menu;

import org.example.products.ProductService;
import org.example.users.UserService;
import org.example.users.Users;

import java.util.Scanner;

// Class to organize role-based functionality with menu options
public class RoleMenu {

    private ProductService productService;
    private UserService userService;

    // Constructor - initialize RoleMenu object with references to ProductService and UserService
    public RoleMenu (ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // Method to display User Menu based on role
    public void displayMenu(Users user) {
        switch (user.getUser_role().toUpperCase()) {
            case "BUYER":
                displayBuyerMenu();
                break;
            case "SELLER":
                displaySellerMenu(user.getUser_id());
                break;
            case "ADMIN":
                displayAdminMenu();
                break;
            default:
                System.out.println("Invalid role.");
        }
    }

    // Buyer Menu Method
    private void displayBuyerMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Buyer Menu:");
        System.out.println("1. Browse Products");
        System.out.println("2. Search Products");
        System.out.println("3. View Product Details");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1: // Browse Products
                productService.getAllProducts().forEach(System.out::println);
                break;
            case 2: // Search Products by name
                System.out.println("Enter product name: ");
                String name = scanner.next();
                productService.searchProducts(name).forEach(System.out::println);
                break;
            case 3: // View Product Details by ID
                System.out.println("Enter product ID: ");
                int id = scanner.nextInt();
                System.out.println(productService.getProductById(id));
                break;
            case 4: // Exit Program
                return;
            default:
                System.out.println("Invalid choice: Enter 1, 2, 3, or 4");
        }
        // Display menu again so that user can perform multiple actions without restarting program
        displayBuyerMenu();
    }

    // Seller Menu Method
    private void displaySellerMenu(int sellerID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seller Menu:");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. View My Products");
        System.out.println("5. Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                addProduct(sellerID);
                break;
            case 2:
                updateProduct(sellerID);
                break;
            case 3:
                deleteProduct(sellerID);
                break;
            case 4:
                viewSellerProducts(sellerID);
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice: Enter 1, 2, 3, 4, or 5.");
        }
        // Display seller menu again
        displaySellerMenu(sellerID);
    }

    // Methods called in Seller Menu
    // (addProduct, updateProduct, deleteProduct, viewSellerProducts)

    // Add Product Method
    private void addProduct(int sellerID) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter ")
        }

    }




}


