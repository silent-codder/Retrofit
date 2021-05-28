package com.silentcodder.retrofit.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.silentcodder.retrofit.LoginActivity;
import com.silentcodder.retrofit.MainActivity;
import com.silentcodder.retrofit.Model.LoginResponse;
import com.silentcodder.retrofit.Model.UpdateResponse;
import com.silentcodder.retrofit.R;
import com.silentcodder.retrofit.RetrofitClient;
import com.silentcodder.retrofit.SharedPrefManger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends Fragment {

    EditText mUserName,mEmail;
    EditText mCurrent,mNew;
    Button mBtnUpdate,mBtnUpdatePassword,mBtnLogout,mBtnDelete;
    SharedPrefManger sharedPrefManger;
    int id;
    String email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        mUserName = view.findViewById(R.id.username);
        mEmail = view.findViewById(R.id.email);
        mBtnUpdate = view.findViewById(R.id.btnUpdate);
        mCurrent = view.findViewById(R.id.currentPassword);
        mNew = view.findViewById(R.id.newPassword);
        mBtnLogout = view.findViewById(R.id.btnLogout);
        mBtnDelete = view.findViewById(R.id.btnDeleteAccount);
        mBtnUpdatePassword = view.findViewById(R.id.btnPasswordUpdate);
        sharedPrefManger = new SharedPrefManger(getActivity());
        id = sharedPrefManger.getUser().getId();
        email = sharedPrefManger.getUser().getEmail();

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserName.getText().toString();
                String email = mEmail.getText().toString();

                if (TextUtils.isEmpty(userName)){
                    mUserName.setError("Name");
                }else if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email");
                }else {
                    Call<LoginResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .update(id,userName,email);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()){
                                if (response.body().getError().equals("200")){
                                    sharedPrefManger.saveUser(response.body().getUser());
                                    Toast.makeText(getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(), "Fail to update", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManger.logout();
                startActivity(new Intent(getContext(), LoginActivity.class));
                Toast.makeText(getContext(), "Logout Successfully..", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Current = mCurrent.getText().toString().trim();
                String New = mNew.getText().toString().trim();

                if (TextUtils.isEmpty(Current)){
                    mUserName.setError("Current Password");
                }else if (TextUtils.isEmpty(New)){
                    mEmail.setError("New Password");
                }else {
                    Call<UpdateResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .updatePassword(Current,New,email);

                    call.enqueue(new Callback<UpdateResponse>() {
                        @Override
                        public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateResponse> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<LoginResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .delete(id);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                            sharedPrefManger.logout();
                        }else {
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}