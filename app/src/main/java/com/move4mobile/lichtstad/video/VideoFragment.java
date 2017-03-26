package com.move4mobile.lichtstad.video;

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
import com.move4mobile.lichtstad.databinding.FragmentVideoBinding;

public class VideoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentVideoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);

        PagerAdapter adapter = new VideoPagerAdapter(getChildFragmentManager(), getYears());
        binding.component.viewPager.setAdapter(adapter);
        binding.component.viewPager.setCurrentItem(adapter.getCount() - 1);

        binding.component.tabLayout.setupWithViewPager(binding.component.viewPager);
        getActivity().setActionBar(binding.component.toolbar);

        return binding.getRoot();
    }

    @NonNull
    private int[] getYears() {
        return getActivity().getResources().getIntArray(R.array.album_years);
    }
}
