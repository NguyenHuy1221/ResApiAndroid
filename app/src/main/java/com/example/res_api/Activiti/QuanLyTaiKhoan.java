package com.example.res_api.Activiti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.res_api.Adapter.TaiKhoanAdapter;
import com.example.res_api.Model.User;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyTaiKhoan extends AppCompatActivity {

    RecyclerView recycle_qltk;
    TaiKhoanAdapter taiKhoanAdapter;
    private Context context = this;
    private List<User> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);

        anhXa();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recycle_qltk.setLayoutManager(gridLayoutManager);

        ApiService.apiService.converstUser("42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();
                taiKhoanAdapter = new TaiKhoanAdapter(context, userList);
                recycle_qltk.setAdapter(taiKhoanAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void anhXa(){
        recycle_qltk = findViewById(R.id.recycle_qltk);
    };
}