package com.example.res_api.Model;

public class ChiTietGioHang {

    private String _id;
    private String gioHangID;
    private String chiTietSanPhamID;
    private int soLuong;
    private Double donGia;
    private Double tongTien;

    public ChiTietGioHang() {
    }

    public ChiTietGioHang(String gioHangID, String chiTietSanPhamID, int soLuong, Double donGia) {
        this.gioHangID = gioHangID;
        this.chiTietSanPhamID = chiTietSanPhamID;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public ChiTietGioHang(String gioHangID, String chiTietSanPhamID, int soLuong, Double donGia, Double tongTien) {
        this.gioHangID = gioHangID;
        this.chiTietSanPhamID = chiTietSanPhamID;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tongTien = tongTien;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGioHangID() {
        return gioHangID;
    }

    public void setGioHangID(String gioHangID) {
        this.gioHangID = gioHangID;
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
