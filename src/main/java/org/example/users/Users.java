package org.example.users;

public class Users {

    private int user_id;
    private static String user_username;
    private static String user_password;
    private String user_role;

    public Users(String user_username, String user_password, String user_role) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_role = user_role;
    }

    public Users(int user_id, String user_username, String user_password, String user_role) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_role = user_role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public static String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
