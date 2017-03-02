package com.move4mobile.lichtstad.video;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentVideosBinding;
import com.move4mobile.lichtstad.widget.GridSpacingItemDecoration;

public class VideosFragment extends Fragment {

    private FragmentVideosBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false);

        getActivity().setActionBar(binding.toolbar);

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.video_spacing), true));
        binding.recyclerView.setAdapter(new VideosAdapter(FirebaseReferences.YOUTUBE));

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

    private RecyclerView.LayoutManager getLayoutManager() {
        //GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.video_span_count));
        //return layoutManager;
        return new StaggeredGridLayoutManager(getResources().getInteger(R.integer.video_span_count), RecyclerView.VERTICAL);
    }

}
