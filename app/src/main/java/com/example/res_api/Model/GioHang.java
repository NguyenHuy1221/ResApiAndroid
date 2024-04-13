package com.example.res_api.Model;

public class GioHang {

    private String _id;
    private String gioHangID;
    private String userId;
    private Double tongTien;

    public GioHang() {
    }

    public GioHang(String gioHangID, String userId, Double tongTien) {
        this.gioHangID = gioHangID;
        this.userId = userId;
        this.tongTien = tongTien;
    }

    public String getGioHangID() {
        return gioHangID;
    }

    public void setGioHangID(String gioHangID) {
        this.gioHangID = gioHangID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }


    @Override
    public String toString() {
        return "GioHang{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", tongTien=" + tongTien +
                '}';
    }
}
