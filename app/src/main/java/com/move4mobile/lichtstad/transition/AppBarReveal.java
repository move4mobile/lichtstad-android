package com.move4mobile.lichtstad.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
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

import java.util.ArrayList;

/**
 * Creates a reveal effect transition on an {@link AppBarLayout}
 * <p>
 * The {@link AppBarLayout} should be setup with a {@link android.widget.FrameLayout} as its direct child
 * Inside the {@link android.widget.FrameLayout} as its first child should be another {@link android.widget.FrameLayout} with id {@link R.id#transitionBackground}
 * Use a vertical {@link android.widget.LinearLayout} as second child to contain the {@link AppBarLayout}'s normal subviews
 * to replicate normal {@link AppBarLayout behaviour}
 */
public class AppBarReveal extends Transition {

    private static final String PROPNAME_WIDTH = "lichtstad:appbarreveal:width";
    private static final String PROPNAME_HEIGHT = "lichtstad:appbarreveal:height";
    private static final String PROPNAME_BACKGROUND = "lichtstad:appbarreveal:background";

    public AppBarReveal() {
    }

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

        return new UnpausableAnimator(animator);
    }

    private class UnpausableAnimator extends Animator {

        private final Animator wrappedAnimator;

        private UnpausableAnimator(Animator wrapped) {
            this.wrappedAnimator = wrapped;
        }

        @Override
        public void start() {
            wrappedAnimator.start();
        }

        @Override
        public void cancel() {
            //Uncancelable
        }

        @Override
        public void end() {
            wrappedAnimator.end();
        }

        @Override
        public void pause() {
            //Unpausable
        }

        @Override
        public void resume() {
            //Unresumable
        }

        @Override
        public boolean isPaused() {
            return wrappedAnimator.isPaused();
        }

        @Override
        public long getStartDelay() {
            return wrappedAnimator.getStartDelay();
        }

        @Override
        public void setStartDelay(long startDelay) {
            wrappedAnimator.setStartDelay(startDelay);
        }

        @Override
        public Animator setDuration(long duration) {
            return wrappedAnimator.setDuration(duration);
        }

        @Override
        public long getDuration() {
            return wrappedAnimator.getDuration();
        }

        @Override
        @TargetApi(24)
        public long getTotalDuration() {
            return wrappedAnimator.getTotalDuration();
        }

        @Override
        public void setInterpolator(TimeInterpolator value) {
            wrappedAnimator.setInterpolator(value);
        }

        @Override
        public TimeInterpolator getInterpolator() {
            return wrappedAnimator.getInterpolator();
        }

        @Override
        public boolean isRunning() {
            return wrappedAnimator.isRunning();
        }

        @Override
        public boolean isStarted() {
            return wrappedAnimator.isStarted();
        }

        @Override
        public void addListener(AnimatorListener listener) {
            wrappedAnimator.addListener(listener);
        }

        @Override
        public void removeListener(AnimatorListener listener) {
            wrappedAnimator.removeListener(listener);
        }

        @Override
        public ArrayList<AnimatorListener> getListeners() {
            return wrappedAnimator.getListeners();
        }

        @Override
        public void addPauseListener(AnimatorPauseListener listener) {
            wrappedAnimator.addPauseListener(listener);
        }

        @Override
        public void removePauseListener(AnimatorPauseListener listener) {
            wrappedAnimator.removePauseListener(listener);
        }

        @Override
        public void removeAllListeners() {
            wrappedAnimator.removeAllListeners();
        }

        @SuppressWarnings("CloneDoesntCallSuperClone")
        @Override
        public Animator clone() {
            return new UnpausableAnimator(wrappedAnimator.clone());
        }

        @Override
        public void setupStartValues() {
            wrappedAnimator.setupStartValues();
        }

        @Override
        public void setupEndValues() {
            wrappedAnimator.setupEndValues();
        }

        @Override
        public void setTarget(Object target) {
            wrappedAnimator.setTarget(target);
        }
    }
}
