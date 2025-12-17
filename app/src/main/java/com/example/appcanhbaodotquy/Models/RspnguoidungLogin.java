package com.example.appcanhbaodotquy.Models;

public class RspnguoidungLogin{
    public String sdt;
    public String tennd;

    public RspnguoidungLogin() {
    }

    public RspnguoidungLogin(String sdt, String tennd) {
        this.sdt = sdt;
        this.tennd = tennd;
    }

    public String getTennd() {
        return tennd;
    }

    public void setTennd(String tennd) {
        this.tennd = tennd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
