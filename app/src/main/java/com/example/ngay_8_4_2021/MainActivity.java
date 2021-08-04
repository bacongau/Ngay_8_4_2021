package com.example.ngay_8_4_2021;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ngay_8_4_2021.custom.LoadingDialog;
import com.example.ngay_8_4_2021.dangki.DangKyFragment;
import com.example.ngay_8_4_2021.dangnhap.DangNhapFragment;
import com.example.ngay_8_4_2021.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    public DangNhapFragment dangNhapFragment;
    public HomeFragment homeFragment;
    public DangKyFragment dangKyFragment;

    public LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingDialog = new LoadingDialog(this);

        dangNhapFragment = new DangNhapFragment();
        homeFragment = new HomeFragment();
        dangKyFragment = new DangKyFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .add(R.id.fragment_container,dangNhapFragment)
                .commit();
    }


}