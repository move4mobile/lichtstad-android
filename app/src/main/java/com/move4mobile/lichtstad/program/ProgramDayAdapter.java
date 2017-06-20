package com.move4mobile.lichtstad.program;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;
import android.databinding.OnRebindCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemProgramBinding;
import com.move4mobile.lichtstad.databinding.RecyclerTransitionRebindCallback;
import com.move4mobile.lichtstad.model.Program;
import com.move4mobile.lichtstad.snapshotparser.ProgramSnapshotParser;


public class ProgramDayAdapter extends FirebaseRecyclerAdapter<Program, ProgramDayAdapter.ViewHolder> implements ProgramPresenter {

    private final OnRebindCallback rebindCallback = new RecyclerTransitionRebindCallback();

    public ObservableMap<String, Boolean> expandedMap = new ObservableArrayMap<>();

    public ProgramDayAdapter(Query ref) {
        super(new ProgramSnapshotParser(), 0, ViewHolder.class, ref);
        this.registerAdapterDataObserver(new PresenterDataObserver());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemProgramBinding binding = ListItemProgramBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.addOnRebindCallback(rebindCallback);
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
        expandedMap.put(program.key, !wasExpanded);
    }

    void onProgramRemoved(Program program) {
        expandedMap.remove(program.key);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemProgramBinding binding;

        public ViewHolder(ListItemProgramBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class PresenterDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                onProgramRemoved(getItem(i));
            }
        }
    }

}
