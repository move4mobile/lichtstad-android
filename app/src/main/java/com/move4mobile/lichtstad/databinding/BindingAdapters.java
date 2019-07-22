package com.move4mobile.lichtstad.databinding;

import android.annotation.SuppressLint;
import androidx.databinding.BindingAdapter;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Rect;
import android.text.Html;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.move4mobile.lichtstad.model.Size;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
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
        if (source != null && source.length() == 0) {
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

    @BindingAdapter("content")
    public static void setContent(WebView webView, String content) {
        // Applications targeting Build.VERSION_CODES.Q or later must either use base64 or encode any # characters in the content as %23
        // https://developer.android.com/reference/android/webkit/WebView.html#loadData(java.lang.String,%20java.lang.String,%20java.lang.String)
        String escapedContent = content == null ? null : content.replace("#", "%23");
        webView.loadData(escapedContent, "text/html; charset=UTF-8", null);
    }

    @BindingAdapter("layout_minimumClickSize")
    public static void setMinimumClickSize(View view, float size) {
        View parent = ((View)view.getParent());
        parent.post(() -> {
            Rect hitArea = new Rect();
            view.getHitRect(hitArea);
            int extraWidth = 0;
            int extraHeight = 0;

            if (hitArea.width() < size) {
                extraWidth = (int)(size - hitArea.width());
            }
            if (hitArea.height() < size) {
                extraHeight = (int)(size - hitArea.height());
            }
            hitArea.inset(-extraWidth / 2, -extraHeight / 2);
            parent.setTouchDelegate(new TouchDelegate(hitArea, view));
        });
    }

}
