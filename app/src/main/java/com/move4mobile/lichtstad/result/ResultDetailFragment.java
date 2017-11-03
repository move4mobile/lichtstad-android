package com.move4mobile.lichtstad.result;

import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.ClassSnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentResultDetailBinding;
import com.move4mobile.lichtstad.model.Result;
import com.move4mobile.lichtstad.model.ResultContent;

public class ResultDetailFragment extends Fragment implements ValueEventListener, ResultDetailPresenter {

    private static final String ARG_RESULT = "RESULT";

    public static ResultDetailFragment newInstance(Result result) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_RESULT, result);

        ResultDetailFragment fragment = new ResultDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentResultDetailBinding binding;
    private Result result;
    private Query query;
    private final ClassSnapshotParser<ResultContent> resultSnapshotParser = new ClassSnapshotParser<>(ResultContent.class);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_RESULT)) {
            throw new IllegalStateException("No result");
        }

        this.result = getArguments().getParcelable(ARG_RESULT);
        this.query = getQuery();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_detail, container, false);

        binding.setPresenter(this);
        binding.setResult(result);

        query.addValueEventListener(this);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        query.removeEventListener(this);
        binding = null;
    }

    private Query getQuery() {
        return FirebaseReferences.RESULT_CONTENT
                .child(result.getYear())
                .child(result.getKey());
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (binding != null) {
            binding.setContent(resultSnapshotParser.parseSnapshot(dataSnapshot));
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (binding != null) {
            binding.setContent(null);
        }
    }

    @Override
    public void onBackgroundClicked() {
        onCloseClicked();
    }

    @Override
    public void onCloseClicked() {
        getActivity().finish();
    }
}
