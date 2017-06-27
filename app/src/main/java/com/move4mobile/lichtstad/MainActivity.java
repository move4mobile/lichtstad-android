package com.move4mobile.lichtstad;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;

import com.move4mobile.lichtstad.databinding.ActivityMainBinding;
import com.move4mobile.lichtstad.photo.album.AlbumsFragment;
import com.move4mobile.lichtstad.program.ProgramFragment;
import com.move4mobile.lichtstad.result.ResultsFragment;
import com.move4mobile.lichtstad.util.BottomNavigationViewTinter;
import com.move4mobile.lichtstad.video.VideoFragment;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewTinter.tintBottomNavigationButtons(binding.bottomNavigation,
                this,
                R.color.bottom_navigation_tint_program,
                R.color.bottom_navigation_tint_result,
                R.color.bottom_navigation_tint_photo,
                R.color.bottom_navigation_tint_video
        );

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProgramFragment())
                    .commit();
        }
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
            default:
                return false;
        }
    }

    private void showFragment(Fragment fragment) {
        fragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element));

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        View appBar = findViewById(R.id.appbar);
        if (appBar != null) {
            transaction.addSharedElement(appBar, appBar.getTransitionName());
        }
        transaction.replace(R.id.fragment_container, fragment)
                .commit();
    }
}
