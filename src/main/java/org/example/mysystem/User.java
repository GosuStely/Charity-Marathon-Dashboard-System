package org.example.mysystem;

public class User {
    private String username;
    private String role;
    private String password;
    public User(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }
    public boolean checkAccount(String password, String username){
        return this.password.equals(password) && this.username.equals(username);
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
