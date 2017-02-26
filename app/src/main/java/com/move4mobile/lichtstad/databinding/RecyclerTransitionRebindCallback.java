package com.move4mobile.lichtstad.databinding;

import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.transition.TransitionManager;
import android.view.ViewGroup;

/**
 * A {@link OnRebindCallback} that can be used on the binding of a item in a {@link android.support.v7.widget.RecyclerView.Adapter}.
 * It makes the {@link android.support.v7.widget.RecyclerView} animate the rebind.
 */
public class RecyclerTransitionRebindCallback extends OnRebindCallback<ViewDataBinding> {

    @Override
    public boolean onPreBind(ViewDataBinding binding) {
        TransitionManager.beginDelayedTransition((ViewGroup) binding.getRoot().getParent());
        return super.onPreBind(binding);
    }
}
