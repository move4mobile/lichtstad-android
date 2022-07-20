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
    private static final String ARG_DEFAULT_CONTENT = "default_content";

    public static WebPageFragment newInstance(String referenceKey, String defaultContent) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_REFERENCE_KEY, referenceKey);
        arguments.putString(ARG_DEFAULT_CONTENT, defaultContent);

        WebPageFragment fragment = new WebPageFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentWebPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_page, container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());

        binding.setContent(new FirebaseQueryStringLiveData(FirebaseReferences.instance().get(getArguments().getString(ARG_REFERENCE_KEY))));
        binding.setDefaultContent(getArguments().getString(ARG_DEFAULT_CONTENT));

        return binding.getRoot();
    }
}
