package com.move4mobile.lichtstad.video;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemVideoBinding;
import com.move4mobile.lichtstad.model.Video;

public class VideosAdapter extends FirebaseRecyclerAdapter<Video, VideosAdapter.ViewHolder> {

    public VideosAdapter(Query ref) {
        super(Video.class, 0, ViewHolder.class, ref);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemVideoBinding binding = ListItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Video model, int position) {
        viewHolder.binding.setVideo(model);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemVideoBinding binding;

        public ViewHolder(ListItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
