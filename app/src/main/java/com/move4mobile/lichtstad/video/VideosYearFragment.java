package com.move4mobile.lichtstad.video;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentVideosYearBinding;
import com.move4mobile.lichtstad.model.Video;
import com.move4mobile.lichtstad.widget.GridSpacingItemDecoration;

import java.util.Calendar;

public class VideosYearFragment extends Fragment implements VideoClickListener {

    private static final String ARG_YEAR = "year";
    public static VideosYearFragment newInstance(int year) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_YEAR, year);

        VideosYearFragment fragment = new VideosYearFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentVideosYearBinding binding;

    private int year;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_YEAR)) {
            throw new IllegalStateException("No year");
        }

        this.year = getArguments().getInt(ARG_YEAR);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos_year, container, false);

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.video_spacing), true));

        VideosYearAdapter adapter = new VideosYearAdapter(getQuery());
        adapter.setVideoClickListener(this);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding.recyclerView.getAdapter() instanceof FirebaseRecyclerAdapter) {
            FirebaseRecyclerAdapter adapter = (FirebaseRecyclerAdapter) binding.recyclerView.getAdapter();
            adapter.cleanup();
        }
        binding = null;
    }

    private Query getQuery() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 0, 1, 0, 0, 0);

        long startOfYear = calendar.getTimeInMillis();

        calendar.roll(Calendar.YEAR, 1);
        calendar.roll(Calendar.MILLISECOND, -1);

        long endOfYear = calendar.getTimeInMillis();

        return FirebaseReferences.YOUTUBE
                .orderByChild("date")
                .startAt((double)startOfYear, "date")
                .endAt((double)endOfYear, "date");
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(getResources().getInteger(R.integer.video_span_count), RecyclerView.VERTICAL);
    }

    @Override
    public void onVideoClick(Video video) {
        watchYoutubeVideo(getActivity(), video.id);
    }

    private static void watchYoutubeVideo(Context context, String id){
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id)));
    }
}
