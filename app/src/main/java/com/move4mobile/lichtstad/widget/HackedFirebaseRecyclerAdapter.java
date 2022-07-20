package com.move4mobile.lichtstad.widget;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.move4mobile.lichtstad.model.Result;
import com.move4mobile.lichtstad.result.ResultsYearAdapter;

public abstract class HackedFirebaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends FirebaseRecyclerAdapter<T, VH> {
    public HackedFirebaseRecyclerAdapter(FirebaseRecyclerOptions<T> options) {
        super(options);
    }

    @Override
    public void stopListening() {
        // For whatever reason, when we stop listening, and then start listening later,
        // an ADD event is raised for every item, even though they were never removed.
        // This fix will probably break things when this bug is fixed
        notifyItemRangeRemoved(0, getItemCount());
        super.stopListening();
    }
}
