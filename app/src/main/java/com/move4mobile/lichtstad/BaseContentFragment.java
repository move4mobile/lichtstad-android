package com.move4mobile.lichtstad;

import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class BaseContentFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateStatusBarColor(view);
    }

    private void updateStatusBarColor(View view) {
        TypedArray typedArray = view.getContext().obtainStyledAttributes(new int[] {android.R.attr.colorPrimaryDark});
        int color = typedArray.getColor(0, view.getContext().getResources().getColor(R.color.colorPrimaryDark));
        typedArray.recycle();
        ((MainActivity)getActivity()).setStatusBarColor(color);
    }
}
