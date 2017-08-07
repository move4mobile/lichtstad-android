package com.move4mobile.lichtstad.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;


public class YearFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private final int[] years;
    private final FragmentSupplier fragmentSupplier;

    public YearFragmentPagerAdapter(FragmentManager fm, int[] years, FragmentSupplier fragmentSupplier) {
        super(fm);
        this.years = years;
        this.fragmentSupplier = fragmentSupplier;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentSupplier.supplyFragment(years[position]);
    }

    @Override
    public int getCount() {
        return years.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableString pageTitle = new SpannableString("" + years[position]);
        pageTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, pageTitle.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return pageTitle;
    }

    public interface FragmentSupplier {
        Fragment supplyFragment(int year);
    }
}
