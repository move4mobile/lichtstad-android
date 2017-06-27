package com.move4mobile.lichtstad.photo.detail;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentPhotoPagerBinding;
import com.move4mobile.lichtstad.model.Album;
import com.move4mobile.lichtstad.model.Photo;
import com.move4mobile.lichtstad.widget.FirebaseViewPagerAdapter;
import com.move4mobile.lichtstad.widget.SinglePageLinearSnapHelper;

/**
 * Created by wilcowolters on 16/05/2017.
 */

public class PhotoPagerFragment extends Fragment {

    private static final String ARG_ALBUM = "ALBUM";
    private static final String ARG_CURRENT_PHOTO = "CURRENT_PHOTO";

    public static PhotoPagerFragment newInstance(Album album, @Nullable Photo currentPhoto) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_ALBUM, album);


        PhotoPagerFragment fragment = new PhotoPagerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentPhotoPagerBinding binding;

    private Album album;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_ALBUM)) {
            throw new IllegalStateException("No album");
        }

        album = getArguments().getParcelable(ARG_ALBUM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_pager, container, false);

        binding.viewPager.setAdapter(new PhotoPagerAdapter(getQuery()));

        return binding.getRoot();
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
}
