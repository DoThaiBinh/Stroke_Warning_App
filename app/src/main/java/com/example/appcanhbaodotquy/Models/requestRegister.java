package com.example.appcanhbaodotquy.Models;

public class requestRegister {
    private String Id;
    private String Name;
    private String email;
    private String password;

    public requestRegister() {
    }

    public requestRegister(String id, String name, String email, String password) {
        Id = id;
        Name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
}
