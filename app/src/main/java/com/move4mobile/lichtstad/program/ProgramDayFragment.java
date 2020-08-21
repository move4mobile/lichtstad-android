package com.move4mobile.lichtstad.program;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentProgramDayBinding;
import com.move4mobile.lichtstad.databinding.ItemCountAdapterDataObserver;
import com.move4mobile.lichtstad.datasource.FilterableSnapshotArray;
import com.move4mobile.lichtstad.model.Program;
import com.move4mobile.lichtstad.program.favorite.FavoriteManager;
import com.move4mobile.lichtstad.program.favoritenotificationmanager.ProgramFavoriteNotificationManager;
import com.move4mobile.lichtstad.snapshotparser.KeyedSnapshotParser;
import com.move4mobile.lichtstad.util.ApplicationContext;
import com.move4mobile.lichtstad.util.ConfigUtil;
import com.move4mobile.lichtstad.viewmodel.ProgramViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class ProgramDayFragment extends Fragment {

    private static final String ARG_DAY = "day";
    private FragmentProgramDayBinding binding;
    private ProgramDayAdapter adapter;
    private FilterableSnapshotArray<Program> filteredArray;

    /**
     * Create a new instance of this class.
     * This should be used instead of the constructor.
     *
     * @param day The day for which to show the program
     * @return an instance of this class to be used with a {@link FragmentManager}.
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
        binding.setLifecycleOwner(this);

        adapter = new ProgramDayAdapter(getAdapterOptions());
        // Mark the favorites
        // Note that we do not display changes to the favorites immediately,
        // so users can remove favorites and undo their change while still only showing favorites
        for (String favorite : getFavorites()) {
            adapter.getFavoriteMap().put(favorite, true);
        }
        adapter.getFavoriteMap().addOnMapChangedCallback(onFavoriteChangedCallback);
        binding.recyclerView.setAdapter(adapter);

        ItemCountAdapterDataObserver adapterDataObserver = new ItemCountAdapterDataObserver(adapter);
        binding.setItemCount(adapterDataObserver);

        ProgramViewModel viewModel = new ViewModelProvider(getParentFragment()).get(ProgramViewModel.class);
        viewModel.showFavorites.observe(getViewLifecycleOwner(), showFavoritesObserver);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.getItemCount().cleanup();
        }
        if (adapter != null) {
            adapter.getFavoriteMap().removeOnMapChangedCallback(onFavoriteChangedCallback);
        }
        binding = null;
        adapter = null;
        filteredArray = null;
    }


    private FirebaseRecyclerOptions<Program> getAdapterOptions() {
        SnapshotParser<Program> programSnapshotParser = new KeyedSnapshotParser<>(Program.class);
        ObservableSnapshotArray<Program> backingArray = new FirebaseArray<>(getProgramReference(), programSnapshotParser);
        filteredArray = new FilterableSnapshotArray<>(backingArray, programSnapshotParser);
        return new FirebaseRecyclerOptions.Builder<Program>()
                .setSnapshotArray(filteredArray)
                .setLifecycleOwner(this)
                .build();
    }

    /**
     * The reference with the programs to show, ordered by time ascending.
     *
     * It only contains programs that happen on {@link #day}
     * @return the reference with the programs to show.
     */
    private Query getProgramReference() {
        return FirebaseReferences.instance().get("program")
                .child(getDayString())
                .child("programs")
                .orderByChild("time");
    }

    /**
     * @return The selected day formatted as a string to be used in a firebase query
     */
    private String getDayString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        format.setTimeZone(day.getTimeZone());
        return format.format(day.getTime());
    }

    private Collection<String> getFavorites() {
        return FavoriteManager.getAllFavoritesOnDate(getContext(), getDayString());
    }

    private ObservableMap.OnMapChangedCallback<ObservableMap<String, Boolean>, String, Boolean> onFavoriteChangedCallback = new ObservableMap.OnMapChangedCallback<ObservableMap<String, Boolean>, String, Boolean>() {
        @Override
        public void onMapChanged(ObservableMap<String, Boolean> sender, String key) {
            boolean isFavorite = sender.get(key) != null && sender.get(key);
            if (isFavorite) {
                FavoriteManager.registerFavorite(getContext(), getDayString(), key);
            } else {
                FavoriteManager.unregisterFavorite(getContext(), getDayString(), key);
            }
        }
    };

    private FilterableSnapshotArray.Predicate<Program> showFavoritesPredicate = program -> getFavorites().contains(program.getKey());

    private Observer<Boolean> showFavoritesObserver = showFavorites -> filteredArray.setPredicate(showFavorites ? showFavoritesPredicate : null);
}
