package com.move4mobile.lichtstad.databinding;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingAdapters {

    @BindingAdapter({"android:text", "format"})
    public static void setDate(TextView view, Date date, String format) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(format);
        view.setText(dateFormat.format(date));
    }

}
