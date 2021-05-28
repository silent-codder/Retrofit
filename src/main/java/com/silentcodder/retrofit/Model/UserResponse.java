package com.silentcodder.retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
   @SerializedName("users")
    List<User> users;
    String error;

    public UserResponse(List<User> users, String error) {
        this.users = users;
        this.error = error;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
