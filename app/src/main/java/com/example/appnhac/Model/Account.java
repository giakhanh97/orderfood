package com.example.appnhac.Model;

import java.io.Serializable;


public class Account implements Serializable {

    public Account(int id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    private String userName;
    private String email;

    public Account() {
    }

}
