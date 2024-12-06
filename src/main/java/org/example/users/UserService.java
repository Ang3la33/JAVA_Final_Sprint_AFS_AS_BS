package org.example.users;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void getAllUsers() throws SQLException {
        userDAO.getAllUsers();
    }

    public void addUser(Users user) throws SQLException{
        userDAO.addUser(user);
    }

}
