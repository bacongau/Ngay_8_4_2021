package com.example.ngay_8_4_2021.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.ngay_8_4_2021.R;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {

        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        setContentView(R.layout.loading_dialog);
        setCanceledOnTouchOutside(false);

    }
}
