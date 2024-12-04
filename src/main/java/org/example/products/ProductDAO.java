package org.example.products;

import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {

    // Fetch all products from the database
    public void getAllProducts() throws SQLException {
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int prod_id = rs.getInt("prod_id");
                String prod_name = rs.getString("prod_name");
                double prod_price = rs.getDouble("prod_price");
                double prod_quantity = rs.getDouble("prod_quantity");
                String seller_id = rs.getString("seller_id");

                // Print product details
                System.out.println("Product ID: " + prod_id);
                System.out.println("Product Name: " + prod_name);
                System.out.println("Product Price: " + prod_price);
                System.out.println("Product Quantity: " + prod_quantity);
                System.out.println("Seller ID: " + seller_id);
                System.out.println("------------------------");
            }
        }
    }

    // Add a new product to the database
    public void addProduct(Products product) throws SQLException {
        String sql = "INSERT INTO products (prod_name, prod_price, prod_quantity, seller_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getProd_name());
            pstmt.setDouble(2, product.getProd_price());
            pstmt.setDouble(3, product.getProd_quantity());
            pstmt.setString(4, product.getSeller_id());

            pstmt.executeUpdate();
        }
    }
}
