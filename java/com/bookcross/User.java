package com.bookcross;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean online;

    /*public User(String id, String name, boolean online) {
        this.id = id;
        this.name = name;
        this.online = online;
    }*/

    public void setId(String id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public User(String id, String name, String email, String password, boolean online) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.online = online;
    }

    public User(){
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return online;
    }
}