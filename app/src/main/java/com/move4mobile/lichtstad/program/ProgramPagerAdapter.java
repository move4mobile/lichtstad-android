package com.move4mobile.lichtstad.program;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.move4mobile.lichtstad.R;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ProgramPagerAdapter extends FragmentStatePagerAdapter {

    private List<Calendar> days;
    private DateFormat format;

    @SuppressLint("SimpleDateFormat")
    public ProgramPagerAdapter(Context context, FragmentManager fm, List<Calendar> days) {
        super(fm);
        this.days = Collections.unmodifiableList(days);

        DateFormatSymbols symbols = DateFormatSymbols.getInstance(context.getResources().getConfiguration().locale);
        symbols.setShortMonths(context.getResources().getStringArray(R.array.abbreviated_months));
        format = new SimpleDateFormat(context.getString(R.string.format_tab_date), symbols);
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
