package com.move4mobile.lichtstad.program;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.BaseContentFragment;
import com.move4mobile.lichtstad.BuildConfig;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentProgramBinding;
import com.move4mobile.lichtstad.databinding.ItemCountPagerAdapterDataObserver;
import com.move4mobile.lichtstad.snapshotparser.DateSnapshotParser;

import java.util.Calendar;

public class ProgramFragment extends BaseContentFragment implements ChangeEventListener {

    private static String STATE_SET_SELECTED_DAY = "setSelectedDay";

    private boolean needsSetSelectedDay = true;
    private ObservableSnapshotArray<Calendar> tabDays;

    private FragmentProgramBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program, container, false);

        FirebaseRecyclerOptions<Calendar> adapterOptions = getAdapterOptions();
        tabDays = adapterOptions.getSnapshots();
        PagerAdapter adapter = new ProgramPagerAdapter(getContext(), getChildFragmentManager(), adapterOptions);
        binding.component.viewPager.setAdapter(adapter);
        binding.component.tabLayout.setupWithViewPager(binding.component.viewPager);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.component.toolbar.toolbar);

        ItemCountPagerAdapterDataObserver adapterDataObserver = new ItemCountPagerAdapterDataObserver(adapter);
        binding.setItemCount(adapterDataObserver);

        if (savedInstanceState != null) {
            needsSetSelectedDay = savedInstanceState.getBoolean(STATE_SET_SELECTED_DAY);
        }

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (needsSetSelectedDay) {
            tabDays.addChangeEventListener(this);
        }
    }

    @Override
    public void onStop() {
        tabDays.removeChangeEventListener(this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_SET_SELECTED_DAY, needsSetSelectedDay);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.getItemCount().cleanup();
        binding = null;
    }

    /**
     * Queries the database for the days in the last known year.
     */
    private FirebaseRecyclerOptions<Calendar> getAdapterOptions() {
        return new FirebaseRecyclerOptions.Builder<Calendar>()
                .setQuery(getDaysReference(), new DateSnapshotParser(getString(R.string.date_format)))
                .setLifecycleOwner(this)
                .build();
    }

    @NonNull
    private Query getDaysReference() {
        return FirebaseReferences.PROGRAM.child(getString(R.string.program_year));
    }

    private Calendar getToday() {
        Calendar today = Calendar.getInstance(BuildConfig.EVENT_TIMEZONE);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    @Override
    public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {
        if (needsSetSelectedDay && type == ChangeEventType.ADDED || type == ChangeEventType.CHANGED) {
            if (getToday().equals(tabDays.get(newIndex))) {
                binding.component.viewPager.setCurrentItem(newIndex);
                needsSetSelectedDay = false;
            }
        }
    }

    @Override
    public void onDataChanged() {

    }

    @Override
    public void onError(@NonNull DatabaseError error) {

    }
}
