package com.example.res_api.Activiti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.res_api.Model.User;
import com.example.res_api.R;
import com.example.res_api.SharedPreferences.MySharedPreferences;
import com.example.res_api.api.ApiService;

import com.google.android.material.textfield.TextInputLayout;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private List<User> listUser = new ArrayList<>();
    Button btnlogin;
    TextInputLayout edtemaillg, edtmklg;
    TextView txtdk, txtquenmk;
    public CheckBox checkBox;

    final MySharedPreferences mySharedPreferences = new MySharedPreferences(LoginActivity.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        getListUser();
        listUser = new ArrayList<>();

        txtdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtemaillg.getEditText().getText().toString().trim();
                String pass = edtmklg.getEditText().getText().toString().trim();
                if (email.equals("")) {
                    edtemaillg.setError("Không để trống");
                    return;
                } else {
                    edtemaillg.setError(null);
                }
                if (pass.equals("")) {
                    edtmklg.setError("Không để trống");
                    return;
                } else {
                    edtmklg.setError(null);
                }
                if (edtemaillg.getEditText().getText().toString().trim().equals("admin") && edtmklg.getEditText().getText().toString().trim().equals("123")) {
                    Intent intent = new Intent(LoginActivity.this, TrangChuActiviti.class);
                    startActivity(intent);
                } else {

                    signUp();
                }

            }
        });

        txtquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotpwActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getListUser() {
        ApiService.apiService.converstUser("42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listUser = response.body();
                if(listUser!= null){
                    Log.d("HUY", listUser.size() + "");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void signUp() {
        String email = edtemaillg.getEditText().getText().toString().trim();
        String pass = edtmklg.getEditText().getText().toString().trim();

        if(listUser == null || listUser.isEmpty()) {
            return;
        }

        boolean loginSuccess = false;

        for(User user : listUser){
            if(email.equals(user.getGmail())){
                String _idUser = user.get_id();
                Intent intent = new Intent(LoginActivity.this, TrangChuActiviti.class);
                intent.putExtra("_idUser", _idUser);

                loginSuccess = true;
                if (checkBox.isChecked()) {
                    mySharedPreferences.putValue("remember_id_tk", user.get_id());
                    mySharedPreferences.putValue("remember_username", user.getGmail());
                    mySharedPreferences.putValue("remember_username_ten", user.getTen());
                    mySharedPreferences.putValue("remember_password", user.getMatKhau());
                    mySharedPreferences.putBooleanValue("remember_checkbox", checkBox.isChecked());
                }
                else {
                    mySharedPreferences.putValue("remember_id_tk", user.get_id());
                    mySharedPreferences.putValue("remember_username", user.getGmail());
                    mySharedPreferences.putValue("remember_username_ten", user.getTen());
                    mySharedPreferences.putValue("remember_password", user.getMatKhau());
                }

                if (user.getRole().equalsIgnoreCase("admin")) {
                    mySharedPreferences.putBooleanValue("permission_admin", true);
                }

                startActivity(intent);
                finish();

            }
        }

        if (!loginSuccess) {
            // Hiển thị lỗi nếu không đăng nhập thành công
            edtemaillg.setError("SAI TÀI KHOẢN HOẶC MẬT KHẨU !");
            edtmklg.setError("SAI TÀI KHOẢN HOẶC MẬT KHẨU !");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    edtemaillg.setError(null);
                    edtmklg.setError(null);
                }
            }, 4000);
        }

    }


    private void anhxa() {
        edtemaillg = findViewById(R.id.edtemaillg);
        edtmklg = findViewById(R.id.edtmklg);
        btnlogin = findViewById(R.id.btnlogin);
        txtdk = findViewById(R.id.txtdk);
        txtquenmk = findViewById(R.id.txtquenmk);
        checkBox = findViewById(R.id.cb_login);

    }
}
