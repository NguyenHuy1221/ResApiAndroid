package com.example.res_api.Activiti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.res_api.Model.User;
import com.example.res_api.R;

import com.example.res_api.api.ApiService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotpwActivity extends AppCompatActivity {

    private TextInputLayout edtForgot;
    private Button btnForgot;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpw);
        edtForgot = findViewById(R.id.edtForgot);
        btnForgot = findViewById(R.id.btnForgot);

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtForgot.getEditText().getText().toString().trim();
                ApiService.apiService.forgotpass(email).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(ForgotpwActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(ForgotpwActivity.this, "CALL API ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}