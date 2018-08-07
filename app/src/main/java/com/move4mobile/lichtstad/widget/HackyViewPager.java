package com.move4mobile.lichtstad.widget;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * There are some ViewGroups (ones that utilize onInterceptTouchEvent) that throw exceptions when
 * a PhotoView is placed within them, most notably ViewPager and DrawerLayout.
 * This is a framework issue that has not been resolved.
 * https://github.com/chrisbanes/PhotoView#issues-with-viewgroups
 */
public class HackyViewPager extends ViewPager {

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
