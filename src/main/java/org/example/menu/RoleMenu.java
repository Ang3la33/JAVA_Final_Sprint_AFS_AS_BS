package org.example.menu;

import org.example.products.Products;
import org.example.products.ProductService;
import org.example.users.UserService;
import org.example.users.Users;

import java.sql.SQLException;
import java.util.Scanner;

// Class to organize role-based functionality with menu options
public class RoleMenu {

    private ProductService productService;
    private UserService userService;

    // Constructor - initialize RoleMenu object with references to ProductService and UserService
    public RoleMenu(ProductService productService, UserService userService) {
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
        scanner.nextLine(); // Clear input buffer
        switch (choice) {
            case 1: // Browse Products
                try {
                    productService.getAllProducts().forEach(System.out::println);
                } catch (SQLException e) {
                    System.out.println("Error fetching products: " + e.getMessage());
                }
                break;
            case 2: // Search Products by name
                System.out.println("Enter product name: ");
                String name = scanner.nextLine();
                try {
                    productService.searchProducts(name).forEach(System.out::println);
                } catch (SQLException e) {
                    System.out.println("Error searching products: " + e.getMessage());
                }
                break;
            case 3: // View Product Details by ID
                System.out.println("Enter product ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Clear input buffer
                try {
                    System.out.println(productService.getProductById(id));
                } catch (SQLException e) {
                    System.out.println("Error fetching product details: " + e.getMessage());
                }
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
        scanner.nextLine(); // Clear input buffer
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

    // Add Product Method
    private void addProduct(int sellerID) {
        Scanner scanner = new Scanner(System.in);
        String name = "";
        double price = 0.0;
        int quantity = 0;

        try {
            // Get Product Name
            while (true) {
                System.out.println("Enter product name: ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input: Product name cannot be empty.");
            }

            // Get Product Price
            while (true) {
                System.out.println("Enter product price: ");
                if (scanner.hasNextDouble()) {
                    price = scanner.nextDouble();
                    if (price > 0) {
                        break;
                    }
                } else {
                    scanner.next(); // Clear invalid input
                }
                System.out.println("Invalid input: Price must be a positive number.");
            }

            // Get Product Quantity
            while (true) {
                System.out.println("Enter product quantity: ");
                if (scanner.hasNextInt()) {
                    quantity = scanner.nextInt();
                    if (quantity >= 0) {
                        break;
                    }
                } else {
                    scanner.next(); // Clear invalid input
                }
                System.out.println("Invalid input: Quantity cannot be negative.");
            }

            // Create Product
            Products product = new Products(0, name, price, quantity, sellerID);
            productService.addProduct(product);
            System.out.println("Product added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    // Update Product Method
    private void updateProduct(int sellerID) {
        Scanner scanner = new Scanner(System.in);
        int productID = 0;
        String name = "";
        double price = 0.0;
        int quantity = 0;

        try {
            // Get Product ID to update
            while (true) {
                System.out.println("Enter the product ID to update: ");
                if (scanner.hasNextInt()) {
                    productID = scanner.nextInt();
                    if (productID > 0) {
                        break;
                    }
                } else {
                    scanner.next(); // Clear invalid input
                }
                System.out.println("Invalid input: Product ID must be a positive integer.");
            }
            scanner.nextLine(); // Clear input buffer

            // Get Updated Product Name
            while (true) {
                System.out.println("Enter new product name: ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input: Product name cannot be empty.");
            }

            // Get Updated Product Price
            while (true) {
                System.out.println("Enter new product price: ");
                if (scanner.hasNextDouble()) {
                    price = scanner.nextDouble();
                    if (price > 0) {
                        break;
                    }
                } else {
                    scanner.next(); // Clear invalid input
                }
                System.out.println("Invalid input: Must be a positive number.");
            }

            // Get Updated Product Quantity
            while (true) {
                System.out.println("Enter new product quantity: ");
                if (scanner.hasNextInt()) {
                    quantity = scanner.nextInt();
                    if (quantity >= 0) {
                        break;
                    }
                } else {
                    scanner.next(); // Clear invalid input
                }
                System.out.println("Invalid input: Quantity cannot be negative.");
            }

            // Create Product with updated details
            Products product = new Products(productID, name, price, quantity, sellerID);
            productService.updateProduct(product);
            System.out.println("Product updated successfully!");

        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    // Delete Product Method
    private void deleteProduct(int sellerID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the product ID to delete: ");
        int productID = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer

        try {
            productService.deleteProduct(productID, sellerID);
            System.out.println("Product deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    // View Seller Products
    private void viewSellerProducts(int sellerID) {
        try {
            productService.getProductsBySeller(sellerID).forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error fetching seller's products: " + e.getMessage());
        }
    }

    // Admin Menu Method (Placeholder for Now)
    private void displayAdminMenu() {
        System.out.println("Admin functionality is under development.");
    }
}
