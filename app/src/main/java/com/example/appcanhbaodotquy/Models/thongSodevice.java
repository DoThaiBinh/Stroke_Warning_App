package com.example.appcanhbaodotquy.Models;

import java.io.Serializable;

public class thongSodevice implements Serializable {
    private String idThietBi;
    private String thoiGian;
    private String toaDoX;
    private String toaDoY;
    private int nhipTim;
    private double nhietDo;
    private int luongOxiTrongMau;
    private int gluco;
    private int dbp;
    private int sbp;
    private String tinhTrang;

    public thongSodevice() {
    }

    public thongSodevice(String tinhTrang, int SBP, int DBP, int gluco, int luongOxiTrongMau, double nhietDo, int nhipTim, String toaDoY, String toaDoX, String thoiGian, String idThietBi) {
        this.tinhTrang = tinhTrang;
        this.sbp = SBP;
        this.dbp = DBP;
        this.gluco = gluco;
        this.luongOxiTrongMau = luongOxiTrongMau;
        this.nhietDo = nhietDo;
        this.nhipTim = nhipTim;
        this.toaDoY = toaDoY;
        this.toaDoX = toaDoX;
        this.thoiGian = thoiGian;
        this.idThietBi = idThietBi;
    }

    public int getDBP() {
        return dbp;
    }

    public void setDBP(int DBP) {
        this.dbp = DBP;
    }

    public int getSBP() {
        return sbp;
    }

    public void setSBP(int SBP) {
        this.sbp = SBP;
    }

    public String getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(String idThietBi) {
        this.idThietBi = idThietBi;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getToaDoX() {
        return toaDoX;
    }

    public void setToaDoX(String toaDoX) {
        this.toaDoX = toaDoX;
    }

    public String getToaDoY() {
        return toaDoY;
    }

    public void setToaDoY(String toaDoY) {
        this.toaDoY = toaDoY;
    }

    public int getNhipTim() {
        return nhipTim;
    }

    public void setNhipTim(int nhipTim) {
        this.nhipTim = nhipTim;
    }

    public double getNhietDo() {
        return nhietDo;
    }

    public void setNhietDo(double nhietDo) {
        this.nhietDo = nhietDo;
    }

    public int getLuongOxiTrongMau() {
        return luongOxiTrongMau;
    }

    public void setLuongOxiTrongMau(int luongOxiTrongMau) {
        this.luongOxiTrongMau = luongOxiTrongMau;
    }

    public int getGluco() {
        return gluco;
    }

    public void setGluco(int gluco) {
        this.gluco = gluco;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
