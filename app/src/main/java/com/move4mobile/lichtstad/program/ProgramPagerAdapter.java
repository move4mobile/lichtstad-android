package com.move4mobile.lichtstad.program;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.move4mobile.lichtstad.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ProgramPagerAdapter extends FragmentStatePagerAdapter {

    private List<Calendar> days;
    private Context context;
    private DateFormat format;

    public ProgramPagerAdapter(Context context, FragmentManager fm, List<Calendar> days) {
        super(fm);
        this.context = context;
        this.days = Collections.unmodifiableList(days);

        format = new SimpleDateFormat(context.getString(R.string.format_tab_date), context.getResources().getConfiguration().locale);
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
