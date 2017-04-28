package com.move4mobile.lichtstad.result;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentResultsBinding;

public class ResultsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentResultsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false);

        getActivity().setActionBar(binding.component.toolbar.toolbar);

        return binding.getRoot();
    }
}
