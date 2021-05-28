package com.silentcodder.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.silentcodder.retrofit.Model.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText mUserName,mEmail,mPassword;
    Button mBtnRegister;
    TextView mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = findViewById(R.id.username);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnLogin = findViewById(R.id.btnLogin);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserName.getText().toString();
                String userEmail = mEmail.getText().toString();
                String userPassword = mPassword.getText().toString();

                if (TextUtils.isEmpty(userName)){
                    mUserName.setError("User Name");
                }else if (TextUtils.isEmpty(userEmail)){
                    mEmail.setError("Email");
                }else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                    mEmail.setError("Invalid Email");
                }else if (TextUtils.isEmpty(userPassword)){
                    mPassword.setError("Create Password");
                }else {
                    Call<RegisterResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .register(userName,userEmail,userPassword);
                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                            RegisterResponse registerResponse = response.body();
                            if (response.isSuccessful()){
                                Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            }else {
                                Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPrefManger sharedPrefManger;
        sharedPrefManger = new SharedPrefManger(getApplicationContext());
        if (sharedPrefManger.isLoggedIn()){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
    }
}