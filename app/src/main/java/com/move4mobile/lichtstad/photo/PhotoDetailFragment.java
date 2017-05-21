package com.move4mobile.lichtstad.photo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.move4mobile.lichtstad.databinding.FragmentAlbumDetailBinding;
import com.move4mobile.lichtstad.model.Album;
import com.move4mobile.lichtstad.model.Photo;

/**
 * Created by wilcowolters on 16/05/2017.
 */

public class PhotoDetailFragment extends Fragment {

    private static final String ARG_ALBUM = "ALBUM";
    private static final String ARG_CURRENT_PHOTO = "CURRENT_PHOTO";

    public static PhotoDetailFragment newInstance(Album album, @Nullable Photo currentPhoto) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_ALBUM, album);


        PhotoDetailFragment fragment = new PhotoDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentAlbumDetailBinding binding;

    private Album album;

}
