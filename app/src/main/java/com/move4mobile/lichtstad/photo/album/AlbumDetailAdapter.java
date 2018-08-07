package com.move4mobile.lichtstad.photo.album;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.move4mobile.lichtstad.databinding.ListItemPhotoBinding;
import com.move4mobile.lichtstad.model.Photo;
import com.move4mobile.lichtstad.photo.detail.PhotoPresenter;

public class AlbumDetailAdapter extends FirebaseRecyclerAdapter<Photo, AlbumDetailAdapter.ViewHolder> implements PhotoPresenter {

    private PhotoClickListener photoClickListener = null;

    public AlbumDetailAdapter(FirebaseRecyclerOptions<Photo> options) {
        super(options);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemPhotoBinding binding = ListItemPhotoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, Photo model) {
        holder.binding.setPhoto(model);
        holder.binding.setPresenter(this);
        // Immediately execute the binding, or the StaggeredGridLayoutManager trips
        holder.binding.executePendingBindings();
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
