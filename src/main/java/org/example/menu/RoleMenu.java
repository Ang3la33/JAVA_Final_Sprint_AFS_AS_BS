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

    // Constructor
    public RoleMenu(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // Display the role-based menu
    public void displayMenu(Users user) {
        if (user == null) {
            System.out.println("Error: No user logged in.");
            loginOrRegister();
            return;
        }
        switch (user.getUser_role().toUpperCase()) {
            case "BUYER" -> displayBuyerMenu();
            case "SELLER" -> displaySellerMenu(user.getUser_id());
            case "ADMIN" -> displayAdminMenu(user); // Pass the logged-in Admin user
            default -> {
                System.out.println("Invalid role. Redirecting to login.");
                loginOrRegister();
            }
        }
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
                case 1 -> registerUser(scanner);
                case 2 -> {
                    loggedInUser = loginUser(scanner);
                    if (loggedInUser != null) {
                        System.out.println("Logged in successfully!");
                        displayMenu(loggedInUser);
                    }
                }
                case 3 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    // Register a new user
    private void registerUser(Scanner scanner) {
        System.out.println("Register a new user:");

        // Determine role
        String role = "BUYER"; // Default role
        try {
            if (userService.getAllUsers().isEmpty()) {
                role = "ADMIN"; // First user is Admin
                System.out.println("First user will be set as Admin.");
            } else {
                role = getValidRole(scanner);
            }

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Hash the password
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            Users newUser = new Users(username, hashedPassword, email, role);

            userService.addUser(newUser);

        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    // Get a valid role from the user
    private String getValidRole(Scanner scanner) {
        while (true) {
            System.out.print("Enter role (BUYER/SELLER): ");
            String role = scanner.nextLine().toUpperCase();
            if (role.equals("BUYER") || role.equals("SELLER")) {
                return role;
            }
            System.out.println("Invalid role. Please choose either 'BUYER' or 'SELLER'.");
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
                System.out.println("Invalid email or password.");
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return null;
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
                case 1 -> viewAllProducts();
                case 2 -> searchProducts(scanner);
                case 3 -> viewProductDetails(scanner);
                case 4 -> {
                    System.out.println("Exiting Buyer Menu...");
                    running = false;
                    loginOrRegister(); // Redirect to login or register menu
                }
                default -> System.out.println("Invalid choice: Enter 1, 2, 3, or 4");
            }
        }
    }

    private void searchProducts(Scanner scanner) {
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();
        try {
            productService.searchProducts(name).forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println("Error searching products: " + e.getMessage());
        }
    }

    private void viewProductDetails(Scanner scanner) {
       int id = getValidInput(scanner, "Enter product ID: ");
       try {
           Products product = productService.getProductById(id);
           if (product != null) {
               String sellerName = productService.getSellerNameById(product.getSeller_id());
               System.out.println("-----------------------");
               System.out.println("Product Name: " + product.getProd_name());
               System.out.println("ID: " + product.getSeller_id());
               System.out.println("Price: $" + product.getProd_price());
               System.out.println("Quantity: " + product.getProd_price());
               System.out.println("Seller: " + (sellerName != null ? sellerName : "Unknown Seller"));
               System.out.println("-----------------------");
           } else {
               System.out.println("Product with ID " + id + " not found.");
           }
       } catch (SQLException e) {
           System.err.println("Error fetching product details: " + e.getMessage());
       }
    }

    private int getValidInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a number.");
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
                case 1 -> addProduct(sellerID);
                case 2 -> productService.updateProductWithUserInput(sellerID);
                case 3 -> deleteProduct(sellerID);
                case 4 -> viewSellerProducts(sellerID);
                case 5 -> {
                    System.out.println("Exiting Seller Menu...");
                    running = false;
                    loginOrRegister(); // Redirect to login or register menu
                }
                default -> System.out.println("Invalid choice: Enter 1, 2, 3, 4, or 5.");
            }
        }
    }

    // Add Product Method
    private void addProduct(int sellerID) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();

        Products product = new Products(0, name, price, quantity, sellerID);
        try {
            productService.addProduct(product);
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    // Delete Product Method
    private void deleteProduct(int sellerID) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the product ID to delete: ");
        int productID = scanner.nextInt();

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
            System.out.println("List of Products:");
            System.out.println("------------------");
            productService.getProductsBySeller(sellerID).forEach(product -> {
                System.out.println("Product ID: " + product.getProd_id());
                System.out.println("Product Name: " + product.getProd_name());
                System.out.println("Price: $" + product.getProd_price());
                System.out.println("Quantity: " + product.getProd_quantity());
                System.out.println("--------------------------");
            });
        } catch (SQLException e) {
            System.out.println("Error fetching seller's products: " + e.getMessage());
        }
    }

    // Admin Menu Method
    private void displayAdminMenu(Users loggedInAdmin) {
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
                case 1 -> viewAllUsers();
                case 2 -> deleteUser(loggedInAdmin);
                case 3 -> viewAllProducts();
                case 4 -> {
                    System.out.println("Exiting Admin Menu...");
                    running = false;
                    loginOrRegister();
                }
                default -> System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }
        }
    }

    // View All Users (Admin Menu)
    private void viewAllUsers() {
        try {
            System.out.println("List of all users:");
            userService.getAllUsers().forEach(user -> {
                System.out.println("Name: " + user.getUser_username());
                System.out.println("ID: " + user.getUser_id());
                System.out.println("Role: " + user.getUser_role());
                System.out.println("Email: " + user.getUser_email());
                System.out.println("--------------------");
            });
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
    }

    // Method to delete User (Admin Menu)
    private void deleteUser(Users loggedInAdmin) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the user ID to delete: ");
        int userID = scanner.nextInt();

        if (userID == loggedInAdmin.getUser_id()) {
            System.out.println("Error: Admin cannot delete own account!");
            return;
        }

        try {
            if (userService.deleteUserById(userID)) {
                System.out.println("User deleted successfully!");
            } else {

                System.out.println("User with ID " + userID + "not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

        // Method to view all products (Admin Menu)
        private void viewAllProducts () {
            try {
                System.out.println("List of all products:");
                System.out.println("----------------------");
                productService.getAllProducts().forEach(product -> {
                    try {
                        // Retrieve seller name using seller ID
                        String sellerName = productService.getSellerNameById(product.getSeller_id());

                        // Display product details
                        System.out.println("Product ID: " + product.getProd_id());
                        System.out.println("Name: " + product.getProd_name());
                        System.out.println("Price: $" + product.getProd_price());
                        System.out.println("Quantity: " + product.getProd_quantity());
                        System.out.println("Seller Name: " + sellerName;
                        System.out.println("---------------------");
                    }
                    catch (SQLException e) {
                        System.out.println("Error fetching seller details for Product ID " + product.getProd_id() + ":" + e.getMessage());
                    }
                });
            } catch (SQLException e) {
                System.out.println("Error fetching products: " + e.getMessage());
            }
        }
}

