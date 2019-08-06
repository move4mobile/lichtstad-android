package com.move4mobile.lichtstad.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.move4mobile.lichtstad.LichtstadApplication;

public class ApplicationContext {

    @NonNull
    public static LichtstadApplication getApplication(@NonNull Context context) {
        return (LichtstadApplication) context.getApplicationContext();
    }

}
