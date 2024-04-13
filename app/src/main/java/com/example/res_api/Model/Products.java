package com.example.res_api.Model;

public class Products {

    private String _id;
    private String ten;
    private String hinh;
    private double donGiaNhap;
    private double donGiaBan;
    private int soLuongNhap;
    private int soLuongDaBan;
    private String category;
    private String moTa;
    private String tinhTrang;
    private String ngayTao;

    public Products( String ten, String hinh, double donGiaNhap, double donGiaBan, int soLuongNhap, int soLuongDaBan, String category, String moTa, String tinhTrang) {
        this.ten = ten;
        this.hinh = hinh;
        this.donGiaNhap = donGiaNhap;
        this.donGiaBan = donGiaBan;
        this.soLuongNhap = soLuongNhap;
        this.soLuongDaBan = soLuongDaBan;
        this.category = category;
        this.moTa = moTa;
        this.tinhTrang = tinhTrang;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    public double getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
