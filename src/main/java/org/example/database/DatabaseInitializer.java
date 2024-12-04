package org.example.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                user_id SERIAL PRIMARY KEY,
                user_username VARCHAR(50) NOT NULL,
                user_password VARCHAR(255) NOT NULL,
                user_email VARCHAR(100) NOT NULL UNIQUE,
                user_role VARCHAR(20) NOT NULL
            );
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createUsersTable);
            System.out.println("Users table initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
