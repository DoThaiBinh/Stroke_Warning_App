package com.example.appcanhbaodotquy.Models;

public class login {
    private String id;
    private String pass;

    public login() {
    }

    public login(String id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
