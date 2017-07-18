package com.move4mobile.lichtstad.result;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.adapter.YearFragmentPagerAdapter;
import com.move4mobile.lichtstad.databinding.FragmentResultsBinding;

public class ResultsFragment extends Fragment implements YearFragmentPagerAdapter.FragmentSupplier {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentResultsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false);

        PagerAdapter adapter = new YearFragmentPagerAdapter(getChildFragmentManager(), getYears(), this);
        binding.component.viewPager.setAdapter(adapter);
        binding.component.viewPager.setCurrentItem(adapter.getCount() - 1);

        binding.component.tabLayout.setupWithViewPager(binding.component.viewPager);
        getActivity().setActionBar(binding.component.toolbar.toolbar);

        return binding.getRoot();
    }

    @NonNull
    private int[] getYears() {
        return getActivity().getResources().getIntArray(R.array.history_years);
    }

    @Override
    public Fragment supplyFragment(int year) {
        return ResultYearFragment.newInstance(year);
    }
}
