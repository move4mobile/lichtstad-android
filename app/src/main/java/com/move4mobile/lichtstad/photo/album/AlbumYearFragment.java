package com.move4mobile.lichtstad.photo.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentAlbumsYearBinding;
import com.move4mobile.lichtstad.databinding.ItemCountAdapterDataObserver;
import com.move4mobile.lichtstad.model.Album;
import com.move4mobile.lichtstad.snapshotparser.AlbumSnapshotParser;
import com.move4mobile.lichtstad.widget.GridSpacingItemDecoration;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
        binding.setLifecycleOwner(this);

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.card_spacing), true));

        AlbumsYearAdapter adapter = new AlbumsYearAdapter(getAdapterOptions());
        adapter.setAlbumClickListener(this);
        binding.recyclerView.setAdapter(adapter);

        ItemCountAdapterDataObserver adapterDataObserver = new ItemCountAdapterDataObserver(adapter);
        binding.setItemCount(adapterDataObserver);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.getItemCount().cleanup();
        binding = null;
    }

    @Override
    public void onAlbumClick(Album album) {
        getActivity().startActivity(AlbumActivity.newInstanceIntent(getActivity(), album));
    }

    private FirebaseRecyclerOptions<Album> getAdapterOptions() {
        return new FirebaseRecyclerOptions.Builder<Album>()
                .setQuery(getQuery(), new AlbumSnapshotParser())
                .setLifecycleOwner(this)
                .build();
    }

    private Query getQuery() {
        return FirebaseReferences.instance().get("album")
                .child("" + year);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(getResources().getInteger(R.integer.album_span_count), RecyclerView.VERTICAL);
    }
}
