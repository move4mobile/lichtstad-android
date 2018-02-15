package com.move4mobile.lichtstad.result;

import android.app.ActivityOptions;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class ResultYearFragment extends Fragment implements ResultClickListener {

    public static final String BACKSTACK_NAME = "resultYear";

    private static final String ARG_YEAR = "year";

    public static ResultYearFragment newInstance(int year) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_YEAR, year);

        ResultYearFragment fragment = new ResultYearFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentResultsYearBinding binding;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results_year, container, false);

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
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                new Pair<>(binding.card, getString(R.string.transition_name_card)),
                new Pair<>(binding.title, getString(R.string.transition_name_title))
        );
        getActivity().startActivity(ResultDetailActivity.newInstanceIntent(getActivity(), result), options.toBundle());
    }

    private FirebaseRecyclerOptions<Result> getAdapterOptions() {
        return new FirebaseRecyclerOptions.Builder<Result>()
                .setQuery(getQuery(), new ResultSnapshotParser())
                .setLifecycleOwner(this)
                .build();
    }

    private Query getQuery() {
        return FirebaseReferences.RESULT
                .child("" + year);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
    }
}
