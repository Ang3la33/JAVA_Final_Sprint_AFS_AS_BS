package org.example.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        // SQL for creating the users table
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                user_id SERIAL PRIMARY KEY,
                user_username VARCHAR(50) NOT NULL,
                user_password VARCHAR(255) NOT NULL,
                user_email VARCHAR(100) NOT NULL UNIQUE,
                user_role VARCHAR(20) NOT NULL
            );
        """;

        // SQL for creating the products table
        String createProductsTable = """
            CREATE TABLE IF NOT EXISTS products (
                prod_id SERIAL PRIMARY KEY,
                prod_name VARCHAR(100) NOT NULL,
                prod_price DECIMAL(10, 2) NOT NULL,
                prod_quantity DECIMAL(10, 2) NOT NULL,
                seller_id INT REFERENCES users(user_id)
            );
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            // Create users table
            statement.execute(createUsersTable);
            System.out.println("Users table initialized successfully.");

            // Create products table
            statement.execute(createProductsTable);
            System.out.println("Products table initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Initialize the database tables
        initializeDatabase();
    }
}
