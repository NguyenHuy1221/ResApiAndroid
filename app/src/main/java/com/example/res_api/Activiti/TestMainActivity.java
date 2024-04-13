package com.example.res_api.Activiti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.res_api.Adapter.ProductsAdapter;
import com.example.res_api.Model.ChiTietGioHang;
import com.example.res_api.Model.ChiTietSanPham;
import com.example.res_api.Model.GioHang;
import com.example.res_api.Model.HoaDon;
import com.example.res_api.Model.Products;
import com.example.res_api.Model.User;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestMainActivity extends AppCompatActivity {

    private TextView tv1,tv2,tv3,tv4;
    private Button btn;
//    private List<ChiTietSanPham> chiTietSanPhamList = new ArrayList<>();
    private List<GioHang> gioHangList = new ArrayList<>();
    private List<Products> productsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
            }
        });

    }

    public void callAPI() {
//        String id = "660e37146538f1ac30be6ee3";
//        ApiService.apiService.chitietsanphamID(id,"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<ChiTietSanPham>>() {
//            @Override
//            public void onResponse(Call<List<ChiTietSanPham>> call, Response<List<ChiTietSanPham>> response) {
//                Toast.makeText(TestMainActivity.this, "Call Api succes", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<ChiTietSanPham>> call, Throwable t) {
//                Toast.makeText(TestMainActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
//            }
//        });

       ApiService.apiService.showHdIdUser("660e40e96538f1ac30be6efa ","42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<HoaDon>>() {
           @Override
           public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
               Toast.makeText(TestMainActivity.this, "OKE", Toast.LENGTH_SHORT).show();

           }

           @Override
           public void onFailure(Call<List<HoaDon>> call, Throwable t) {
               Toast.makeText(TestMainActivity.this, "CALL API ERROR", Toast.LENGTH_SHORT).show();
           }
       });
    }
}
