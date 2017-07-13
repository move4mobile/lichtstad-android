package com.move4mobile.lichtstad.program;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemProgramBinding;
import com.move4mobile.lichtstad.model.Program;
import com.move4mobile.lichtstad.snapshotparser.ProgramSnapshotParser;


public class ProgramDayAdapter extends FirebaseRecyclerAdapter<Program, ProgramDayAdapter.ViewHolder> implements ProgramPresenter {

    public ObservableMap<String, Boolean> expandedMap = new ObservableArrayMap<>();

    public ProgramDayAdapter(Query ref) {
        super(new ProgramSnapshotParser(), 0, ViewHolder.class, ref);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemProgramBinding binding = ListItemProgramBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Program model, int position) {
        viewHolder.binding.setProgram(model);
        viewHolder.binding.setPresenter(this);
    }

    @Override
    public ObservableMap<String, Boolean> getExpandedMap() {
        return expandedMap;
    }

    public void onProgramClick(View view, Program program) {
        Boolean wasExpanded = expandedMap.get(program.key);
        wasExpanded = wasExpanded == null ? false : wasExpanded;
        //We can't remove the key from the map, as we can not receive which objects were deleted
        //Since the memory leaked is so small, this should not be a problem
        expandedMap.put(program.key, !wasExpanded);

        TransitionManager.beginDelayedTransition((ViewGroup) view.getParent());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemProgramBinding binding;

        public ViewHolder(ListItemProgramBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
