package com.example.ngay_8_4_2021.dangnhap;

import androidx.databinding.BaseObservable;

public class DangNhapViewModel extends BaseObservable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void onClickDangNhap(){

    }
}
