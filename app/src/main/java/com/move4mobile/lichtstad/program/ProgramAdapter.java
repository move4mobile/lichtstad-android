package com.move4mobile.lichtstad.program;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemProgramBinding;
import com.move4mobile.lichtstad.model.Program;


public class ProgramAdapter extends FirebaseRecyclerAdapter<Program, ProgramAdapter.ViewHolder> {


    public ProgramAdapter(Query ref) {
        super(Program.class, 0, ViewHolder.class, ref);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemProgramBinding binding = ListItemProgramBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Program model, int position) {
        viewHolder.binding.setProgram(model);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemProgramBinding binding;

        public ViewHolder(ListItemProgramBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
