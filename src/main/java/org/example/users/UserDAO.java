package org.example.users;

import org.example.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Fetch all users
    public List<Users> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<Users> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Extract data from the ResultSet
                int user_id = rs.getInt("user_id");
                String user_username = rs.getString("user_username");
                String user_email = rs.getString("user_email");
                String user_role = rs.getString("user_role");

                // Print user details
                System.out.println("User ID: " + user_id);
                System.out.println("Username: " + user_username);
                System.out.println("Email: " + user_email);
                System.out.println("Role: " + user_role);
                System.out.println("------------------------");

                // Add user to the list
                users.add(new Users(user_id, user_username, null, user_email, user_role)); // Password excluded for security
            }
        }
        return users;
    }

    // Add a new user
    public void addUser(Users user) throws SQLException {
        if (isEmailTaken(user.getUser_email())) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        String sql = "INSERT INTO users (user_username, user_password, user_email, user_role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUser_username());
            pstmt.setString(2, user.getUser_password());
            pstmt.setString(3, user.getUser_email());
            pstmt.setString(4, user.getUser_role());

            pstmt.executeUpdate();
        }
    }

    // Check if email is already in use
    public boolean isEmailTaken(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE user_email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    // Update an existing user's role
    public void updateUser(Users user) throws SQLException {
        if (user.getUser_email() == null || user.getUser_email().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty.");
        }

        String sql = "UPDATE users SET user_role = ? WHERE user_email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUser_role());
            pstmt.setString(2, user.getUser_email());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("No user found with this email.");
            }
        }
    }

    // Delete a user by ID
    public boolean deleteUser(int user_id) throws SQLException {
        if (user_id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user_id);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User with ID " + user_id + " deleted successfully.");
                return true;
            } else {
                System.out.println("No user found with ID " + user_id + ".");
                return false;
            }
        }
    }

    // Get user by email
    public Users getUserByEmail(String email) throws SQLException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        String sql = "SELECT * FROM users WHERE user_email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Users(
                        rs.getInt("user_id"),
                        rs.getString("user_username"),
                        rs.getString("user_password"),
                        rs.getString("user_email"),
                        rs.getString("user_role")
                );
            }
        }
        System.out.println("No user found with email: " + email);
        return null;
    }

    // Get user by ID
    public Users getUserById(int user_id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Users(
                        rs.getInt("user_id"),
                        rs.getString("user_username"),
                        rs.getString("user_password"),
                        rs.getString("user_email"),
                        rs.getString("user_role")
                );
            }
        }
        return null;
    }
}
