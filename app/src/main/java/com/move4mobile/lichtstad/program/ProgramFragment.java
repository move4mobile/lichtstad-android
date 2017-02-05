package com.move4mobile.lichtstad.program;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentProgramBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProgramFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProgramBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program, container, false);

        binding.viewPager.setAdapter(new ProgramPagerAdapter(getChildFragmentManager(), getDays()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        return binding.getRoot();
    }

    private List<Calendar> getDays() {
        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat(getString(R.string.date_format));

        String[] dateStrings = getActivity().getResources().getStringArray(R.array.lichtstad_days);
        List<Calendar> days = new ArrayList<>(dateStrings.length);
        for (String dateString : dateStrings) {
            Calendar day = Calendar.getInstance();
            try {
                day.setTime(format.parse(dateString));
            } catch (ParseException e) {
                // Since we specify the source string ourselves this should never happen anyway
                throw new RuntimeException(e);
            }
            days.add(day);
        }
        return days;
    }
}
