package com.move4mobile.lichtstad.result;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemResultBinding;
import com.move4mobile.lichtstad.model.Result;

public class ResultsYearAdapter extends FirebaseRecyclerAdapter<Result, ResultsYearAdapter.ViewHolder> implements ResultPresenter {

    public ResultsYearAdapter(Query ref) {
        super(Result.class, 0, ViewHolder.class, ref);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemResultBinding binding = ListItemResultBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Result model, int position) {
        viewHolder.binding.setResult(model);
        viewHolder.binding.setPresenter(this);
    }

    @Override
    public void onResultClick(View view, Result result) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final ListItemResultBinding binding;

        ViewHolder(ListItemResultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
