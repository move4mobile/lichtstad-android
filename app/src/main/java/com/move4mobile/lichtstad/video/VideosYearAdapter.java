package com.move4mobile.lichtstad.video;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.move4mobile.lichtstad.databinding.ListItemVideoBinding;
import com.move4mobile.lichtstad.model.Video;
import com.move4mobile.lichtstad.widget.HackedFirebaseRecyclerAdapter;

public class VideosYearAdapter extends HackedFirebaseRecyclerAdapter<Video, VideosYearAdapter.ViewHolder> implements VideoPresenter {

    public VideosYearAdapter(FirebaseRecyclerOptions<Video> options) {
        super(options);
    }

    private VideoClickListener videoClickListener = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemVideoBinding binding = ListItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, Video model) {
        holder.binding.setVideo(model);
        holder.binding.setPresenter(this);
        // Immediately execute the binding, or the StaggeredGridLayoutManager trips
        holder.binding.executePendingBindings();
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
