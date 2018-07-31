package com.move4mobile.lichtstad.map;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentMarkerDetailBinding;
import com.move4mobile.lichtstad.model.MarkerContent;
import com.move4mobile.lichtstad.model.MarkerVideo;
import com.move4mobile.lichtstad.util.YoutubePresenter;

public class MarkerDetailFragment extends DialogFragment implements MarkerDetailPresenter {

    private static final String ARG_CONTENT = "content";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_Map_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MarkerContent content = null;
        if (getArguments() != null) {
            content = getArguments().getParcelable(ARG_CONTENT);
        }
        if (content == null) {
            throw new IllegalArgumentException("Missing argument " + ARG_CONTENT);
        }

        FragmentMarkerDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_marker_detail, container, false);

        content.setDescription(content.getDescription().trim());

        binding.setContent(content);
        binding.setPresenter(this);

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP | Gravity.START);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setAttributes(params);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }

    public static MarkerDetailFragment newInstance(MarkerContent content) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CONTENT, content);

        MarkerDetailFragment fragment = new MarkerDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onVideoClick(MarkerVideo video) {
        YoutubePresenter.watchYoutubeVideo(getContext(), video.getId());
    }
}
