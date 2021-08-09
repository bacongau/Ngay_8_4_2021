package com.example.ngay_8_4_2021;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ngay_8_4_2021.ui.chitietsanpham.ChiTietSanPhamFragment;
import com.example.ngay_8_4_2021.custom.LoadingDialog;
import com.example.ngay_8_4_2021.ui.dangky.DangKyFragment;
import com.example.ngay_8_4_2021.ui.dangnhap.DangNhapFragment;
import com.example.ngay_8_4_2021.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.ISendIdClothesListener {
    FragmentManager fragmentManager;
    public DangNhapFragment dangNhapFragment;
    public HomeFragment homeFragment;
    public DangKyFragment dangKyFragment;
    public ChiTietSanPhamFragment chiTietSanPhamFragment;

    public LoadingDialog loadingDialog;

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingDialog = new LoadingDialog(this);

        dangNhapFragment = new DangNhapFragment();
        homeFragment = new HomeFragment();
        dangKyFragment = new DangKyFragment();
        chiTietSanPhamFragment = new ChiTietSanPhamFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .add(R.id.fragment_container,dangNhapFragment)
                .commit();
    }


    @Override
    public void sendId(String idClothes) {
        chiTietSanPhamFragment.getIdClothesFromHomeFragment(idClothes);
    }
}