package com.example.ngay_8_4_2021.api;

import com.example.ngay_8_4_2021.model.Customer;

import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/auths/customer/login")
    Single<ResponseBody> dangNhapCustomer(@Header("Authorization") String encodedString, @Body RequestBody requestBody);

    @POST("api/auths/customers/register")
    Single<ResponseBody> dangKyCustomer(@Header("Authorization") String encodedString, @Body Customer customer);
}
