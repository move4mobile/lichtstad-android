package com.move4mobile.lichtstad.result;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.ActivityResultDetailBinding;
import com.move4mobile.lichtstad.model.Result;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class ResultDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT = "RESULT";
    public static final String EXTRA_DETAIL_KEY = "DETAIL";

    private static final String TAG = ResultDetailActivity.class.getSimpleName();

    public static Intent newInstanceIntent(Context context, @NonNull String detailKey, @NonNull Result result) {
        Intent intent = new Intent(context, ResultDetailActivity.class);
        intent.putExtra(EXTRA_RESULT, result);
        intent.putExtra(EXTRA_DETAIL_KEY, detailKey);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityResultDetailBinding resultDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_result_detail);

        //This doesn't work from xml :(
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);

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
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ResultDetailFragment.newInstance(getIntent().getStringExtra(EXTRA_DETAIL_KEY), result))
                    .commit();
        } else {
            Log.e(TAG, "No album passed as extra");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, null)
                    .commit();
        }
    }
}
