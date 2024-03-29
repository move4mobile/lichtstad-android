package com.move4mobile.lichtstad.result;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentResultsYearBinding;
import com.move4mobile.lichtstad.databinding.ItemCountAdapterDataObserver;
import com.move4mobile.lichtstad.databinding.ListItemResultBinding;
import com.move4mobile.lichtstad.model.Result;
import com.move4mobile.lichtstad.snapshotparser.ResultSnapshotParser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ResultYearFragment extends Fragment implements ResultClickListener {

    public static final String BACKSTACK_NAME = "resultYear";

    private static final String ARG_YEAR = "year";
    private static final String ARG_OVERVIEW_KEY = "overview";
    private static final String ARG_CONTENT_KEY = "content";

    public static ResultYearFragment newInstance(@NonNull String overviewReferenceKey, @NonNull String contentReferenceKey, String year) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_YEAR, year);
        arguments.putString(ARG_OVERVIEW_KEY, overviewReferenceKey);
        arguments.putString(ARG_CONTENT_KEY, contentReferenceKey);

        ResultYearFragment fragment = new ResultYearFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentResultsYearBinding binding;

    private String year;
    private String overviewKey;
    private String contentKey;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_YEAR)) {
            throw new IllegalStateException("No year");
        }
        year = getArguments().getString(ARG_YEAR);
        overviewKey = getArguments().getString(ARG_OVERVIEW_KEY);
        contentKey = getArguments().getString(ARG_CONTENT_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results_year, container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());

        binding.recyclerView.setLayoutManager(getLayoutManager());

        ResultsYearAdapter adapter = new ResultsYearAdapter(getAdapterOptions());
        adapter.setResultClickListener(this);
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

    @Override
    public void onResultClick(Result result, ListItemResultBinding binding) {
        if (result.getUrl() != null && getResources().getBoolean(R.bool.open_result_url_in_browser)) {
            Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getUrl()));
            startActivity(openBrowserIntent);
        } else {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                    new Pair<>(binding.card, getString(R.string.transition_name_card)),
                    new Pair<>(binding.title, getString(R.string.transition_name_title))
            );
            getActivity().startActivity(ResultDetailActivity.newInstanceIntent(getActivity(), contentKey, result), options.toBundle());
        }
    }

    private FirebaseRecyclerOptions<Result> getAdapterOptions() {
        return new FirebaseRecyclerOptions.Builder<Result>()
                .setQuery(getQuery(), new ResultSnapshotParser())
                .setLifecycleOwner(this)
                .build();
    }

    private Query getQuery() {
        return FirebaseReferences.instance().get(overviewKey)
                .child(year);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
    }
}
