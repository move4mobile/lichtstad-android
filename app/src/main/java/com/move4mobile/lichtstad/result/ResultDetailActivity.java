package com.move4mobile.lichtstad.result;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.ActivityResultDetailBinding;
import com.move4mobile.lichtstad.model.Result;

public class ResultDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT = "RESULT";

    private static final String TAG = ResultDetailActivity.class.getSimpleName();

    public static Intent newInstanceIntent(Context context, @NonNull Result result) {
        Intent intent = new Intent(context, ResultDetailActivity.class);
        intent.putExtra(EXTRA_RESULT, result);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityResultDetailBinding resultDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_result_detail);

        if (savedInstanceState == null) {
            applyIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        applyIntent(getIntent());
    }

    private void applyIntent(Intent intent) {
        Result result = intent.getParcelableExtra(EXTRA_RESULT);

        if (result != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ResultDetailFragment.newInstance(result))
                    .commit();
        } else {
            Log.e(TAG, "No album passed as extra");
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, null)
                    .commit();
        }
    }
}