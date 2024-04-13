package com.example.res_api.Activiti;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.res_api.Adapter.GioHangAdapter;
import com.example.res_api.Model.ChiTietGioHang;
import com.example.res_api.Model.GioHang;
import com.example.res_api.Model.Products;
import com.example.res_api.R;
import com.example.res_api.SharedPreferences.MySharedPreferences;
import com.example.res_api.api.ApiService;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gio_Hang extends AppCompatActivity {

    private GioHangAdapter gioHangAdapter;
    private RecyclerView recyclerView;
    public static List<GioHang> mlistGioHang = new ArrayList<>();
    public static List<ChiTietGioHang> mListChiTiet = new ArrayList<>();
    private Context context = this;
    private ImageView btnBack,img_khuyen_mai;
    double tongTienSanPham = 0.0;
    private TextView txtTien;
    private TextView tvTienSanPham;
    private TextView tvGiamGia;
    private TextView tvTongTien;
    private TextView edtkhuyenmai;
    private Button btnMua;
    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        user = mySharedPreferences.getValue("remember_id_tk");
        Log.d("HUY","USSER" + user);


        anhxa();
        senDataCart();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.rcy_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


    }

    private void anhxa() {
        btnBack = findViewById(R.id.btnBack);
        txtTien = findViewById(R.id.txtTongTien);
        btnMua = findViewById(R.id.button);
        img_khuyen_mai = findViewById(R.id.img_khuyen_mai);
        tvTienSanPham = findViewById(R.id.tv_tong_tien_hang);
        tvGiamGia = findViewById(R.id.tv_giam_gia);
        tvTongTien = findViewById(R.id.tv_tong_tien);
        edtkhuyenmai = findViewById(R.id.editTextText2);

        btnMua.setOnClickListener(v -> {
            Intent intent = new Intent(Gio_Hang.this, ThanhToanMainActivity.class);
            intent.putExtra("tongtien",tongTienSanPham);
            startActivity(intent);
        });
    }

    public void senDataCart(){

        ApiService.apiService.showGioHangUser(user,"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
                    List<GioHang> gioHangList = response.body();
                    if (gioHangList != null && !gioHangList.isEmpty()) {
                        String idGioHang = gioHangList.get(0).getGioHangID();
                        Log.d("HUY",idGioHang);
                        senDataChiTietCart(idGioHang);

                    }
                } else {
                    Toast.makeText(context, "Lỗi khi lấy giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                Toast.makeText(context, "CAll Api error", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void senDataChiTietCart(String idGioHang) {
        ApiService.apiService.showCtghIDCart(idGioHang,"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                if (response.isSuccessful()) {
                    List<ChiTietGioHang> chiTietGioHangList = response.body();
                    tongTienSanPham = 0.0;

                    if (chiTietGioHangList != null && !chiTietGioHangList.isEmpty()) {
                        for (ChiTietGioHang chiTietGioHang : chiTietGioHangList) {
                            tongTienSanPham += chiTietGioHang.getTongTien();
                            mListChiTiet.add(chiTietGioHang);
                            Log.d("HUY", "TT: " + mListChiTiet.size());

                        }
                        tvTongTien.setText(formatTien(tongTienSanPham));
                        txtTien.setText(formatTien(tongTienSanPham));
                        tvTienSanPham.setText(formatTien(tongTienSanPham));
                        gioHangAdapter = new GioHangAdapter(context, chiTietGioHangList, new GioHangAdapter.IclickListener() {
                            @Override
                            public void onItemChanged(ChiTietGioHang chiTietGioHang) {
                                updateTotal();
                            }

                            @Override
                            public void onclickDeleteCart(ChiTietGioHang chiTietGioHang) {
                                deleteCart(chiTietGioHang.get_id());
                            }
                        });
                        recyclerView.setAdapter(gioHangAdapter);
                    }
                } else {
                    Toast.makeText(context, "Lỗi khi lấy chi tiết giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                Toast.makeText(context, "CAll Api error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void deleteCart(String productsId) {
        new AlertDialog.Builder(context)
                .setTitle("Xóa sản phẩm ")
                .setMessage("Bạn có chắc muốn xóa sản phẩm này")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ApiService.apiService.deleteCTGH(productsId).enqueue(new Callback<ChiTietGioHang>() {
                            @Override
                            public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                senDataCart();
                            }

                            @Override
                            public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Không", null).show();
    }

    private String formatTien(double value) {
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value) + " đ";
    }

    private void updateTotal() {
        tongTienSanPham = 0.0;
        for (ChiTietGioHang chiTietGioHang : mListChiTiet) {
            tongTienSanPham += chiTietGioHang.getSoLuong() * chiTietGioHang.getDonGia();
        }
        tvTongTien.setText(formatTien(tongTienSanPham));
        txtTien.setText(formatTien(tongTienSanPham));
        tvTienSanPham.setText(formatTien(tongTienSanPham));
    }


}