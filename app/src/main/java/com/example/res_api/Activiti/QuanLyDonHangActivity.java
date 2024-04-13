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


import com.example.res_api.Adapter.QuanLyDonHangAdapter;
import com.example.res_api.Model.HoaDon;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyDonHangActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuanLyDonHangAdapter quanLyDonHangAdapter;


    private List<HoaDon> mListhoadon = new ArrayList<>();
    private Context context = this;
    private String user;
    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);

        recyclerView = findViewById(R.id.rcvHoaDon);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }




}
