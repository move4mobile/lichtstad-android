package com.move4mobile.lichtstad.program;

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

    public ObservableMap<String, Boolean> expandedMap = new ObservableArrayMap<>();

    public ProgramDayAdapter(FirebaseRecyclerOptions<Program> options) {
        super(options);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemProgramBinding binding = ListItemProgramBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, Program model) {
        holder.binding.setProgram(model);
        holder.binding.setPresenter(this);
    }

    @Override
    public ObservableMap<String, Boolean> getExpandedMap() {
        return expandedMap;
    }

    public void onProgramClick(View view, Program program) {
        Boolean wasExpanded = expandedMap.get(program.getKey());
        wasExpanded = wasExpanded == null ? false : wasExpanded;
        //We can't remove the key from the map, as we can not receive which objects were deleted
        //Since the memory leaked is so small, this should not be a problem
        expandedMap.put(program.getKey(), !wasExpanded);

        View animatedParent = view;
        while (animatedParent != null && !(animatedParent instanceof RecyclerView)) {
            animatedParent = (View) animatedParent.getParent();
        }
        if (animatedParent != null) {
            TransitionManager.beginDelayedTransition((ViewGroup) animatedParent);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemProgramBinding binding;

        public ViewHolder(ListItemProgramBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
