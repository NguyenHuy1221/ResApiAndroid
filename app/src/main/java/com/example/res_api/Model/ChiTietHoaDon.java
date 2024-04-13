package com.example.res_api.Model;

public class ChiTietHoaDon {

    private String _id;
    private String hoaDonID;
    private String chiTietSanPhamID;
    private int soLuong;
    private Double donGia;
    private Double tongTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String hoaDonID, String chiTietSanPhamID, int soLuong, Double donGia) {
        this.hoaDonID = hoaDonID;
        this.chiTietSanPhamID = chiTietSanPhamID;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHoaDonID() {
        return hoaDonID;
    }

    public void setHoaDonID(String hoaDonID) {
        this.hoaDonID = hoaDonID;
    }

    public String getChiTietSanPhamID() {
        return chiTietSanPhamID;
    }

    public void setChiTietSanPhamID(String chiTietSanPhamID) {
        this.chiTietSanPhamID = chiTietSanPhamID;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }
}
