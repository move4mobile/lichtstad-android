package com.move4mobile.lichtstad.program;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.move4mobile.lichtstad.R;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ProgramPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Calendar> days;
    private DateFormat format;

    @SuppressLint("SimpleDateFormat")
    public ProgramPagerAdapter(Context context, FragmentManager fm, List<Calendar> days) {
        super(fm);
        this.context = context;
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
        String formattedDate = format.format(days.get(position).getTime());
        String[] lines = formattedDate.split("\n", 2);
        if (lines.length == 0) {
            throw new IllegalStateException();
        }
        if (lines.length == 1) {
            return formattedDate;
        }

        SpannableString firstLine = new SpannableString(lines[0]);
        firstLine.setSpan(new RelativeSizeSpan(13f / 15), 0, firstLine.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        String date = lines[1].toUpperCase(context.getResources().getConfiguration().locale);
        SpannableString spannedDate = new SpannableString(date);
        spannedDate.setSpan(new StyleSpan(Typeface.BOLD), 0, date.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return new SpannableStringBuilder()
                .append(lines[0])
                .append('\n')
                .append(spannedDate);
    }
}
