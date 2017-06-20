package com.move4mobile.lichtstad.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.ActivityViewPhotoBinding;
import com.move4mobile.lichtstad.model.Album;
import com.move4mobile.lichtstad.model.Photo;

public class PhotoViewActivity extends Activity {

    public static final String EXTRA_ALBUM = "ALBUM";
    public static final String EXTRA_CURRENT_PHOTO = "CURRENT_PHOTO";

    private static final String TAG = PhotoViewActivity.class.getSimpleName();

    public static Intent newInstanceIntent(Context context, @NonNull Album album, @Nullable Photo currentPhoto) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(EXTRA_ALBUM, album);
        intent.putExtra(EXTRA_CURRENT_PHOTO, currentPhoto);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityViewPhotoBinding viewPhotoBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_photo);

        if (savedInstanceState == null) {
            applyIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        applyIntent(intent);
    }

    private void applyIntent(Intent intent) {
        Album album = intent.getParcelableExtra(EXTRA_ALBUM);
        Photo currentPhoto = intent.getParcelableExtra(EXTRA_CURRENT_PHOTO);

        if (album != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, PhotoDetailFragment.newInstance(album, currentPhoto))
                    .commit();
        } else {
            Log.e(TAG, "No album passed as extra");
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, null)
                    .commit();
        }

    }
}
