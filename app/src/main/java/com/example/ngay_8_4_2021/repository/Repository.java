package com.example.ngay_8_4_2021.repository;

import com.example.ngay_8_4_2021.api.ApiClient;
import com.example.ngay_8_4_2021.api.ApiService;
import com.example.ngay_8_4_2021.model.Clothes;
import com.example.ngay_8_4_2021.model.Customer;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class Repository {
    private final ApiService apiService;

    public Repository() {
        this.apiService = ApiClient.getInstance().getApiService();
    }

    public Single<ResponseBody> dangNhapObservable(String encodedString, RequestBody requestBody) {
        return apiService.dangNhapCustomer(encodedString, requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBody> dangKyObservable(String encodedString, Customer customer) {
        return apiService.dangKyCustomer(encodedString, customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Clothes> getClothesObservable(int pageIndex, int pageSize, String sortBy, String sortType){
        return apiService.getClothes(pageIndex,pageSize,sortBy,sortType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
