package com.move4mobile.lichtstad.menu;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.move4mobile.lichtstad.MainActivity;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.map.MapFragment;
import com.move4mobile.lichtstad.photo.album.AlbumsFragment;
import com.move4mobile.lichtstad.program.ProgramFragment;
import com.move4mobile.lichtstad.result.ResultsFragment;
import com.move4mobile.lichtstad.video.VideoFragment;

import androidx.annotation.NonNull;

public class MenuNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final MainActivity mainActivity;

    public MenuNavigationItemSelectedListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_program:
                mainActivity.showFragment(new ProgramFragment());
                return true;
            case R.id.action_results:
                mainActivity.showFragment(ResultsFragment.newInstance("result", "result_content"));
                return true;
            case R.id.action_photos:
                mainActivity.showFragment(new AlbumsFragment());
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

}
