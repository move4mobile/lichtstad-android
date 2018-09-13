package com.move4mobile.lichtstad.map;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

public class MarkerDetailFragment extends DialogFragment implements MarkerDetailPresenter {

    private static final String ARG_CONTENT = "content";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.WhiteLabel_Map_Dialog);
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

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = getResources().getDimensionPixelSize(R.dimen.map_marker_popup_width);
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
