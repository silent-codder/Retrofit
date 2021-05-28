package com.silentcodder.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.silentcodder.retrofit.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail,mPassword;
    TextView mBtnRegister;
    Button mBtnLogin;

    SharedPrefManger sharedPrefManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnLogin = findViewById(R.id.btnLogin);

        sharedPrefManger = new SharedPrefManger(getApplicationContext());

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email");
                }else if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password");
                }else {
                    Call<LoginResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .login(email,password);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            LoginResponse loginResponse = response.body();
                            if (response.isSuccessful()){
                                if (loginResponse.getError().equals("200")){
                                    sharedPrefManger.saveUser(loginResponse.getUser());
                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {

                                }
                            }else {
                                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sharedPrefManger.isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }
    }
}