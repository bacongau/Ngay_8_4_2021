package com.example.ngay_8_4_2021.api;

import com.example.ngay_8_4_2021.model.Customer;
import com.example.ngay_8_4_2021.model.response.ResponseChiTietSanPham;
import com.example.ngay_8_4_2021.model.response.ResponseDanhSachClothes;

import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/auths/customer/login")
    Single<ResponseBody> dangNhapCustomer(@Header("Authorization") String encodedString, @Body RequestBody requestBody);

    @POST("api/auths/customers/register")
    Single<ResponseBody> dangKyCustomer(@Header("Authorization") String encodedString, @Body Customer customer);

    // https://36a30a965086.ngrok.io/api/products/clothes?pageIndex=0&pageSize=10&sortBy=createdDate&sortType=desc
    @GET("/api/products/clothes")
    Single<ResponseDanhSachClothes> getListClothes(@Query("pageIndex") int pageIndex,
                                                       @Query("pageSize") int pageSize,
                                                       @Query("sortBy") String sortBy,
                                                       @Query("sortType") String sortType);

    // https://36a30a965086.ngrok.io/api/products/clothes/55a70880-ced5-4bac-9ef4-0a1ea9677a59
    @GET("/api/products/clothes/{id}")
    Single<ResponseChiTietSanPham> getClothesById(@Path("id") String id);
}
