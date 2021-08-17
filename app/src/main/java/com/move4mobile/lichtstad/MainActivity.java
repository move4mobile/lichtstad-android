package com.move4mobile.lichtstad;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;

import com.move4mobile.lichtstad.databinding.ActivityMainBinding;
import com.move4mobile.lichtstad.menu.NavigationHandler;
import com.move4mobile.lichtstad.util.BottomNavigationViewTinter;
import com.move4mobile.lichtstad.util.GoogleMapLoader;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        NavigationHandler navigationHandler = new NavigationHandler(this);
        binding.bottomNavigation.setOnItemSelectedListener(navigationHandler);
        BottomNavigationViewTinter.tintBottomNavigationButtons(binding.bottomNavigation,
                this,
                R.array.bottom_navigation_tint_lists
        );

        if (savedInstanceState == null) {
            navigationHandler.showInitialFragment();
        }

        GoogleMapLoader.preloadGoogleMap(this);
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }

    public void setStatusBarColor(@ColorInt int color) {
        binding.drawerLayout.setStatusBarBackgroundColor(color);
    }

    public void showFragment(Fragment fragment) {
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
