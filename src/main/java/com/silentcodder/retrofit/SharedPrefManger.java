package com.silentcodder.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.silentcodder.retrofit.Model.User;

public class SharedPrefManger {

    private static String SHARED_PREF_NAME = "UserData";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManger(Context context) {
        this.context = context;
    }

    public void saveUser(User user){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,0);
        editor = sharedPreferences.edit();
        editor.putInt("id",user.getId());
        editor.putString("username",user.getUsername());
        editor.putString("email",user.getEmail());
        editor.putBoolean("logged",true);
        editor.apply();
    }

    public boolean isLoggedIn(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,0);
        return sharedPreferences.getBoolean("logged",false);
    }

    public User getUser(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,0);
        return new User(sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("email",null));
    }

    public void logout(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,0);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
