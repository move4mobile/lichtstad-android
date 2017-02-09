package com.move4mobile.lichtstad;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.move4mobile.lichtstad.databinding.ActivityMainBinding;
import com.move4mobile.lichtstad.program.ProgramFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProgramFragment())
                .commit();
    }
}
