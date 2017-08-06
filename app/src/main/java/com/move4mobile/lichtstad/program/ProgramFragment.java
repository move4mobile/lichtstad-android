package com.move4mobile.lichtstad.program;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.BaseContentFragment;
import com.move4mobile.lichtstad.BuildConfig;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentProgramBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProgramFragment extends BaseContentFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProgramBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program, container, false);

        List<Calendar> days = getDays();
        int currentIndex = days.indexOf(getSelectedDay(days));

        binding.component.viewPager.setAdapter(new ProgramPagerAdapter(getActivity(), getChildFragmentManager(), days));
        binding.component.viewPager.setCurrentItem(currentIndex);
        binding.component.tabLayout.setupWithViewPager(binding.component.viewPager);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.component.toolbar.toolbar);

        return binding.getRoot();
    }

    /**
     * Creates a list of each day the programs should be shown for
     * @return A list of calendars, one of each day of the event
     */
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

    private Calendar getSelectedDay(List<Calendar> days) {
        Calendar today = Calendar.getInstance(BuildConfig.EVENT_TIMEZONE);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        for (Calendar day : days) {
            if (today.equals(day)) {
                return day;
            }
        }
        return days.get(0);
    }
}
