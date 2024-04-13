package com.example.res_api.Activiti;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.res_api.Adapter.SanPhamAdapter;
import com.example.res_api.Model.Products;

import com.example.res_api.R;
import com.example.res_api.api.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLySanPhamActivity extends AppCompatActivity {

    private List<Products> productsList = new ArrayList<>();

    private FloatingActionButton floatingActionButton;
    private Context context = this;

    private SanPhamAdapter sanPhamAdapter;
    private RecyclerView recyclerView;
    private ImageView imgBack;
    private Spinner spnloaisp, spnchiso;
    private List<String> loaisp = new ArrayList<>();
    private List<String> chisosanpham = new ArrayList<>();
    private Button btn_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        anhXa();
        showProducts();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProducts();
            }
        });
    }


    private void addProducts() {
        Intent intent = new Intent(QuanLySanPhamActivity.this, ThemSanPhamActivity.class);
        startActivity(intent);
    }

    private void showProducts() {
        ApiService.apiService.productsListApi("42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {

                productsList = response.body();
               sanPhamAdapter = new SanPhamAdapter(context,productsList);
               recyclerView.setAdapter(sanPhamAdapter);
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa() {
        spnloaisp = findViewById(R.id.spn_loaisanpham);
        spnchiso = findViewById(R.id.spn_chiso);
        btn_loc = findViewById(R.id.btn_loc);

        imgBack = findViewById(R.id.toolbar);
        imgBack.setOnClickListener(v -> finish());

        floatingActionButton = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.rcy_qlsp);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}