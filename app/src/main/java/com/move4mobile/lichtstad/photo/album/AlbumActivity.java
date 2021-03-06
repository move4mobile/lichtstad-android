package com.move4mobile.lichtstad.photo.album;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.ActivityAlbumBinding;
import com.move4mobile.lichtstad.model.Album;

import java.util.Objects;


public class AlbumActivity extends AppCompatActivity {

    public static final String EXTRA_ALBUM = "ALBUM";

    public static Intent newInstanceIntent(Context context, Album album) {
        Intent intent = new Intent(context, AlbumActivity.class);
        intent.putExtra(EXTRA_ALBUM, album);
        return intent;
    }

    private static final String TAG = AlbumActivity.class.getSimpleName();

    private String currentAlbumKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAlbumBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_album);
        binding.setLifecycleOwner(this);

        //This doesn't work from xml :(
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);

        if (savedInstanceState == null) {
            applyFragment(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        applyFragment(intent);
    }

    private void applyFragment(Intent intent) {
        Album album = intent.getParcelableExtra(EXTRA_ALBUM);

        if (album != null) {
            if (!Objects.equals(currentAlbumKey, album.getKey())) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, AlbumDetailFragment.newInstance(album))
                        .commit();
            }
            currentAlbumKey = album.getKey();
        } else {
            Log.e(TAG, "No album passed as extra");
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(currentFragment)
                        .commit();
            }
        }
    }
}
