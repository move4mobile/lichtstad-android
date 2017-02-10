package com.move4mobile.lichtstad.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.R;

/**
 * Creates a reveal effect transition on an {@link AppBarLayout}
 *
 * The {@link AppBarLayout} should be setup with a {@link android.widget.FrameLayout} as its direct child
 * Inside the {@link android.widget.FrameLayout} as its first child should be another {@link android.widget.FrameLayout} with id {@link R.id#transitionBackground}
 * Use a vertical {@link android.widget.LinearLayout} as second child to contain the {@link AppBarLayout}'s normal subviews
 *  to replicate normal {@link AppBarLayout behaviour}
 */
public class AppBarReveal extends Transition {

    private static final String PROPNAME_WIDTH = "lichtstad:appbarreveal:width";
    private static final String PROPNAME_HEIGHT = "lichtstad:appbarreveal:height";
    private static final String PROPNAME_BACKGROUND = "lichtstad:appbarreveal:background";

    public AppBarReveal() {}

    public AppBarReveal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean shouldRun(TransitionValues values) {
        return values.view instanceof AppBarLayout;
    }

    private void captureValues(TransitionValues transitionValues) {
        if (!shouldRun(transitionValues)) {
            return;
        }

        transitionValues.values.put(PROPNAME_WIDTH, transitionValues.view.getMeasuredWidth());
        transitionValues.values.put(PROPNAME_HEIGHT, transitionValues.view.getMeasuredHeight());
        transitionValues.values.put(PROPNAME_BACKGROUND, transitionValues.view.getBackground());
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        if (!shouldRun(endValues) || !shouldRun(startValues)) {
            return null;
        }

        int width = Math.max((int) startValues.values.get(PROPNAME_WIDTH), (int) endValues.values.get(PROPNAME_WIDTH));
        int height = Math.max((int) startValues.values.get(PROPNAME_HEIGHT), (int) endValues.values.get(PROPNAME_HEIGHT));

        int centerX = width / 2;
        int centerY = 0;

        int radius = Math.max(width / 2, height);

        final View revealedView = endValues.view.findViewById(R.id.transitionBackground);

        endValues.view.setBackground((Drawable) startValues.values.get(PROPNAME_BACKGROUND));
        revealedView.setBackground((Drawable) endValues.values.get(PROPNAME_BACKGROUND));

        Animator animator = ViewAnimationUtils.createCircularReveal(revealedView, centerX, centerY, 0, radius);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                endValues.view.setBackground((Drawable) endValues.values.get(PROPNAME_BACKGROUND));
                revealedView.setBackground(null);
            }
        });
        return animator;
    }
}
