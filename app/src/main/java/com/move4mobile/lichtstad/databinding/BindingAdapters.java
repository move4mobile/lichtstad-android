package com.move4mobile.lichtstad.databinding;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.move4mobile.lichtstad.model.Size;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingAdapters {

    @BindingAdapter({"android:text", "format"})
    public static void setDate(TextView view, Date date, String format) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(format);
        view.setText(dateFormat.format(date));
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, long text) {
        view.setText(String.valueOf(text));
    }

    @BindingAdapter("android:src")
    public static void setSource(ImageView imageView, String source) {
        if (source.length() == 0) {
            source = null;
        }
        Picasso.with(imageView.getContext())
                .load(source)
                .into(imageView);
    }

    @BindingAdapter("layout_constraintDimensionRatio")
    public static void setRatio(View view, Size size) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).dimensionRatio = "h," + size.getWidth() + ":" + size.getHeight();
        } else {
            Log.w("dimensionRatio", "Asked to set ratio to LayoutParams of class " + layoutParams.getClass());
        }
    }

}
