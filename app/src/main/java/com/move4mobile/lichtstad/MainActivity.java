package com.move4mobile.lichtstad;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.move4mobile.lichtstad.databinding.ActivityMainBinding;
import com.move4mobile.lichtstad.util.BottomNavigationViewTinter;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewTinter.tintBottomNavigationButtons(binding.bottomNavigation,
                this,
                R.color.bottom_navigation_tint_program,
                R.color.bottom_navigation_tint_result,
                R.color.bottom_navigation_tint_photo,
                R.color.bottom_navigation_tint_video
        );

        binding.fragmentPager.setScrollEnabled(false);
        binding.fragmentPager.setAdapter(new ComponentPagerAdapter(getFragmentManager()));
        binding.fragmentPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_program:
                showFragment(ComponentPagerAdapter.POSITION_PROGRAM);
                return true;
            case R.id.action_results:
                showFragment(ComponentPagerAdapter.POSITION_RESULTS);
                return true;
            case R.id.action_photos:
                showFragment(ComponentPagerAdapter.POSITION_PHOTOS);
                return true;
            case R.id.action_videos:
                showFragment(ComponentPagerAdapter.POSITION_VIDEOS);
                return true;
            default:
                return false;
        }


    }

    private void showFragment(int position) {
        if (binding != null) {
            binding.fragmentPager.setCurrentItem(position, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (binding != null) {
            binding.bottomNavigation.getMenu().getItem(position).setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
