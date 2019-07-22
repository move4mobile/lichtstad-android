package com.move4mobile.lichtstad.program;

import android.annotation.SuppressLint;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.BaseContentFragment;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentProgramBinding;
import com.move4mobile.lichtstad.util.ConfigUtil;
import com.move4mobile.lichtstad.viewmodel.ProgramViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProgramFragment extends BaseContentFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProgramBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program, container, false);
        binding.setLifecycleOwner(this);

        List<Calendar> days = getDays();
        int currentIndex = days.indexOf(getSelectedDay(days));

        binding.component.viewPager.setAdapter(new ProgramPagerAdapter(getContext(), getChildFragmentManager(), days));
        binding.component.viewPager.setCurrentItem(currentIndex);
        binding.component.tabLayout.setupWithViewPager(binding.component.viewPager);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.component.toolbar.toolbar);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.program, menu);
        updateFavoriteIcon(menu.findItem(R.id.show_favorites));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.show_favorites) {
            item.setChecked(!item.isChecked());
            updateFavoriteIcon(item);
            ViewModelProviders.of(this).get(ProgramViewModel.class).showFavorites.setValue(item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateFavoriteIcon(MenuItem item) {
        // For some reason, menu items do not use state_checked for their icons
        // So the icon has to be set by hand 🙄
        item.setIcon(item.isChecked() ? R.drawable.ic_favorite : R.drawable.ic_favorite_empty);
    }

    /**
     * Creates a list of each day the programs should be shown for
     * @return A list of calendars, one of each day of the event
     */
    private List<Calendar> getDays() {
        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat(getString(R.string.date_format));

        String[] dateStrings = getContext().getResources().getStringArray(R.array.lichtstad_days);
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
        Calendar today = Calendar.getInstance(ConfigUtil.getEventTimeZone(getContext()));
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
