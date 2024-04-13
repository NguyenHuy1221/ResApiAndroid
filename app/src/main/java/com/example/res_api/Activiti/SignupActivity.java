package com.example.res_api.Activiti;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.res_api.Model.User;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;
import com.google.android.material.textfield.TextInputLayout;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    TextInputLayout hoten,email,matkhau,nhaplaimk;
    Button button;
    TextView txtShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        button = findViewById(R.id.btnsignup);
        txtShow = findViewById(R.id.showUser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKy();
            }
        });
    }

    private void DangKy() {
        hoten = findViewById(R.id.hotendangky);
        email = findViewById(R.id.emaildangky);
        matkhau = findViewById(R.id.matkhaudangky);
        nhaplaimk = findViewById(R.id.nhaplaimatkhaudangky);

        String ten = hoten.getEditText().getText().toString().trim();
        String gmail = email.getEditText().getText().toString().trim();
        String matKhau = matkhau.getEditText().getText().toString().trim();
        String nhapLaiMatKhau = nhaplaimk.getEditText().getText().toString().trim();

        if (ten.isEmpty() || gmail.isEmpty() || matKhau.isEmpty() || nhapLaiMatKhau.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ten.length() < 6) {
            Toast.makeText(this, "Tên phải chứa ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(gmail)) {
            Toast.makeText(this, "Gmail không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (matKhau.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải chứa ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!matKhau.equals(nhapLaiMatKhau)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(ten,"Trống",1231231231,gmail,matKhau,"hoạt động","user");

        ApiService.apiService.creatUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    User userResult = response.body();
                    if (userResult != null) {
                        Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(SignupActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

}

