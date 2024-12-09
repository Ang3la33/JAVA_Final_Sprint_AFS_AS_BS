package org.example.products;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    public List<Products> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public void addProduct(Products product) throws SQLException {
        productDAO.addProduct(product);
    }

    public Products getProductById(int productId) throws SQLException {
        return productDAO.getProductById(productId);
    }

    public List<Products> getProductsBySeller(int sellerId) throws SQLException {
        return productDAO.getProductsBySeller(sellerId);
    }

    public List<Products> searchProducts(String name) throws SQLException {
        return productDAO.searchProducts(name);
    }

    public void updateProduct(Products product) throws SQLException {
        String sql = "UPDATE products SET prod_name = ?, prod_price = ?, prod_quantity = ? WHERE prod_id = ? AND seller_id = ?";

        try {
            productDAO.updateProductInDatabase(sql, product);
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            throw e;
        }
    }

    public void deleteProduct(int productId, int sellerId) throws SQLException {
        productDAO.deleteProduct(productId, sellerId);
    }

    public void updateProductWithUserInput(int sellerID) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the Product ID to update:");
            int productID = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            // Fetch the product details by ID to show current values
            Products existingProduct = productDAO.getProductById(productID);

            if (existingProduct == null || existingProduct.getSeller_id() != sellerID) {
                System.out.println("Error: Product not found or you do not have permission to update this product.");
                return;
            }

            // Display current product details
            System.out.println("Updating Product ID: " + productID);
            System.out.println("Current Name: " + existingProduct.getProd_name());
            System.out.println("Current Price: $" + existingProduct.getProd_price());
            System.out.println("Current Quantity: " + existingProduct.getProd_quantity());

            // Update Name
            System.out.println("Enter new product name (press Enter to keep current):");
            String newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                newName = existingProduct.getProd_name(); // Keep the same name
            }

            // Update Price
            System.out.println("Enter new product price (press Enter to keep current):");
            String priceInput = scanner.nextLine().trim();
            double newPrice = existingProduct.getProd_price(); // Default to current price
            if (!priceInput.isEmpty()) {
                try {
                    newPrice = Double.parseDouble(priceInput);
                    if (newPrice <= 0) {
                        System.out.println("Invalid input: Price must be a positive number. Keeping current price.");
                        newPrice = existingProduct.getProd_price();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Price must be a number. Keeping current price.");
                }
            }

            // Update Quantity
            System.out.println("Enter new product quantity (press Enter to keep current):");
            String quantityInput = scanner.nextLine().trim();
            int newQuantity = existingProduct.getProd_quantity(); // Default to current quantity
            if (!quantityInput.isEmpty()) {
                try {
                    newQuantity = Integer.parseInt(quantityInput);
                    if (newQuantity < 0) {
                        System.out.println("Invalid input: Quantity cannot be negative. Keeping current quantity.");
                        newQuantity = existingProduct.getProd_quantity();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Quantity must be a whole number. Keeping current quantity.");
                }
            }

            // Create updated product
            Products updatedProduct = new Products(productID, newName, newPrice, newQuantity, sellerID);

            // Update the product using productDAO
            updateProduct(updatedProduct);

            System.out.println("Product updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    // Fetch sellers name using seller id
    public String getSellerNameById(int sellerId) throws SQLException {
        return productDAO.getSellerNameById(sellerId);
    }
}
