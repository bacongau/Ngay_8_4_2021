package com.example.ngay_8_4_2021.api;

import com.example.ngay_8_4_2021.App;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String URL = "https://36a30a965086.ngrok.io/";

    private final ApiService apiService;

    private static ApiClient singleton;

    private ApiClient() {


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        OkHttpClient client = builder.addInterceptor(new ConnectivityInterceptor(App.getInstance()))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build();

        apiService = mRetrofit.create(ApiService.class);
    }

    public static ApiClient getInstance() {
        if (singleton == null) {
            synchronized (ApiClient.class) {
                if (singleton == null) {
                    singleton = new ApiClient();
                }
            }
        }


        return singleton;
    }

    public ApiService getApiService() {

        return apiService;
    }
}
