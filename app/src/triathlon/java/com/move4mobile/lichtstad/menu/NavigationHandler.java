package com.move4mobile.lichtstad.menu;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.move4mobile.lichtstad.MainActivity;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.map.MapFragment;
import com.move4mobile.lichtstad.photo.album.AlbumsFragment;
import com.move4mobile.lichtstad.program.ProgramFragment;
import com.move4mobile.lichtstad.result.ResultsFragment;
import com.move4mobile.lichtstad.util.InputStreamReader;
import com.move4mobile.lichtstad.video.VideoFragment;
import com.move4mobile.lichtstad.web.WebPageFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class NavigationHandler implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final MainActivity mainActivity;

    public NavigationHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showInitialFragment() {
        mainActivity.showFragment(getHomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                mainActivity.showFragment(getHomeFragment());
                return true;
            case R.id.action_program:
                mainActivity.showFragment(new ProgramFragment());
                return true;
            case R.id.action_info:
                mainActivity.showFragment(ResultsFragment.newInstance("info", "info_content"));
                return true;
            case R.id.action_videos:
                mainActivity.showFragment(new VideoFragment());
                return true;
            case R.id.action_map:
                mainActivity.showFragment(MapFragment.newInstance("route", "markers"));
                return true;
            default:
                return false;
        }
    }

    private Fragment getHomeFragment() {
        String defaultString = InputStreamReader.readString(mainActivity.getResources().openRawResource(R.raw.home_default_text));
        return WebPageFragment.newInstance("home", defaultString);
    }

}
