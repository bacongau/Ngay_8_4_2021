package com.example.ngay_8_4_2021.ui.dangky;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.example.ngay_8_4_2021.model.Customer;
import com.example.ngay_8_4_2021.repository.Repository;

import java.util.Base64;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;

public class DangKyViewModel extends BaseObservable {
    // khoi tao
    private String username;
    private String password;
    private String fullname;
    private String address;
    private String phone;

    private final MutableLiveData<Integer> chuyenSangDangNhap = new MutableLiveData<>();
    private final MutableLiveData<Integer> checkInput = new MutableLiveData<>();
    private final MutableLiveData<ResponseBody> responseBodyMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final Repository repository;

    public DangKyViewModel() {
        this.repository = new Repository();
    }

    // getter and setter


    public MutableLiveData<ResponseBody> getResponseBodyMutableLiveData() {
        return responseBodyMutableLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }

    public MutableLiveData<Integer> getCheckInput() {
        return checkInput;
    }

    public MutableLiveData<Integer> getChuyenSangDangNhap() {
        return chuyenSangDangNhap;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickDangKy(){
        String tentk = username;
        String mk = password;
        String hoten = fullname;
        String diachi = address;
        String sdt = phone;

        if (!checkInput(tentk, mk, hoten, diachi, sdt)) {
            return;
        }

        String str = tentk + ":" + mk;
        String str2 = Base64.getEncoder().encodeToString(str.getBytes());
        String encodedString = "Basic " + str2;

        Customer customer = new Customer(hoten, diachi, sdt);

        compositeDisposable.add(repository.dangKyObservable(encodedString, customer)
                .doOnSubscribe(disposable -> {
                    loading.setValue(true);
                })
                .doFinally(() -> {
                    loading.setValue(false);
                })
                .subscribe(
                        responseBody -> {
                            responseBodyMutableLiveData.setValue(responseBody);
                        },
                        throwable -> {
                            throwableMutableLiveData.setValue(throwable);
                        }
                ));
    }

    private boolean checkInput(String tentk, String mk, String hoten, String diachi, String sdt) {
        if (tentk.isEmpty() || mk.isEmpty() || hoten.isEmpty() || diachi.isEmpty() || sdt.isEmpty()) {
            checkInput.setValue(12);
            return false;
        }
        return true;
    }

    public void onClickChuyenSangDangNhap(){
        chuyenSangDangNhap.setValue(10);
    }
}
