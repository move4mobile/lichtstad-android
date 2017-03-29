package com.move4mobile.lichtstad.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class ScrollDisablableViewPager extends ViewPager {

    private boolean scrollEnabled = true;

    public ScrollDisablableViewPager(Context context) {
        super(context);
    }

    public ScrollDisablableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollEnabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        return scrollEnabled && super.executeKeyEvent(event);
    }

    public boolean isScrollEnabled() {
        return scrollEnabled;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.scrollEnabled = scrollEnabled;
    }
}
