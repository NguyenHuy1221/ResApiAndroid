package com.example.res_api.Activiti;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.res_api.Adapter.SanPhamMainAdapter;
import com.example.res_api.Model.Products;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSearch;
    private EditText edtSearch;
    private SanPhamMainAdapter searchAdapter;
    private List<Products> allSanPham;
    private ImageView imgBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        anhXa();
        setupRecyclerView();
        setupSearchEditText();
    }

    private void anhXa() {
        edtSearch = findViewById(R.id.edtSearch);
        recyclerViewSearch = findViewById(R.id.ryc_search);
        imgBack = findViewById(R.id.imageView5);
        imgBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        allSanPham = new ArrayList<>();
        searchAdapter = new SanPhamMainAdapter(this, allSanPham);
        recyclerViewSearch.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewSearch.setAdapter(searchAdapter);
    }

    private void setupSearchEditText() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();
                if (!keyword.isEmpty()) {
                    searchProducts(keyword);
                } else {
                    allSanPham.clear();
                    searchAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void searchProducts(String keyword) {
        ApiService.apiService.findProducts(keyword).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> foundProducts = response.body();
                    if (foundProducts != null && !foundProducts.isEmpty()) {
                        allSanPham.clear();
                        allSanPham.addAll(foundProducts);
                        searchAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(SearchMainActivity.this, "Lỗi khi gọi API tìm kiếm sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
