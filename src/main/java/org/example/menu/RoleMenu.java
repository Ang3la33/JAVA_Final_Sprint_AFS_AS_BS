package org.example.menu;

import org.example.products.Products;
import org.example.products.ProductService;
import org.example.users.UserService;
import org.example.users.Users;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Scanner;

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
        if (user == null) {
            System.out.println("Error: No user logged in.");
            loginOrRegister();
            return;
        }
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
                System.out.println("Invalid role. Redirecting to login.");
        }       loginOrRegister();
    }

    // Method to ask for login or registration
    public void loginOrRegister() {
        Scanner scanner = new Scanner(System.in);
        Users loggedInUser = null;

        // Menu to choose login or registration
        while (loggedInUser == null) {
            System.out.println("Please choose an option:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loggedInUser = loginUser(scanner);
                    if (loggedInUser != null) {
                        System.out.println("Logged in successfully!");
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }

        // Once logged in, display the role-based menu
        displayMenu(loggedInUser);
    }

    // Register a new user
    private void registerUser(Scanner scanner) {
        System.out.println("Register a new user:");

        // Check if there are any existing users. The first user will be set as Admin.
        String role = "BUYER";  // Default role
        try {
            if (userService.getAllUsers().isEmpty()) {
                role = "ADMIN"; // First user will be an Admin
                System.out.println("First user will be set as Admin.");
            } else {
                // If there are already users, only allow "BUYER" or "SELLER"
                System.out.print("Enter role (BUYER/SELLER): ");
                role = scanner.nextLine().toUpperCase();
                while (!role.equals("BUYER") && !role.equals("SELLER")) {
                    System.out.println("Invalid role. Please choose either 'BUYER' or 'SELLER'.");
                    System.out.print("Enter role (BUYER/SELLER): ");
                    role = scanner.nextLine().toUpperCase();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking existing users: " + e.getMessage());
            return;
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Hash the password before saving
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Users newUser = new Users(username, hashedPassword, email, role);

        try {
            userService.addUser(newUser);
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    // Login a user
    private Users loginUser(Scanner scanner) {
        System.out.println("Please log in:");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {
            Users user = userService.getUserByEmail(email);
            if (user != null && BCrypt.checkpw(password, user.getUser_password())) {
                return user; // Successful login
            } else {
                System.out.println("Invalid email or password. Please try again.");
                return null; // Failed login
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return null; // Error occurred
        }
    }

    // Buyer Menu Method
    private void displayBuyerMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Buyer Menu:");
            System.out.println("1. Browse Products");
            System.out.println("2. Search Products");
            System.out.println("3. View Product Details");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            switch (choice) {
                case 1:
                    try {
                        productService.getAllProducts().forEach(System.out::println);
                    } catch (SQLException e) {
                        System.out.println("Error fetching products: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter product name: ");
                    String name = scanner.nextLine();
                    try {
                        productService.searchProducts(name).forEach(System.out::println);
                    } catch (SQLException e) {
                        System.out.println("Error searching products: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Enter product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Clear input buffer
                    try {
                        System.out.println(productService.getProductById(id));
                    } catch (SQLException e) {
                        System.out.println("Error fetching product details: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Exiting Buyer Menu...");
                    running = false;
                    loginOrRegister(); // Redirect to login or register menu
                    return;
                default:
                    System.out.println("Invalid choice: Enter 1, 2, 3, or 4");
            }
        }
    }

    // Seller Menu Method
    private void displaySellerMenu(int sellerID) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
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
                    System.out.println("Exiting Seller Menu...");
                    running = false;
                    loginOrRegister(); // Redirect to login or register menu
                    return;
                default:
                    System.out.println("Invalid choice: Enter 1, 2, 3, 4, or 5.");
            }
        }
    }

    // Method to add a product (Seller Menu)
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

    // Admin Menu Method
    private void displayAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Admin Menu:");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. View All Products");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    viewAllProducts();
                    break;
                case 4:
                    System.out.println("Exiting Admin Menu...");
                    running = false;
                    loginOrRegister(); // Redirect to login or register menu
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }
        }
    }

    // Method to view all users (Admin Menu)
    private void viewAllUsers() {
        try {
            userService.getAllUsers().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
    }

    // Method to delete users (Admin Menu)
    private void deleteUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the user ID to delete: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer

        try {
            userService.deleteUserById(userID);
            System.out.println("User deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    // Method to view all products (Admin Menu)
    private void viewAllProducts() {
        try {
            productService.getAllProducts().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
    }
}
