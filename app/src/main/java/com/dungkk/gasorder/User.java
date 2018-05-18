package com.dungkk.gasorder;

public class User {
    private String firstName;
    private String lastName;
    private static String username;
    private String password;
    private String phoneNumber;
    private String email;

    public User(String name){
        username = name;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }
}
