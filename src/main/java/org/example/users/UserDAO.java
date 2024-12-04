package org.example.users;

import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public void getAllUsers() throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM USERS";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

        rs = pstmt.executeQuery();
        while(rs.next()) {
            int user_id = rs.getInt("user_id");
            String user_username = rs.getString("user_username");
            String user_password = rs.getString("user_password");
            String user_role = rs.getString("user_role");

            System.out.println("user_id: " + user_id);
            System.out.println("user_username: " + user_username);
            System.out.println("user_password: " + user_password);
            System.out.println("user_role: " + user_role);
            System.out.println("------------------------");
                        }

            //Pickup video at ~30 minutes

                                                                }

    }
    public static void addUser(Users user) throws SQLException{
        String sql = "INSERT INTO Users(User_username, User_password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Users.getUser_username());
            preparedStatement.setString(2, Users.getUser_password());

            preparedStatement.executeUpdate();
        }
    }
}
