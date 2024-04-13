package com.example.res_api.Model;

public class HoaDon {

    private String _id;
    private String hoaDonID;
    private String khachHangID;
    private String trangThai;
    private String diaChi;
    private String ngayTao;


    public HoaDon() {
    }

    public HoaDon(String hoaDonID, String khachHangID, String trangThai, String diaChi) {
        this.hoaDonID = hoaDonID;
        this.khachHangID = khachHangID;
        this.trangThai = trangThai;
        this.diaChi = diaChi;
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

    public String getKhachHangID() {
        return khachHangID;
    }

    public void setKhachHangID(String khachHangID) {
        this.khachHangID = khachHangID;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
