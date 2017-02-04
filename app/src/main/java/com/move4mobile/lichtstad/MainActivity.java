package com.move4mobile.lichtstad;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.move4mobile.lichtstad.databinding.ActivityMainBinding;
import com.move4mobile.lichtstad.program.ProgramDayFragment;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Calendar testDate = Calendar.getInstance(TimeZone.getTimeZone("CEST"));
        testDate.set(2016, 7, 28);
        getFragmentManager().beginTransaction()
                .add(R.id.activity_main, ProgramDayFragment.newInstance(testDate))
                .commit();
    }
}
