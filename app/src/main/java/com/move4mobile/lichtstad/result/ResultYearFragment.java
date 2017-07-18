package com.move4mobile.lichtstad.result;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ResultYearFragment extends Fragment {

    private static final String ARG_YEAR = "year";

    public static ResultYearFragment newInstance(int year) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_YEAR, year);

        ResultYearFragment fragment = new ResultYearFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private int year;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_YEAR)) {
            throw new IllegalStateException("No year");
        }
        this.year = getArguments().getInt(ARG_YEAR);
    }


}
