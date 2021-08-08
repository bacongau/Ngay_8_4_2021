package com.example.ngay_8_4_2021.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ngay_8_4_2021.adapter.ClothesAdapter;
import com.example.ngay_8_4_2021.model.Clothes;
import com.example.ngay_8_4_2021.model.response.ResponseDanhSachClothes;
import com.example.ngay_8_4_2021.repository.Repository;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class HomeFragmentViewModel extends ViewModel {
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<ResponseDanhSachClothes> responseDanhSachClothesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Clothes>> arrayListClothesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ClothesAdapter> clothesAdapterMutableLiveData = new MutableLiveData<>();

    private final Repository repository;

    // constructor
    public HomeFragmentViewModel() {
        this.repository = new Repository();
    }


    // getter and setter
    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<ClothesAdapter> getClothesAdapterMutableLiveData() {
        return clothesAdapterMutableLiveData;
    }

    public void setClothesAdapterMutableLiveData(MutableLiveData<ClothesAdapter> clothesAdapterMutableLiveData) {
        this.clothesAdapterMutableLiveData = clothesAdapterMutableLiveData;
    }

    public MutableLiveData<ArrayList<Clothes>> getArrayListClothesMutableLiveData() {
        return arrayListClothesMutableLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }

    public MutableLiveData<ResponseDanhSachClothes> getResponseDanhSachClothesMutableLiveData() {
        return responseDanhSachClothesMutableLiveData;
    }

    public void getDanhSachClothes() {
        compositeDisposable.add(repository.getListClothesObservable(0, 10, "createdDate", "desc")
                .doOnSubscribe(disposable -> {
                    //showLoading
                    loading.setValue(true);
                })
                .doFinally(() -> {
                    //hideLoading
                    loading.setValue(false);
                })
                .subscribe(
                        responseDanhSachClothes -> {
                            responseDanhSachClothesMutableLiveData.setValue(responseDanhSachClothes);
                            arrayListClothesMutableLiveData.setValue(responseDanhSachClothes.getData().getResults());
                        },
                        throwable -> {
                            throwableMutableLiveData.setValue(throwable);
                        }
                ));
    }
}
