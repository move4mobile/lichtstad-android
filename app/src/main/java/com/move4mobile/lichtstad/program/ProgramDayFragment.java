package com.move4mobile.lichtstad.program;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentProgramDayBinding;
import com.move4mobile.lichtstad.databinding.ItemCountAdapterDataObserver;
import com.move4mobile.lichtstad.model.Program;
import com.move4mobile.lichtstad.snapshotparser.KeyedSnapshotParser;

import java.util.Calendar;

public class ProgramDayFragment extends Fragment {

    private static final String ARG_DAY = "day";
    private FragmentProgramDayBinding binding;

    /**
     * Create a new instance of this class.
     * This should be used instead of the constructor.
     *
     * @param day The day for which to show the program
     * @return an instance of this class to be used with a {@link android.support.v4.app.FragmentManager}.
     */
    public static ProgramDayFragment newInstance(@NonNull Calendar day) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_DAY, day);

        ProgramDayFragment fragment = new ProgramDayFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private Calendar day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.day = (Calendar) getArguments().getSerializable(ARG_DAY);

        if (this.day == null) {
            throw new IllegalStateException("No day");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program_day, container, false);

        ProgramDayAdapter adapter = new ProgramDayAdapter(getAdapterOptions());
        binding.recyclerView.setAdapter(adapter);

        ItemCountAdapterDataObserver adapterDataObserver = new ItemCountAdapterDataObserver(adapter);
        binding.setItemCount(adapterDataObserver);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.getItemCount().cleanup();
        }
        binding = null;
    }


    private FirebaseRecyclerOptions<Program> getAdapterOptions() {
        return new FirebaseRecyclerOptions.Builder<Program>()
                .setQuery(getProgramReference(), new KeyedSnapshotParser<>(Program.class))
                .setLifecycleOwner(this)
                .build();
    }

    /**
     * The reference with the programs to show, ordered by time ascending.
     *
     * It only contains programs that happen on {@link #day}, that is, between {@link #getStartOfDay()}
     * and {@link #getEndOfDay()}
     * @return the reference with the programs to show.
     */
    private Query getProgramReference() {
        return FirebaseReferences.PROGRAM
                .orderByChild("time")
                .startAt((double)getStartOfDay().getTimeInMillis(), "time")
                .endAt((double)getEndOfDay().getTimeInMillis(), "time");
    }

    /**
     * Calculates the start of {@link #day}
     * @return A calendar with the hour, minute, second and millisecond set to the start of {@link #day}
     */
    private Calendar getStartOfDay() {
        Calendar startOfDay = (Calendar) day.clone();
        startOfDay.set(Calendar.HOUR_OF_DAY, 2);
        startOfDay.set(Calendar.MINUTE, 0);
        startOfDay.set(Calendar.SECOND, 0);
        startOfDay.set(Calendar.MILLISECOND, 0);
        return startOfDay;
    }

    /**
     * Calculates the end of {@link #day}
     *
     * This is equal to the last millisecond still in {@link #day}
     * @return A calendar with the hour, minute, second and millisecond set to the end of {@link #day}
     */
    private Calendar getEndOfDay() {
        Calendar endOfDay = (Calendar) getStartOfDay().clone();
        endOfDay.add(Calendar.DAY_OF_MONTH, 1);
        endOfDay.add(Calendar.MILLISECOND, -1);
        return endOfDay;
    }
}
