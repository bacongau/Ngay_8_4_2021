package com.example.ngay_8_4_2021.dangnhap;

import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import com.example.ngay_8_4_2021.MainActivity;
import com.example.ngay_8_4_2021.R;
import com.example.ngay_8_4_2021.repository.Repository;

import java.util.Base64;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DangNhapViewModel extends BaseObservable {
    private String username;
    private String password;
    private Boolean checkGhiNho;

    MainActivity mMainActivity;
    FragmentManager fragmentManager;

    CompositeDisposable compositeDisposable;
    MutableLiveData<Boolean> loading;
    private final Repository repository;

    SharedPreferences sharedPreferences;

    public DangNhapViewModel() {
        this.repository = new Repository();
    }

    public Boolean getCheckGhiNho() {
        return checkGhiNho;
    }

    public void setCheckGhiNho(Boolean checkGhiNho) {
        this.checkGhiNho = checkGhiNho;
    }

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

    public void onClickChuyenSangDangKy(){
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, mMainActivity.dangKyFragment)
                .addToBackStack(null)
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickDangNhap() {
        String tentk = username;
        String mk = password;
        String str = tentk + ":" + mk;
        if (!CheckInput(tentk, mk)) {
            return;
        }
        String str2 = Base64.getEncoder().encodeToString(str.getBytes());
        String encodedString = "Basic " + str2;

        String strRequestBody = "body";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);

        // rxjava
        ThucHienDangNhap(encodedString, requestBody, tentk, mk);
    }

    private void ThucHienDangNhap(String encodedString, RequestBody requestBody, String tentk, String mk) {
        compositeDisposable.add(repository.dangNhapObservable(encodedString, requestBody)
                .doOnSubscribe(disposable -> {
                    loading.setValue(true);
                })
                .doFinally(() -> {
                    loading.setValue(false);
                })
                .subscribe(
                        responseBody -> {
                            Toast.makeText(mMainActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                    .replace(R.id.fragment_container, mMainActivity.homeFragment)
                                    .addToBackStack(null)
                                    .commit();
                            luuDangNhapVaoSharedPreferences(tentk, mk);
                        },
                        throwable -> {
                            // xử lý lỗi
                            Log.v("myLog", "err " + throwable.getLocalizedMessage());
                            Toast.makeText(mMainActivity, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void luuDangNhapVaoSharedPreferences(String tentk, String mk) {
        if (checkGhiNho == true) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Login_tentaikhoan", tentk);
            editor.putString("Login_matkhau", mk);
            editor.putBoolean("Login_nhodangnhap", true);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("Login_tentaikhoan");
            editor.remove("Login_matkhau");
            editor.remove("Login_nhodangnhap");
            editor.apply();
        }
    }

    private boolean CheckInput(String tentk, String mk) {
        if (tentk.isEmpty() || mk.isEmpty() || mk.length() < 6) {
            Toast.makeText(mMainActivity, "Bạn chưa nhập thông tin" + "\nMật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
