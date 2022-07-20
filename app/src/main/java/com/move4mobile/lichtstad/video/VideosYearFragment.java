package com.move4mobile.lichtstad.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentVideosYearBinding;
import com.move4mobile.lichtstad.databinding.ItemCountAdapterDataObserver;
import com.move4mobile.lichtstad.model.Video;
import com.move4mobile.lichtstad.util.YoutubePresenter;
import com.move4mobile.lichtstad.widget.GridSpacingItemDecoration;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class VideosYearFragment extends Fragment implements VideoClickListener {

    private static final String ARG_YEAR = "year";
    public static VideosYearFragment newInstance(String year) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_YEAR, year);

        VideosYearFragment fragment = new VideosYearFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentVideosYearBinding binding;

    private String year;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_YEAR)) {
            throw new IllegalStateException("No year");
        }

        year = getArguments().getString(ARG_YEAR);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos_year, container, false);
        binding.setLifecycleOwner(this);

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.card_spacing), true));

        VideosYearAdapter adapter = new VideosYearAdapter(getAdapterOptions());
        adapter.setVideoClickListener(this);
        binding.recyclerView.setAdapter(adapter);

        ItemCountAdapterDataObserver adapterDataObserver = new ItemCountAdapterDataObserver(adapter);
        binding.setItemCount(adapterDataObserver);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.getItemCount().cleanup();
        binding = null;
    }

    private FirebaseRecyclerOptions<Video> getAdapterOptions() {
        return new FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(getQuery(), Video.class)
                .setLifecycleOwner(this)
                .build();
    }

    private Query getQuery() {
        return FirebaseReferences.instance().get("youtube")
                .child(year)
                .orderByChild("date");
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(getResources().getInteger(R.integer.video_span_count), RecyclerView.VERTICAL);
    }

    @Override
    public void onVideoClick(Video video) {
        YoutubePresenter.watchYoutubeVideo(getActivity(), video.getId());
    }

}
