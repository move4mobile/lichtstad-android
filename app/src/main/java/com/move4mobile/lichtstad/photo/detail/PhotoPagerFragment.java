package com.move4mobile.lichtstad.photo.detail;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentPhotoPagerBinding;
import com.move4mobile.lichtstad.model.Album;
import com.move4mobile.lichtstad.model.Photo;
import com.move4mobile.lichtstad.widget.FirebaseViewPagerAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wilcowolters on 16/05/2017.
 */

public class PhotoPagerFragment extends Fragment {

    private static final String ARG_ALBUM = "ALBUM";
    private static final String ARG_CURRENT_PHOTO = "CURRENT_PHOTO";
    private static final String SAVED_KEY_CURRENT_PHOTO = "CURRENT_PHOTO";

    public static PhotoPagerFragment newInstance(@NonNull Album album, @Nullable Photo currentPhoto) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_ALBUM, album);
        arguments.putParcelable(ARG_CURRENT_PHOTO, currentPhoto);

        PhotoPagerFragment fragment = new PhotoPagerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentPhotoPagerBinding binding;

    private Album album;

    private Photo scrollToPhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_ALBUM)) {
            throw new IllegalStateException("No album");
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_KEY_CURRENT_PHOTO)) {
            scrollToPhoto = savedInstanceState.getParcelable(SAVED_KEY_CURRENT_PHOTO);
        } else {
            scrollToPhoto = getArguments().getParcelable(SAVED_KEY_CURRENT_PHOTO);
        }

        album = getArguments().getParcelable(ARG_ALBUM);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_pager, container, false);

        final PhotoPagerAdapter adapter = new PhotoPagerAdapter(getQuery());
        adapter.setOnMatrixChangedListener(new PhotoPagerAdapter.OnMatrixChangedListener() {
            @Override
            public void onMatrixChanged(RectF matrix, PhotoView photoView) {
                photoView.setAllowParentInterceptOnEdge(photoView.getScale() <= 1);
            }
        });
        adapter.setOnDataChangedListener(new PhotoPagerAdapter.OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                adapter.setOnDataChangedListener(null);
                if (scrollToPhoto != null) {
                    for (int i = 0; i < adapter.getSnapshots().size(); i++) {
                        DataSnapshot snapshot = adapter.getSnapshots().get(i);
                        if (snapshot.getKey().equals(scrollToPhoto.key)) {
                            binding.viewPager.setCurrentItem(i, false);
                        }
                    }
                }
                scrollToPhoto = null;
            }
        });
        binding.viewPager.setAdapter(adapter);
        getActivity().setActionBar(binding.toolbar);

        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().setTitle(album.title);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.photo_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareCurrentPhoto();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (binding != null && binding.viewPager.getAdapter() instanceof PhotoPagerAdapter) {
            outState.putParcelable(SAVED_KEY_CURRENT_PHOTO, ((PhotoPagerAdapter) binding.viewPager.getAdapter()).getItem(binding.viewPager.getCurrentItem()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (binding.viewPager.getAdapter() instanceof FirebaseViewPagerAdapter) {
            FirebaseViewPagerAdapter adapter = (FirebaseViewPagerAdapter) binding.viewPager.getAdapter();
            adapter.cleanup();
        }

        binding = null;
    }

    private Query getQuery() {
        return FirebaseReferences.ALBUM_CONTENT
                .child(album.year)
                .child(album.key)
                .orderByChild("order");
    }

    private void shareCurrentPhoto() {
        Photo photo = getCurrentPhoto();
        if (photo != null) {
            sharePhoto(photo);
        }
    }

    @Nullable
    private Photo getCurrentPhoto() {
        if (binding != null) {
            int currentIndex = binding.viewPager.getCurrentItem();
            if (currentIndex >= 0 && binding.viewPager.getAdapter() instanceof PhotoPagerAdapter) {
                return ((PhotoPagerAdapter) binding.viewPager.getAdapter()).getItem(currentIndex);
            }
        }
        return null;
    }

    private void sharePhoto(@NonNull final Photo photo) {
        Picasso.with(getActivity())
                .load(photo.imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if (!isAdded()) {
                            return;
                        }

                        String mimeType = "image/*";
                        String extension = MimeTypeMap.getFileExtensionFromUrl(photo.imageUrl);
                        if (extension != null) {
                            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                        }

                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                        shareIntent.setType(mimeType);
                        startActivity(Intent.createChooser(shareIntent, getText(R.string.share_to)));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {}

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });
    }

    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File directory = new File(getActivity().getCacheDir(), "shares");
            if (!directory.mkdirs()) {
                return null;
            }
            File file =  new File(directory, "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(getActivity(), "nl.gramsbergen.oranjevereniging.lichtstad.ShareFileProvider", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
