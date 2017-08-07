package com.move4mobile.lichtstad.video;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemVideoBinding;
import com.move4mobile.lichtstad.model.Video;

public class VideosYearAdapter extends FirebaseRecyclerAdapter<Video, VideosYearAdapter.ViewHolder> implements VideoPresenter {

    public VideosYearAdapter(Query ref) {
        super(Video.class, 0, ViewHolder.class, ref);
    }

    private VideoClickListener videoClickListener = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemVideoBinding binding = ListItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Video model, int position) {
        viewHolder.binding.setVideo(model);
        viewHolder.binding.setPresenter(this);
        // Immediately execute the binding, or the StaggeredGridLayoutManager trips
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public void onVideoClick(View view, Video video) {
        if (videoClickListener != null) {
            videoClickListener.onVideoClick(video);
        }
    }

    public VideoClickListener getVideoClickListener() {
        return videoClickListener;
    }

    public void setVideoClickListener(VideoClickListener videoClickListener) {
        this.videoClickListener = videoClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemVideoBinding binding;

        public ViewHolder(ListItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
