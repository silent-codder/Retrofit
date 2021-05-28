package com.silentcodder.retrofit.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silentcodder.retrofit.R;
import com.silentcodder.retrofit.SharedPrefManger;

public class HomeFragment extends Fragment {

    TextView mUsername;
    TextView mEmail;
    SharedPrefManger sharedPrefManger;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mUsername = view.findViewById(R.id.username);
        mEmail = view.findViewById(R.id.email);

        sharedPrefManger = new SharedPrefManger(getActivity());

        mUsername.setText("Hello !! " +sharedPrefManger.getUser().getUsername());
        mEmail.setText(sharedPrefManger.getUser().getEmail());

        return view;
    }
}