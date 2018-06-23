package com.move4mobile.lichtstad.photo.album;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentAlbumDetailBinding;
import com.move4mobile.lichtstad.databinding.ItemCountAdapterDataObserver;
import com.move4mobile.lichtstad.databinding.ItemCountDataObserver;
import com.move4mobile.lichtstad.model.Album;
import com.move4mobile.lichtstad.model.Photo;
import com.move4mobile.lichtstad.photo.detail.PhotoViewActivity;
import com.move4mobile.lichtstad.snapshotparser.KeyedSnapshotParser;
import com.move4mobile.lichtstad.widget.GridSpacingItemDecoration;

public class AlbumDetailFragment extends Fragment implements PhotoClickListener {

    private static final String ARG_ALBUM = "ALBUM";

    public static AlbumDetailFragment newInstance(Album album) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_ALBUM, album);

        AlbumDetailFragment fragment = new AlbumDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentAlbumDetailBinding binding;

    private Album album;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_ALBUM)) {
            throw new IllegalStateException("No album");
        }

        this.album = getArguments().getParcelable(ARG_ALBUM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_detail, container, false);

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.photo_spacing), false));

        AlbumDetailAdapter adapter = new AlbumDetailAdapter(getAdapterOptions());
        adapter.setPhotoClickListener(this);
        binding.recyclerView.setAdapter(adapter);

        ItemCountDataObserver adapterDataObserver = new ItemCountAdapterDataObserver(adapter);
        binding.setItemCount(adapterDataObserver);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().setTitle(album.getTitle());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPhotoClick(Photo photo) {
        getActivity().startActivity(PhotoViewActivity.newInstanceIntent(getActivity(), album, photo));
    }

    private FirebaseRecyclerOptions<Photo> getAdapterOptions() {
        return new FirebaseRecyclerOptions.Builder<Photo>()
                .setQuery(getQuery(), new KeyedSnapshotParser<>(Photo.class))
                .setLifecycleOwner(this)
                .build();
    }

    private Query getQuery() {
        return FirebaseReferences.ALBUM_CONTENT
                .child(album.getYear())
                .child(album.getKey())
                .orderByChild("order");
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.photo_span_count));
    }
}
