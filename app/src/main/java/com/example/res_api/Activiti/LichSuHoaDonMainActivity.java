package com.example.res_api.Activiti;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.res_api.Adapter.ChiTietDonHangAdapter;
import com.example.res_api.Adapter.HoaDonAdapter;
import com.example.res_api.Model.HoaDon;
import com.example.res_api.R;
import com.example.res_api.SharedPreferences.MySharedPreferences;
import com.example.res_api.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuHoaDonMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HoaDonAdapter hoaDonAdapter;
    private ChiTietDonHangAdapter chiTietDonHangAdapter;

    private List<HoaDon> mListhoadon = new ArrayList<>();
    private Context context = this;
    private String user;
    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_hoa_don_main);

        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        user = mySharedPreferences.getValue("remember_id_tk");


        recyclerView = findViewById(R.id.rcvHoaDon);
        showHD(user);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());



    }

    public void showHD(String user){
        ApiService.apiService.showHdIdUser(user,"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                if (response.isSuccessful()) {
                    List<HoaDon> hoaDonList = response.body();
                    if (hoaDonList != null && !hoaDonList.isEmpty()) {
                        Log.d("HUY","size : "+hoaDonList.size());
                        mListhoadon.addAll(hoaDonList);
                        setAdapterHoaDon();
                    } else {
                        Toast.makeText(context, "Không có dữ liệu hóa đơn", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Lỗi khi lấy dữ liệu hóa đơn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {
                Toast.makeText(context, "API ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setAdapterHoaDon() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        hoaDonAdapter = new HoaDonAdapter(context,mListhoadon);
        recyclerView.setAdapter(hoaDonAdapter);
    }



}