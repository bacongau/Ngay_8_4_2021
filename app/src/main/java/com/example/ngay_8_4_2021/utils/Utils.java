package com.example.ngay_8_4_2021.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
    public static int step = 0;
    public static boolean checkNetwork(Context context) {
        boolean available = false;
        ConnectivityManager cn = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cn.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            available = true;
        }
        return available;
    }
}
