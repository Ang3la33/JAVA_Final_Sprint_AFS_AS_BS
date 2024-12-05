package org.example.products;

import org.example.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Fetch all products from the database
    public List<Products> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM products";
        List<Products> productList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                productList.add(new Products(
                        rs.getInt("prod_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("prod_price"),
                        rs.getInt("prod_quantity"),
                        rs.getInt("seller_id")
                ));
            }
        }
        return productList;
    }

    // Add a new product to the database
    public void addProduct(Products product) throws SQLException {
        String sql = "INSERT INTO products (prod_name, prod_price, prod_quantity, seller_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getProd_name());
            pstmt.setDouble(2, product.getProd_price());
            pstmt.setInt(3, product.getProd_quantity());
            pstmt.setInt(4, product.getSeller_id());
            pstmt.executeUpdate();
        }
    }

    // Fetch a product by ID
    public Products getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE prod_id = ?";
        Products product = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                product = new Products(
                        rs.getInt("prod_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("prod_price"),
                        rs.getInt("prod_quantity"),
                        rs.getInt("seller_id")
                );
            }
        }
        return product;
    }

    // Search for products by name
    public List<Products> searchProducts(String name) throws SQLException {
        String sql = "SELECT * FROM products WHERE prod_name ILIKE ?";
        List<Products> productList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                productList.add(new Products(
                        rs.getInt("prod_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("prod_price"),
                        rs.getInt("prod_quantity"),
                        rs.getInt("seller_id")
                ));
            }
        }
        return productList;
    }

    // Update a product
    public void updateProduct(Products product) throws SQLException {
        String sql = "UPDATE products SET prod_name = ?, prod_price = ?, prod_quantity = ? WHERE prod_id = ? AND seller_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getProd_name());
            pstmt.setDouble(2, product.getProd_price());
            pstmt.setInt(3, product.getProd_quantity());
            pstmt.setInt(4, product.getProd_id());
            pstmt.setInt(5, product.getSeller_id());
            pstmt.executeUpdate();
        }
    }

    // Delete a product by ID
    public void deleteProduct(int productId, int sellerId) throws SQLException {
        String sql = "DELETE FROM products WHERE prod_id = ? AND seller_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            pstmt.setInt(2, sellerId);
            pstmt.executeUpdate();
        }
    }

    // Fetch all products for a specific seller
    public List<Products> getProductsBySeller(int sellerId) throws SQLException {
        String sql = "SELECT * FROM products WHERE seller_id = ?";
        List<Products> productList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sellerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                productList.add(new Products(
                        rs.getInt("prod_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("prod_price"),
                        rs.getInt("prod_quantity"),
                        rs.getInt("seller_id")
                ));
            }
        }
        return productList;
    }
}
