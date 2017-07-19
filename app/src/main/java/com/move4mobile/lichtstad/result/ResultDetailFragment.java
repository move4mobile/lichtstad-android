package com.move4mobile.lichtstad.result;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentResultDetailBinding;
import com.move4mobile.lichtstad.model.Result;

public class ResultDetailFragment extends Fragment {

    private static final String ARG_RESULT = "RESULT";

    public static ResultDetailFragment newInstance(Result result) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_RESULT, result);

        ResultDetailFragment fragment = new ResultDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private Result result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_RESULT)) {
            throw new IllegalStateException("No result");
        }

        this.result = getArguments().getParcelable(ARG_RESULT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentResultDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_detail, container, false);

        binding.setResult(result);

        return binding.getRoot();
    }
}
