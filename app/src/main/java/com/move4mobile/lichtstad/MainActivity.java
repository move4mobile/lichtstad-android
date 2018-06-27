package com.move4mobile.lichtstad;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.MapView;
import com.move4mobile.lichtstad.databinding.ActivityMainBinding;
import com.move4mobile.lichtstad.map.MapFragment;
import com.move4mobile.lichtstad.photo.album.AlbumsFragment;
import com.move4mobile.lichtstad.program.ProgramFragment;
import com.move4mobile.lichtstad.result.ResultsFragment;
import com.move4mobile.lichtstad.util.BottomNavigationViewTinter;
import com.move4mobile.lichtstad.util.GoogleMapLoader;
import com.move4mobile.lichtstad.video.VideoFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewTinter.tintBottomNavigationButtons(binding.bottomNavigation,
                this,
                R.array.bottom_navigation_tint_lists
        );

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProgramFragment())
                    .commit();
        }

        GoogleMapLoader.preloadGoogleMap(this);
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
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
                showFragment(new VideoFragment());
                return true;
            case R.id.action_map:
                showFragment(new MapFragment());
                return true;
            default:
                return false;
        }
    }

    public void setStatusBarColor(@ColorInt int color) {
        binding.drawerLayout.setStatusBarBackgroundColor(color);
    }

    private void showFragment(Fragment fragment) {
        fragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        View appBar = findViewById(R.id.appbar);
        if (appBar != null) {
            transaction.addSharedElement(appBar, appBar.getTransitionName());
        }
        transaction.replace(R.id.fragment_container, fragment)
                .commit();
    }
}
