package com.move4mobile.lichtstad.photo;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentAlbumsBinding;

public class AlbumsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAlbumsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_albums, container, false);

        getActivity().setActionBar(binding.toolbar);

        return binding.getRoot();
    }

}
