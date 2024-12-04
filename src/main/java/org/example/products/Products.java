package org.example.products;

public class Products {
    private int prod_id;
    private String prod_name;
    private double prod_price;
    private double prod_quantity;
    private String seller_id;

    // Constructor for creating a product without an ID (e.g., before saving to the database)
    public Products(String prod_name, double prod_price, double prod_quantity, String seller_id) {
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_quantity = prod_quantity;
        this.seller_id = seller_id;
    }

    // Constructor for creating a product with an ID (e.g., after fetching from the database)
    public Products(int prod_id, String prod_name, double prod_price, double prod_quantity, String seller_id) {
        this.prod_id = prod_id;
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_quantity = prod_quantity;
        this.seller_id = seller_id;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public double getProd_price() {
        return prod_price;
    }

    public void setProd_price(double prod_price) {
        this.prod_price = prod_price;
    }

    public double getProd_quantity() {
        return prod_quantity;
    }

    public void setProd_quantity(double prod_quantity) {
        this.prod_quantity = prod_quantity;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
}
