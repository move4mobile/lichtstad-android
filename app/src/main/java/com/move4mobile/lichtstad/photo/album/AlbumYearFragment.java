package com.move4mobile.lichtstad.photo.album;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentAlbumsYearBinding;
import com.move4mobile.lichtstad.databinding.ItemCountAdapterDataObserver;
import com.move4mobile.lichtstad.model.Album;
import com.move4mobile.lichtstad.widget.GridSpacingItemDecoration;

public class AlbumYearFragment extends Fragment implements AlbumClickListener {

    private static final String ARG_YEAR = "year";

    public static AlbumYearFragment newInstance(int year) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_YEAR, year);

        AlbumYearFragment fragment = new AlbumYearFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentAlbumsYearBinding binding;

    private int year;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_YEAR)) {
            throw new IllegalStateException("No year");
        }

        this.year = getArguments().getInt(ARG_YEAR);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_albums_year, container, false);

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.card_spacing), true));

        AlbumsYearAdapter adapter = new AlbumsYearAdapter(getQuery());
        adapter.setAlbumClickListener(this);
        binding.recyclerView.setAdapter(adapter);

        ItemCountAdapterDataObserver adapterDataObserver = new ItemCountAdapterDataObserver(adapter);
        binding.setItemCount(adapterDataObserver);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding.recyclerView.getAdapter() instanceof FirebaseRecyclerAdapter) {
            FirebaseRecyclerAdapter adapter = (FirebaseRecyclerAdapter) binding.recyclerView.getAdapter();
            adapter.cleanup();
            binding.getItemCount().cleanup();
        }
        binding = null;
    }

    @Override
    public void onAlbumClick(Album album) {
        getActivity().startActivity(AlbumActivity.newInstanceIntent(getActivity(), album));
    }

    private Query getQuery() {
        return FirebaseReferences.ALBUM
                .child("" + year);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(getResources().getInteger(R.integer.album_span_count), RecyclerView.VERTICAL);
    }
}
