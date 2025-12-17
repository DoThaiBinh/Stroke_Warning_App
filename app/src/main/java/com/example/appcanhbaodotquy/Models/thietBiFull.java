package com.example.appcanhbaodotquy.Models;

import java.io.Serializable;

public class thietBiFull implements Serializable {
    private String idThietBi;
    private String tenNguoiDung;
    private int tuoi;
    private double bmi;
    private boolean gioiTinh;
    private boolean hutThuoc;
    private int soDieuThuoc1Ngay;
    private boolean dungThuocHuyetAp;
    private boolean daBiDotQuy;
    private boolean tangHuyetAp;
    private boolean tieuDuong;
    private int cholesterol ;
    private String thoiGian;
    private int nhipTim;
    private double nhietDo;
    private int luongOxiTrongMau;
    private int gluco;

    private String toaDoX;
    private String toaDoY;
    private int dbp;
    private int sbp;
    private String tinhTrang;

    public thietBiFull() {
    }

    public thietBiFull(String idThietBi, String tenNguoiDung, int tuoi, double bmi, boolean gioiTinh, boolean hutThuoc, int soDieuThuoc1Ngay, boolean dungThuocHuyetAp, boolean daBiDotQuy, boolean tangHuyetAp, boolean tieuDuong, int cholesterol, String thoiGian, int nhipTim, double nhietDo, int luongOxiTrongMau, int gluco, String toaDoX, String toaDoY, int dbp, int sbp, String tinhTrang) {
        this.idThietBi = idThietBi;
        this.tenNguoiDung = tenNguoiDung;
        this.tuoi = tuoi;
        this.bmi = bmi;
        this.gioiTinh = gioiTinh;
        this.hutThuoc = hutThuoc;
        this.soDieuThuoc1Ngay = soDieuThuoc1Ngay;
        this.dungThuocHuyetAp = dungThuocHuyetAp;
        this.daBiDotQuy = daBiDotQuy;
        this.tangHuyetAp = tangHuyetAp;
        this.tieuDuong = tieuDuong;
        this.cholesterol = cholesterol;
        this.thoiGian = thoiGian;
        this.nhipTim = nhipTim;
        this.nhietDo = nhietDo;
        this.luongOxiTrongMau = luongOxiTrongMau;
        this.gluco = gluco;
        this.toaDoX = toaDoX;
        this.toaDoY = toaDoY;
        this.dbp = dbp;
        this.sbp = sbp;
        this.tinhTrang = tinhTrang;
    }

    public String getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(String idThietBi) {
        this.idThietBi = idThietBi;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isHutThuoc() {
        return hutThuoc;
    }

    public void setHutThuoc(boolean hutThuoc) {
        this.hutThuoc = hutThuoc;
    }

    public int getSoDieuThuoc1Ngay() {
        return soDieuThuoc1Ngay;
    }

    public void setSoDieuThuoc1Ngay(int soDieuThuoc1Ngay) {
        this.soDieuThuoc1Ngay = soDieuThuoc1Ngay;
    }

    public boolean isDungThuocHuyetAp() {
        return dungThuocHuyetAp;
    }

    public void setDungThuocHuyetAp(boolean dungThuocHuyetAp) {
        this.dungThuocHuyetAp = dungThuocHuyetAp;
    }

    public boolean isDaBiDotQuy() {
        return daBiDotQuy;
    }

    public void setDaBiDotQuy(boolean daBiDotQuy) {
        this.daBiDotQuy = daBiDotQuy;
    }

    public boolean isTangHuyetAp() {
        return tangHuyetAp;
    }

    public void setTangHuyetAp(boolean tangHuyetAp) {
        this.tangHuyetAp = tangHuyetAp;
    }

    public boolean isTieuDuong() {
        return tieuDuong;
    }

    public void setTieuDuong(boolean tieuDuong) {
        this.tieuDuong = tieuDuong;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
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

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public int getSbp() {
        return sbp;
    }

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
