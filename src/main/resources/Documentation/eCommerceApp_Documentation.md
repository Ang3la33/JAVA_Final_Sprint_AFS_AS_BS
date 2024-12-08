# E-Commerce Application Documentation

## Table of Contents
- [Application Description](#application-description)
- [Class Overview](#class-overview)
    - [EcomApp](#ecomapp)
    - [RoleMenu](#rolemenu)
    - [ProductService](#productservice)
    - [UserService](#userservice)
    - [Products](#products)
    - [Users](#users)
- [Usage Guide](#usage-guide)

---

## Application Description
Welcome to our E-Commerce App! A command line platform designed to bring buyers, sellers and administrators together in one system.
Whether you're looking to browse products, manage your inventory or over user activity, this application provides the tools you need
in a simple, intuitive way.

**Main Features**
- **Buyers** Can explore products, search for specific items, and read detailed product information.
- **Sellers** Have the ability to add new products, edit existing ones, or remove items that are no longer available.
- **Admins** Oversee the platform, with the power to manage users, view all product, and maintain control of the system.

This application was built with a focus on modularity and scalability, and showcases key principles of Object-Oriented Programming,
like inheritance and polymorphism. Each user role comes with tailored menus and options, ensuring everyone has access to what they need.

This application was collaboratively developed by **Brandon Shea**, **Adam Stevenson**, and **Angie Flynn-Smith**, for 
Keyin College's Semester 3 Java final project.


---

## Class Overview

### [EcomApp](#ecomapp)
**Description:** Entry point for the application.  
**Responsibilities:** Initializes core components and manages the main application flow.  
**Key Methods:**
- `main(String[] args)`: Bootstraps the application.

---

### [RoleMenu](#rolemenu)
**Description:** Manages user interactions based on their roles.  
**Responsibilities:**
- Handles role-based menus and functionalities.
- Manages login and registration workflows.  
  **Key Methods:**
- `displayMenu(Users user)`: Displays the menu based on the logged-in user's role.
- `loginOrRegister()`: Prompts users to either log in or register.
- `viewSellerProducts(int sellerID)`: Lists products specific to a seller.

---

### [ProductService](#productservice)
**Description:** Handles all product-related business logic.  
**Responsibilities:**
- Retrieve, add, update, and delete products.
- Interact with the database for product data.  
  **Key Methods:**
- `getAllProducts()`: Returns a list of all products.
- `getProductsBySeller(int sellerID)`: Retrieves products for a specific seller.
- `addProduct(Products product)`: Adds a new product.

---

### [UserService](#userservice)
**Description:** Manages all user-related logic.  
**Responsibilities:**
- Authenticate users during login.
- Handle user registration and deletion.  
  **Key Methods:**
- `getAllUsers()`: Retrieves all registered users.
- `getUserById(int userID)`: Finds a user by their ID.
- `addUser(Users user)`: Adds a new user.

---

### [Products](#products)
**Description:** Represents a product in the application.  
**Attributes:**
- `prod_id`: Unique identifier for a product.
- `prod_name`: Name of the product.
- `prod_price`: Price of the product.
- `prod_quantity`: Quantity available.
- `seller_id`: ID of the seller who owns the product.  
  **Responsibilities:** Acts as a data model for products.

---

### [Users](#users)
**Description:** Represents a user in the system.  
**Attributes:**
- `user_id`: Unique identifier for a user.
- `user_username`: Username.
- `user_password`: Password (hashed).
- `user_email`: Email address.
- `user_role`: Role (Admin, Buyer, Seller).  
  **Responsibilities:** Acts as a data model for users.

---

## Usage Guide
<a id="usage-guide"></a>

### Getting Started
1. **Clone the Project**: Download or clone the project repository from your version control platform.
2. **Set Up the Database**: Ensure your PostgreSQL database is configured. Use the provided SQL scripts to set up the required tables (`users` and `products`).
3. **Install Dependencies**: Use Maven to install the required dependencies by running:
   ```bash
   mvn clean install


---

---

### Application Workflow
<a id="application-workflow"></a>

#### Login or Register
- **Login**:
  - Enter your registered email and password to access the application.
  - Role-specific menus will be displayed based on your user type.
- **Register**:
  - Enter your details, including username, email, and password.
  - The system will automatically assign roles:
    - The first registered user is designated as an **Admin**.
    - Subsequent users can choose between the roles **Buyer** or **Seller**.

---

### Role-Specific Menus
<a id="role-specific-menus"></a>

#### Buyer Menu
**Buyers** have the following options:
- **Browse Products**: Displays a list of all available products, showing:
  - Product name.
  - Price.
  - Quantity.
  - Product ID.
- **Search Products**: Enter a keyword to search for specific products by name.
- **View Product Details**: Provide a product ID to view its details, including:
  - Product name.
  - Price.
  - Quantity.
  - Product ID.
- **Exit**: Logs out and redirects to the login or registration menu.


---

#### Seller Menu
**Sellers** have the following options:
- **Add Product**: Create a new product by providing:
  - Product name.
  - Price.
  - Quantity.
- **Update Product**: Modify details of existing products in your inventory, including:
  - Product name.
  - Price.
  - Quantity.
- **Delete Product**: Remove a product from your inventory using its unique product ID.
- **View My Products**: Lists all products you have created, showing:
  - Product name.
  - Price.
  - Quantity.
  - Product ID.
- **Exit**: Logs out and redirects to the login or registration menu.


---

#### Admin Menu
**Admins** have the following options:
- **View All Products**:
  - Lists all products on the platform, including:
    - Product name.
    - Price.
    - Quantity.
    - Product ID.
- **View All Users**:
  - Displays all registered users, including their:
    - Username.
    - Email.
    - Role (Admin, Buyer, or Seller).
- **Delete User**:
  - Remove a user from the system using their unique user ID.
  - Admins cannot delete their own account.
- **Exit**: Logs out and redirects to the login or registration menu.


