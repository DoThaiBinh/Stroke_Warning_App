package com.example.appcanhbaodotquy.Models;

public class RequestAddThietBi {
    private String sdt;
    private String idThietBi;
    private String ten;
    private int tuoi;
    private int giaoDuc;
    private double bmi;
    private boolean gioitinh;
    private boolean hutThuoc;
    private int soDieu1Ngay;
    private boolean dungThuocHuyetAp;
    private boolean daBiDotQuy;
    private boolean tangHuyetAp;
    private boolean tieuDuong;
    private int cholesterol ;

    public RequestAddThietBi() {
    }

    public RequestAddThietBi(String sdt, String idThietBi, String ten, int tuoi, int giaoDuc, double bmi, boolean gioitinh, boolean hutThuoc, int soDieu1Ngay, boolean dungThuocHuyetAp, boolean daBiDotQuy, boolean tangHuyetAp, boolean tieuDuong, int cholesterol) {
        this.sdt = sdt;
        this.idThietBi = idThietBi;
        this.ten = ten;
        this.tuoi = tuoi;
        this.giaoDuc = giaoDuc;
        this.bmi = bmi;
        this.gioitinh = gioitinh;
        this.hutThuoc = hutThuoc;
        this.soDieu1Ngay = soDieu1Ngay;
        this.dungThuocHuyetAp = dungThuocHuyetAp;
        this.daBiDotQuy = daBiDotQuy;
        this.tangHuyetAp = tangHuyetAp;
        this.tieuDuong = tieuDuong;
        this.cholesterol = cholesterol;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(String idThietBi) {
        this.idThietBi = idThietBi;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public int getGiaoDuc() {
        return giaoDuc;
    }

    public void setGiaoDuc(int giaoDuc) {
        this.giaoDuc = giaoDuc;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public boolean isHutThuoc() {
        return hutThuoc;
    }

    public void setHutThuoc(boolean hutThuoc) {
        this.hutThuoc = hutThuoc;
    }

    public int getSoDieu1Ngay() {
        return soDieu1Ngay;
    }

    public void setSoDieu1Ngay(int soDieu1Ngay) {
        this.soDieu1Ngay = soDieu1Ngay;
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
}
