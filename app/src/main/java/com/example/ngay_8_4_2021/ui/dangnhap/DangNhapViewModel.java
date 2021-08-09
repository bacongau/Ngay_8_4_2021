package com.example.ngay_8_4_2021.ui.dangnhap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.ngay_8_4_2021.repository.Repository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class DangNhapViewModel extends ViewModel {

    // khai báo
    private String username;
    private String password;
    private Boolean checkGhiNho;

    private final MutableLiveData<ResponseBody> responseBodyMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> chuyenSangDangKy = new MutableLiveData<>();
    private final MutableLiveData<Integer> checkInput = new MutableLiveData<>();

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final Repository repository;

    SharedPreferences sharedPreferences;

    private static int checkDangNhap = 0;

    // constructor
    @RequiresApi(api = Build.VERSION_CODES.O)
    public DangNhapViewModel(Context context) {
        this.repository = new Repository();
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    "DangNhap_Encrypt_sharedPreferences",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lấy thông tin đăng nhập từ SharedPreferences và thực hiện đăng nhập
        layDuLieuDangNhapTuSharedPreferences();
    }

    // getter and setter
    public MutableLiveData<Integer> getCheckInput() {
        return checkInput;
    }

    public MutableLiveData<Integer> getChuyenSangDangKy() {
        return chuyenSangDangKy;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<ResponseBody> getResponseBodyMutableLiveData() {
        return responseBodyMutableLiveData;
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

    // Xử lý logic
    public void onClickChuyenSangDangKy() {
        chuyenSangDangKy.setValue(10);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickDangNhap() {
        Log.d("dsfsdf", "sdfsdfsdf");
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
                            responseBodyMutableLiveData.setValue(responseBody);
                            luuDangNhapVaoSharedPreferences(tentk, mk);
                        },
                        throwable -> {
                            // xử lý lỗi
                            throwableMutableLiveData.setValue(throwable);
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
            checkInput.setValue(13);
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void layDuLieuDangNhapTuSharedPreferences() {

        // Lấy thông tin đăng nhập và gán lên các edittext.
        setUsername(sharedPreferences.getString("Login_tentaikhoan", ""));
        setPassword(sharedPreferences.getString("Login_matkhau", ""));
        setCheckGhiNho(sharedPreferences.getBoolean("Login_nhodangnhap", false));

        String tentaikhoan = getUsername();
        String matkhau = getPassword();
        String str = tentaikhoan + ":" + matkhau;
        String str2 = Base64.getEncoder().encodeToString(str.getBytes());
        String encodedString = "Basic " + str2;
        // request body
        String strRequestBody = "body";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);

        if (!tentaikhoan.isEmpty() && !matkhau.isEmpty() && checkDangNhap == 0) {
            checkDangNhap = 1;
            ThucHienDangNhap(encodedString, requestBody, tentaikhoan, matkhau);
        }
    }
}
