package org.example.users;

import org.example.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public void getCars(){
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

//        pstmt.setString(1, users.getUsers_username());
//        pstmt.setString(2, users.getUsers_password());
//        pstmt.setString(3, users.getUsers_role());
//        pstmt.executeUpdate();

            //Pickup video at ~30 minutes
//    }

        }
}
