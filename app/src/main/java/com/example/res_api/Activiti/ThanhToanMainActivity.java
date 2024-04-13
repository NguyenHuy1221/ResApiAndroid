package com.example.res_api.Activiti;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.res_api.Model.ChiTietGioHang;
import com.example.res_api.Model.ChiTietHoaDon;
import com.example.res_api.Model.ChiTietSanPham;
import com.example.res_api.Model.GioHang;
import com.example.res_api.Model.HoaDon;
import com.example.res_api.Model.Products;
import com.example.res_api.Model.User;
import com.example.res_api.R;
import com.example.res_api.SharedPreferences.MySharedPreferences;
import com.example.res_api.api.ApiService;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanMainActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView txtTongTien,txtGmail,txtSdt;
    private EditText edtDiaChi;
    private Button btn_mua_hang;
    double tien;
    private ImageView img_dia_chi;
//    private ArrayList<GioHang> gioHangList = new ArrayList<>(Gio_Hang.mlistGioHang);
    private ArrayList<ChiTietGioHang> chiTietGioHangArrayList = new ArrayList<>(Gio_Hang.mListChiTiet);
    private ArrayList<GioHang> list = new ArrayList<>();
    private ArrayList<Products> listsanpham = new ArrayList<>();

    private  List<User> mListTaiKhoan;

    private GioHang gioHang;
    private Products sanPham;

    private Context context = this ;
    private int idKhachHang;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_main);

        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        user = mySharedPreferences.getValue("remember_id_tk");

        anhxa();
        sendDataCart();


    }

    private void anhxa() {

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        txtTongTien = findViewById(R.id.txtTongTien);
        txtGmail = findViewById(R.id.tvEmail);
        txtSdt = findViewById(R.id.tvSdt);
        img_dia_chi = findViewById(R.id.img_dia_chi);

        edtDiaChi = findViewById(R.id.edtdiaChi);
        btn_mua_hang = findViewById(R.id.btn_mua_hang);
        int trangthai = 0;
        btn_mua_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHoaDon();
                logChiTietGioHang();

            }
        });
    }

    private void sendDataCart() {
        Intent intent = getIntent();
        tien = intent.getDoubleExtra("tongtien",0);
        txtTongTien.setText(formatTien(tien) + " đ");
    }


    private String formatTien(double value) {
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value) + " đ";
    }

    public void addHoaDon() {
        Random random = new Random();
        int randomInt = random.nextInt();
        String idHodDon = Integer.toString(randomInt);
        String diaChi = edtDiaChi.getText().toString().trim();
        if (diaChi.equals("")){
            Toast.makeText(context, "Chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
        }

        HoaDon hoaDon = new HoaDon(idHodDon,user,"Chờ xử lý",diaChi);
        ApiService.apiService.creatHoaDon(hoaDon).enqueue(new Callback<HoaDon>() {
            @Override
            public void onResponse(Call<HoaDon> call, Response<HoaDon> response) {
                Toast.makeText(context, "Thêm giỏ hàng thành công ", Toast.LENGTH_SHORT).show();
                addCTHD(idHodDon);
            }

            @Override
            public void onFailure(Call<HoaDon> call, Throwable t) {
                Toast.makeText(context, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addCTHD(String idHD) {
        if (chiTietGioHangArrayList.isEmpty()) {
            Toast.makeText(context, "Danh sách chi tiết giỏ hàng trống", Toast.LENGTH_SHORT).show();
            return;
        }

        for (ChiTietGioHang chiTietGioHang : chiTietGioHangArrayList) {
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(idHD, chiTietGioHang.getChiTietSanPhamID(), chiTietGioHang.getSoLuong(), chiTietGioHang.getDonGia());
            ApiService.apiService.creatCTHD(chiTietHoaDon).enqueue(new Callback<ChiTietHoaDon>() {
                @Override
                public void onResponse(Call<ChiTietHoaDon> call, Response<ChiTietHoaDon> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Thêm chi tiết hóa đơn thành công", Toast.LENGTH_SHORT).show();
                        removeCTGH(chiTietGioHang.getGioHangID());
                    } else {
                        Toast.makeText(context, "Thêm chi tiết hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ChiTietHoaDon> call, Throwable t) {
                    Toast.makeText(context, "Call API error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void logChiTietGioHang() {
        if (chiTietGioHangArrayList.isEmpty()) {
            Toast.makeText(context, "Danh sách chi tiết giỏ hàng trống", Toast.LENGTH_SHORT).show();
            return;
        }

        for (ChiTietGioHang chiTietGioHang : chiTietGioHangArrayList) {
            Log.d("HUY", "ID GIO HANG" + chiTietGioHang.getGioHangID() + " ID sản phẩm: " + chiTietGioHang.getChiTietSanPhamID() + ", Số lượng: " + chiTietGioHang.getSoLuong() + ", Đơn giá: " + chiTietGioHang.getDonGia());
        }
    }

    public void removeCTGH(String idGH) {
        ApiService.apiService.deleteCTGHID(idGH).enqueue(new Callback<ChiTietGioHang>() {
            @Override
            public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                startActivity(new Intent(ThanhToanMainActivity.this, DatHangThanhCongActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                Toast.makeText(context, "CALL API ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

}