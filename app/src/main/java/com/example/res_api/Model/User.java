package com.example.res_api.Model;

import java.util.Date;

public class User {

    private String _id;
    private String ten;
    private String diaChi;
    private int soDienThoai;
    private String gmail;
    private String matKhau;
    private String tinhTrang;
    private String role;
    private Date ngayTao;

    public User() {
    }


    public User(String ten, String diaChi, int soDienThoai, String gmail, String matKhau, String tinhTrang, String role) {
        this.ten = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.gmail = gmail;
        this.matKhau = matKhau;
        this.tinhTrang = tinhTrang;
        this.role = role;
        this.ngayTao = new Date();
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(int soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", ten='" + ten + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", soDienThoai=" + soDienThoai +
                ", gmail='" + gmail + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", tinhTrang='" + tinhTrang + '\'' +
                ", role='" + role + '\'' +
                ", ngayTao=" + ngayTao +
                '}';
    }
}
