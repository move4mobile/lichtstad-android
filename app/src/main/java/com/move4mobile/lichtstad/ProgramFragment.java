package com.move4mobile.lichtstad;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.FragmentProgramBinding;

public class ProgramFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProgramBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program, container, false);

        Query reference = FirebaseReferences.PROGRAM;
        reference.keepSynced(true);
        binding.recyclerView.setAdapter(new ProgramAdapter(reference));

        return binding.getRoot();
    }
}
