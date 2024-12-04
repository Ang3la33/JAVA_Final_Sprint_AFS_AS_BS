package org.example.users;

import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // fetch all users
    public void getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String user_username = rs.getString("user_username");
                String user_email = rs.getString("user_email");
                String user_role = rs.getString("user_role");

                System.out.println("User ID: " + user_id);
                System.out.println("Username: " + user_username);
                System.out.println("Email: " + user_email);
                System.out.println("Role: " + user_role);
                System.out.println("------------------------");
            }
        }
    }

    // add a user
    public void addUser(Users user) throws SQLException {
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

    // Update an existing user
    public void updateUser(Users user) throws SQLException {
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

}