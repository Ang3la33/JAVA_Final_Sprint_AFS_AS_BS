package org.example.products;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    // Fetch all products
    public List<Products> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    // Add a product
    public void addProduct(Products product) throws SQLException {
        productDAO.addProduct(product);
    }

    // Fetch a product by ID
    public Products getProductById(int productId) throws SQLException {
        return productDAO.getProductById(productId);
    }

    // Search for products by name
    public List<Products> searchProducts(String name) throws SQLException {
        return productDAO.searchProducts(name);
    }

    // Update a product
    public void updateProduct(Products product) throws SQLException {
        productDAO.updateProduct(product);
    }

    // Delete a product
    public void deleteProduct(int productId, int sellerId) throws SQLException {
        productDAO.deleteProduct(productId, sellerId);
    }

    // Fetch all products by a specific seller
    public List<Products> getProductsBySeller(int sellerId) throws SQLException {
        return productDAO.getProductsBySeller(sellerId);
    }
}
