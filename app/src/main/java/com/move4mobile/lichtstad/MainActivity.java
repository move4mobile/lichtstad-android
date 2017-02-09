package com.move4mobile.lichtstad;

import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.move4mobile.lichtstad.databinding.ActivityMainBinding;
import com.move4mobile.lichtstad.program.ProgramFragment;
import com.move4mobile.lichtstad.photo.AlbumsFragment;
import com.move4mobile.lichtstad.result.ResultsFragment;
import com.move4mobile.lichtstad.video.VideosFragment;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProgramFragment())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_program:
                showFragment(new ProgramFragment());
                return true;
            case R.id.action_results:
                showFragment(new ResultsFragment());
                return true;
            case R.id.action_photos:
                showFragment(new AlbumsFragment());
                return true;
            case R.id.action_videos:
                showFragment(new VideosFragment());
                return true;
            default:
                return false;
        }
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
