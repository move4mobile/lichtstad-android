package com.move4mobile.lichtstad.program;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableMap;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.move4mobile.lichtstad.databinding.ListItemProgramBinding;
import com.move4mobile.lichtstad.model.Program;


public class ProgramDayAdapter extends FirebaseRecyclerAdapter<Program, ProgramDayAdapter.ViewHolder> implements ProgramPresenter {

    private ObservableMap<String, Boolean> expandedMap = new ObservableArrayMap<>();
    private ObservableMap<String, Boolean> favoriteMap = new ObservableArrayMap<>();
    private FavoriteChangedListener favoriteChangedListener;

    ProgramDayAdapter(FirebaseRecyclerOptions<Program> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProgramBinding binding = ListItemProgramBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Program model) {
        holder.binding.setProgram(model);
        holder.binding.setPresenter(this);
    }

    @Override
    public ObservableMap<String, Boolean> getExpandedMap() {
        return expandedMap;
    }

    @Override
    public ObservableMap<String, Boolean> getFavoriteMap() {
        return favoriteMap;
    }

    public void onProgramClick(View view, Program program) {
        Boolean wasExpanded = expandedMap.get(program.getKey());
        wasExpanded = wasExpanded == null ? false : wasExpanded;
        // We can't remove the key from the map when the object is removed, as we do not receive these events
        // Since the memory leaked is so small, this should not be a problem
        expandedMap.put(program.getKey(), !wasExpanded);

        View animatedParent = view;
        while (animatedParent != null && !(animatedParent instanceof RecyclerView)) {
            animatedParent = (View) animatedParent.getParent();
        }
        if (animatedParent != null) {
            TransitionManager.beginDelayedTransition((ViewGroup) animatedParent);
        }
    }

    @Override
    public void onFavoriteClick(Program program) {
        Boolean wasFavorite = favoriteMap.get(program.getKey());
        wasFavorite = wasFavorite == null ? false : wasFavorite;
        favoriteMap.put(program.getKey(), !wasFavorite);
        if (favoriteChangedListener != null) {
            favoriteChangedListener.onFavoriteChanged(program, !wasFavorite);
        }
    }

    public FavoriteChangedListener getFavoriteChangedListener() {
        return favoriteChangedListener;
    }

    public void setFavoriteChangedListener(FavoriteChangedListener favoriteChangedListener) {
        this.favoriteChangedListener = favoriteChangedListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemProgramBinding binding;

        ViewHolder(ListItemProgramBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
