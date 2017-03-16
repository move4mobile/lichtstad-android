package com.move4mobile.lichtstad.video;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

public class VideoPagerAdapter extends FragmentStatePagerAdapter {

    private final int[] years;

    public VideoPagerAdapter(FragmentManager fm, int[] years) {
        super(fm);
        this.years = years;
    }

    @Override
    public Fragment getItem(int position) {
        return VideosYearFragment.newInstance(years[position]);
    }

    @Override
    public int getCount() {
        return years.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "" + years[position];
    }
}
