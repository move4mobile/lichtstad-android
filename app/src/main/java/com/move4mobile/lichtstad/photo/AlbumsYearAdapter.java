package com.move4mobile.lichtstad.photo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemAlbumBinding;
import com.move4mobile.lichtstad.model.Album;

public class AlbumsYearAdapter extends FirebaseRecyclerAdapter<Album, AlbumsYearAdapter.ViewHolder> {

    public AlbumsYearAdapter(Query ref) {
        super(Album.class, 0, ViewHolder.class, ref);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemAlbumBinding binding = ListItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Album model, int position) {
        viewHolder.binding.setAlbum(model);
        // Immediately execute the binding, or the StaggeredGridLayoutManager trips
        viewHolder.binding.executePendingBindings();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemAlbumBinding binding;

        public ViewHolder(ListItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
