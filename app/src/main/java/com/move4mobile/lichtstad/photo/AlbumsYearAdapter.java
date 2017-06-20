package com.move4mobile.lichtstad.photo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemAlbumBinding;
import com.move4mobile.lichtstad.model.Album;
import com.move4mobile.lichtstad.snapshotparser.AlbumSnapshotParser;

public class AlbumsYearAdapter extends FirebaseRecyclerAdapter<Album, AlbumsYearAdapter.ViewHolder> implements AlbumPresenter {

    public AlbumsYearAdapter(Query ref) {
        super(new AlbumSnapshotParser(), 0, ViewHolder.class, ref);
    }

    private AlbumClickListener albumClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemAlbumBinding binding = ListItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Album model, int position) {
        viewHolder.binding.setAlbum(model);
        viewHolder.binding.setPresenter(this);
        // Immediately execute the binding, or the StaggeredGridLayoutManager trips
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public void onAlbumClick(View view, Album album) {
        if (albumClickListener != null) {
            albumClickListener.onAlbumClick(album);
        }
    }

    public AlbumClickListener getAlbumClickListener() {
        return albumClickListener;
    }

    public void setAlbumClickListener(AlbumClickListener albumClickListener) {
        this.albumClickListener = albumClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemAlbumBinding binding;

        public ViewHolder(ListItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
