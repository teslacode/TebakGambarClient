package com.tebakgambar.model;

/**
 * Model User
 * 
 * @author Ade Fruandta
 */
public class User {
    
    private String id;
    private String userName;

    public User() {
        this.id = "";
        this.userName = "";
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
