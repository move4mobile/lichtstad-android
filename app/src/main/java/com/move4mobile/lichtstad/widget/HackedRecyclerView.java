package com.move4mobile.lichtstad.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

//This recyclerview prevents a crash when a view is recycled and readded during an animation.
//The child has a parent which is a ViewOverlay. This causes the app to crash. Removing the parent fixes this.
public class HackedRecyclerView extends RecyclerView {
    public HackedRecyclerView(Context context) {
        super(context);
    }

    public HackedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HackedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addView(View child, int index) {
        if (child.getParent() != null) {
            ((ViewGroup)child.getParent()).removeView(child);
        }
        super.addView(child, index);
    }
}
