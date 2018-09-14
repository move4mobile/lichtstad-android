package com.move4mobile.lichtstad.menu;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.move4mobile.lichtstad.MainActivity;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.map.MapFragment;
import com.move4mobile.lichtstad.photo.album.AlbumsFragment;
import com.move4mobile.lichtstad.program.ProgramFragment;
import com.move4mobile.lichtstad.result.ResultsFragment;
import com.move4mobile.lichtstad.web.WebPageFragment;

import androidx.annotation.NonNull;

public class NavigationHandler implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final MainActivity mainActivity;

    public NavigationHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showInitialFragment() {
        mainActivity.showFragment(WebPageFragment.newInstance("home"));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                mainActivity.showFragment(WebPageFragment.newInstance("home"));
                return true;
            case R.id.action_program:
                mainActivity.showFragment(new ProgramFragment());
                return true;
            case R.id.action_info:
                mainActivity.showFragment(ResultsFragment.newInstance("info", "info_content"));
                return true;
            case R.id.action_photos:
                mainActivity.showFragment(new AlbumsFragment());
                return true;
            case R.id.action_map:
                mainActivity.showFragment(MapFragment.newInstance("route"));
                return true;
            default:
                return false;
        }
    }

}
