package com.example.ngay_8_4_2021.chitietsanpham;

import android.text.Html;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ngay_8_4_2021.model.response.ResponseChiTietSanPham;
import com.example.ngay_8_4_2021.repository.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class ChiTietSanPhamViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final Repository repository;

    private MutableLiveData<ResponseChiTietSanPham> responseChiTietSanPhamMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();

    private ObservableField<String> logoUrl = new ObservableField<>();
    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> price = new ObservableField<>();
    private ObservableField<String> description = new ObservableField<>();


    // getter and setter
    public ObservableField<String> getLogoUrl() {
        return logoUrl;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getPrice() {
        return price;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public MutableLiveData<ResponseChiTietSanPham> getResponseChiTietSanPhamMutableLiveData() {
        return responseChiTietSanPhamMutableLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }

    // constructor
    public ChiTietSanPhamViewModel() {
        this.repository = new Repository();
    }


    // cac ham xu ly
    public void getClothesById(String id) {
        compositeDisposable.add(repository.getClothesById(id)
                .doOnSubscribe(disposable -> {
                    loading.setValue(true);
                })
                .doFinally(() -> {
                    loading.setValue(false);
                })
                .subscribe(
                        responseChiTietSanPham -> {
                            responseChiTietSanPhamMutableLiveData.setValue(responseChiTietSanPham);
                            ResponseChiTietSanPham.ChiTietSanPham chiTietSanPham = responseChiTietSanPham.getData();

                            name.set(chiTietSanPham.getName());
                            price.set(chiTietSanPham.getPrice());
                            if (chiTietSanPham.getDescription() != null){
                                description.set(String.valueOf(Html.fromHtml(chiTietSanPham.getDescription())));
                            }else {
                                description.set(chiTietSanPham.getDescription());
                            }
                        },
                        throwable -> {
                            throwableMutableLiveData.setValue(throwable);
                        }
                ));
    }
}
