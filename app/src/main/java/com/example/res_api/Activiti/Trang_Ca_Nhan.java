package com.example.res_api.Activiti;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.res_api.R;
import com.example.res_api.SharedPreferences.MySharedPreferences;


public class Trang_Ca_Nhan extends AppCompatActivity {
    TextView sanpham,taikhoan,hoadon,doanhthu,khuyenmai,lichsumua,thongtinchitiet,doimatkhau,dangxuat,cn_quan_ly,cn_mac_dinh;
    LinearLayout trangchu,yeuthich,giohang;
    private ImageView imgChinh;
    private TextView txtName,txtDiaChi;
    ImageView backBtn;
    private String user, email;
    int idTaiKhoan;
    private Context context = this;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);
        sanpham = findViewById(R.id.GO_qlsp);
        taikhoan = findViewById(R.id.GO_qltk);
        hoadon = findViewById(R.id.GO_qlhd);
        doanhthu = findViewById(R.id.GO_qldt);
        khuyenmai = findViewById(R.id.GO_qlkm);
        backBtn = findViewById(R.id.backBtn);
        lichsumua = findViewById(R.id.GO_lichsumua);
        dangxuat = findViewById(R.id.GO_dangxuat);
        thongtinchitiet = findViewById(R.id.GO_chitiettaikhoan);
        imgChinh = findViewById(R.id.img_chinh);
        txtName = findViewById(R.id.txt_name);
        txtDiaChi = findViewById(R.id.txt_dia_chi);
        cn_quan_ly = findViewById(R.id.cn_quan_ly);
        cn_mac_dinh = findViewById(R.id.cn_mac_dinh);


        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        user = mySharedPreferences.getValue("remember_username_ten");
        email = mySharedPreferences.getValue("remember_username");



        boolean isAdmin = mySharedPreferences.getBooleanValue("permission_admin");
        Log.d("HUY","your admin " + isAdmin);

        txtName.setText(user);
        txtDiaChi.setText(email);



        HienThiThongTin();

        backBtn.setOnClickListener(v -> finish());


        sanpham.setOnClickListener(v -> {
            Intent intent = new Intent(Trang_Ca_Nhan.this, QuanLySanPhamActivity.class);
            startActivity(intent);
        });

//        taikhoan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Trang_Ca_Nhan.this, QuanLyTaiKhoanActivity.class);
//                startActivity(intent);
//            }
//        });

        hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Ca_Nhan.this, QuanLyDonHangActivity.class);
                startActivity(intent);
            }
        });

        lichsumua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Ca_Nhan.this, LichSuHoaDonMainActivity.class);
                startActivity(intent);
            }
        });

//        doanhthu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Trang_Ca_Nhan.this, Doanh_Thu.class);
//                startActivity(intent);
//            }
//        });

//        khuyenmai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Trang_Ca_Nhan.this, quan_ly_khuyen_mai.class);
//                startActivity(intent);
//            }
//        });
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Ca_Nhan.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        thongtinchitiet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Trang_Ca_Nhan.this, ChiTietTaiKhoanActivity.class);
//                startActivity(intent);
//            }
//        });


    }

    private void HienThiThongTin() {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        boolean isAdmin = mySharedPreferences.getBooleanValue("permission_admin");

        if (isAdmin) {
            sanpham.setVisibility(View.VISIBLE);
            taikhoan.setVisibility(View.VISIBLE);
            hoadon.setVisibility(View.VISIBLE);
            doanhthu.setVisibility(View.VISIBLE);
            lichsumua.setVisibility(View.VISIBLE);
            khuyenmai.setVisibility(View.VISIBLE);
            thongtinchitiet.setVisibility(View.VISIBLE);
            cn_quan_ly.setVisibility(View.VISIBLE);
        } else {
            sanpham.setVisibility(View.GONE);
            hoadon.setVisibility(View.GONE);
            doanhthu.setVisibility(View.GONE);
            khuyenmai.setVisibility(View.GONE);
            taikhoan.setVisibility(View.GONE);
            cn_quan_ly.setVisibility(View.GONE);
            cn_mac_dinh.setVisibility(View.GONE);
        }
    }

}