package org.example.users;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // Get all users
    public List<Users> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    // Add a new user
    public void addUser(Users user) throws SQLException {
        try {
            userDAO.addUser(user);
            System.out.println("User added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding user: " + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Database error occurred while adding the user: " + e.getMessage(), e);
        }

    }

    // Update a user's role by their email
    public void updateUserRole(String email, String newRole) throws SQLException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (newRole == null || newRole.isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty.");
        }

        Users user = userDAO.getUserByEmail(email);
        if (user == null) {
            System.out.println("No user found with the email: " + email);
            return;
        }

        user.setUser_role(newRole);

        try {
            userDAO.updateUser(user);
        } catch (SQLException e) {
            throw new SQLException("Database error occurred while updating user role: " + e.getMessage(), e);
        }
    }

    // Delete a user by ID
    public boolean deleteUserById(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }

        try {
            return userDAO.deleteUser(userId);
        } catch (SQLException e) {
            throw new SQLException("Database error occurred while deleting the user: " + e.getMessage(), e);
        }
    }

    // Get a user by ID
    public Users getUserById(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }

        try {
            Users user = userDAO.getUserById(userId);
            if (user == null) {
                System.out.println("No user found with ID: " + userId);
            }
            return user;
        } catch (SQLException e) {
            throw new SQLException("Database error occurred while retrieving the user: " + e.getMessage(), e);
        }
    }

    // Get a user by email
    public Users getUserByEmail(String email) throws SQLException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        try {
            Users user = userDAO.getUserByEmail(email);
            if (user == null) {
                System.out.println("No user found with email: " + email);
            }
            return user;
        } catch (SQLException e) {
            throw new SQLException("Database error occurred while retrieving the user by email: " + e.getMessage(), e);
        }
    }
}
