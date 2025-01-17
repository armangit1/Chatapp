package com.appyfi.spennar.Model;

public class UserData {

    String userID;

    String name;
    String email;
    String password;

    public UserData() {

    }

    public UserData(String name, String email,String password,String userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
