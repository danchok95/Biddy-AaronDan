package com.example.user.biddyv3;

public class User {

    private String userID, username, name, emailAddress, password, address;
    private Integer contactNo;

    public User() {}

    public User(String userID, String username, String name, String emailAddress, String password, Integer contactNo, String address) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.contactNo = contactNo;
        this.address = address;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() { return username; }

    public String getName() { return name; }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Integer getContactNo() {
        return contactNo;
    }

    public String getAddress() { return address; }
}

