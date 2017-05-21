package com.move4mobile.lichtstad.photo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemPhotoBinding;
import com.move4mobile.lichtstad.databinding.ListItemPhotoDetailBinding;
import com.move4mobile.lichtstad.model.Photo;

/**
 * Created by wilcowolters on 16/05/2017.
 */

public class PhotoViewAdapter extends FirebaseRecyclerAdapter<Photo, PhotoViewAdapter.ViewHolder> {

    public PhotoViewAdapter(Query ref) {
        super(Photo.class, 0, ViewHolder.class, ref);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemPhotoDetailBinding binding = ListItemPhotoDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Photo model, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemPhotoDetailBinding binding;

        public ViewHolder(ListItemPhotoDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
