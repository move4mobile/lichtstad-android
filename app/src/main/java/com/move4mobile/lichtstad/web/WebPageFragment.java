package com.move4mobile.lichtstad.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.BaseContentFragment;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FirebaseQueryStringLiveData;
import com.move4mobile.lichtstad.databinding.FragmentWebPageBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class WebPageFragment extends BaseContentFragment {

    private static final String ARG_REFERENCE_KEY = "reference_key";

    public static WebPageFragment newInstance(String referenceKey) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_REFERENCE_KEY, referenceKey);

        WebPageFragment fragment = new WebPageFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentWebPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_page, container, false);
        binding.setLifecycleOwner(this);

        binding.setContent(new FirebaseQueryStringLiveData(FirebaseReferences.instance().get(getArguments().getString(ARG_REFERENCE_KEY))));

        return binding.getRoot();
    }
}
