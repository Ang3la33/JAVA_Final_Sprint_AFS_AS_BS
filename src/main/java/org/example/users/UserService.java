package org.example.users;

import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO;

    public UserService() {

        userDAO = new UserDAO();
    }

    public void getAllUsers() throws SQLException {
        userDAO.getAllUsers();
    }

    public void addUser(Users user) throws SQLException{
        userDAO.addUser(user);
    }

}
