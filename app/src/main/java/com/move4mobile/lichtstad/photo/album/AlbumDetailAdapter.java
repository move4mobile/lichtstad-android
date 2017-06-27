package com.move4mobile.lichtstad.photo.album;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemPhotoBinding;
import com.move4mobile.lichtstad.model.Photo;
import com.move4mobile.lichtstad.photo.detail.PhotoPresenter;

public class AlbumDetailAdapter extends FirebaseRecyclerAdapter<Photo, AlbumDetailAdapter.ViewHolder> implements PhotoPresenter {

    public AlbumDetailAdapter(Query ref) {
        super(Photo.class, 0, ViewHolder.class, ref);
    }

    private PhotoClickListener photoClickListener = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemPhotoBinding binding = ListItemPhotoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Photo model, int position) {
        viewHolder.binding.setPhoto(model);
        viewHolder.binding.setPresenter(this);
        // Immediately execute the binding, or the StaggeredGridLayoutManager trips
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public void onPhotoClick(View view, Photo photo) {
        if (photoClickListener != null) {
            photoClickListener.onPhotoClick(photo);
        }
    }

    public PhotoClickListener getPhotoClickListener() {
        return photoClickListener;
    }

    public void setPhotoClickListener(PhotoClickListener photoClickListener) {
        this.photoClickListener = photoClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemPhotoBinding binding;

        public ViewHolder(ListItemPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
