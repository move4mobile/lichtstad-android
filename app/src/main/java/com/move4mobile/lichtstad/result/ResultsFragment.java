package com.move4mobile.lichtstad.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.BaseContentFragment;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.adapter.YearFragmentPagerAdapter;
import com.move4mobile.lichtstad.databinding.FragmentResultsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

public class ResultsFragment extends BaseContentFragment implements YearFragmentPagerAdapter.FragmentSupplier {

    private static final String ARG_OVERVIEW_KEY = "overview";
    private static final String ARG_CONTENT_KEY = "content";

    public static ResultsFragment newInstance(@NonNull String overviewReferenceKey, @NonNull String contentReferenceKey) {
        Bundle args = new Bundle();
        args.putString(ARG_OVERVIEW_KEY, overviewReferenceKey);
        args.putString(ARG_CONTENT_KEY, contentReferenceKey);

        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String overviewKey;
    private String contentKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentResultsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false);
        binding.setLifecycleOwner(this);

        PagerAdapter adapter = new YearFragmentPagerAdapter(getChildFragmentManager(), getYears(), this);
        binding.component.viewPager.setAdapter(adapter);
        binding.component.viewPager.setCurrentItem(adapter.getCount() - 1);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("Null arguments");
        }
        overviewKey = arguments.getString(ARG_OVERVIEW_KEY);
        contentKey = arguments.getString(ARG_CONTENT_KEY);

        binding.component.tabLayout.setupWithViewPager(binding.component.viewPager);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.component.toolbar.toolbar);

        return binding.getRoot();
    }

    @NonNull
    private String[] getYears() {
        return getActivity().getResources().getStringArray(R.array.result_years);
    }

    @Override
    public Fragment supplyFragment(String year) {
        return ResultYearFragment.newInstance(overviewKey, contentKey, year);
    }
}
