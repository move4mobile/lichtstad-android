package com.move4mobile.lichtstad.program;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ProgramPagerAdapter extends FragmentStatePagerAdapter {

    private List<Calendar> days;
    private DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.SHORT);

    public ProgramPagerAdapter(FragmentManager fm, List<Calendar> days) {
        super(fm);
        this.days = Collections.unmodifiableList(days);
    }

    @Override
    public Fragment getItem(int position) {
        return ProgramDayFragment.newInstance(days.get(position));
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return format.format(days.get(position).getTime());
    }
}
