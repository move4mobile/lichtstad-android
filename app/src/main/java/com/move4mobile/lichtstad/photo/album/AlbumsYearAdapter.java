package com.move4mobile.lichtstad.photo.album;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.move4mobile.lichtstad.databinding.ListItemAlbumBinding;
import com.move4mobile.lichtstad.model.Album;

public class AlbumsYearAdapter extends FirebaseRecyclerAdapter<Album, AlbumsYearAdapter.ViewHolder> implements AlbumPresenter {

    private AlbumClickListener albumClickListener;

    public AlbumsYearAdapter(FirebaseRecyclerOptions<Album> options) {
        super(options);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemAlbumBinding binding = ListItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, Album model) {
        holder.binding.setAlbum(model);
        holder.binding.setPresenter(this);
        // Immediately execute the binding, or the StaggeredGridLayoutManager trips
        holder.binding.executePendingBindings();
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

        final ListItemAlbumBinding binding;

        ViewHolder(ListItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
