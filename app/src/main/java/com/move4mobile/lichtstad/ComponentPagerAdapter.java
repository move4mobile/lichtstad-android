package com.move4mobile.lichtstad;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.move4mobile.lichtstad.photo.AlbumsFragment;
import com.move4mobile.lichtstad.program.ProgramFragment;
import com.move4mobile.lichtstad.result.ResultsFragment;
import com.move4mobile.lichtstad.video.VideoFragment;

public class ComponentPagerAdapter extends FragmentStatePagerAdapter {

    public static final int POSITION_PROGRAM = 0;
    public static final int POSITION_RESULTS = 1;
    public static final int POSITION_PHOTOS = 2;
    public static final int POSITION_VIDEOS = 3;

    public ComponentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_PROGRAM:
                return new ProgramFragment();
            case POSITION_RESULTS:
                return new ResultsFragment();
            case POSITION_PHOTOS:
                return new AlbumsFragment();
            case POSITION_VIDEOS:
                return new VideoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
