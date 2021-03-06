package com.move4mobile.lichtstad.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;


public class YearFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private final String[] years;
    private final FragmentSupplier fragmentSupplier;

    public YearFragmentPagerAdapter(FragmentManager fm, String[] years, FragmentSupplier fragmentSupplier) {
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
        Fragment supplyFragment(String year);
    }
}
