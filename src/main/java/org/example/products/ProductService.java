package org.example.products;

import java.sql.SQLException;

public class ProductService {

    private ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    // Fetch all products
    public void getAllProducts() throws SQLException {
        productDAO.getAllProducts();
    }

    // Add a product
    public void addProduct(org.example.products.Product product) throws SQLException {
        productDAO.addProduct(product);
    }

}
