package com.move4mobile.lichtstad.databinding;

import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.RecyclerView;

public class ItemCountAdapterDataObserver extends RecyclerView.AdapterDataObserver {

    public final ObservableInt count = new ObservableInt();
    public final RecyclerView.Adapter adapter;

    public ItemCountAdapterDataObserver(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        this.adapter.registerAdapterDataObserver(this);
        this.count.set(adapter.getItemCount());
    }

    public void cleanup() {
        this.adapter.unregisterAdapterDataObserver(this);
    }

    @Override
    public void onChanged() {
        count.set(adapter.getItemCount());
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        count.set(adapter.getItemCount());
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        count.set(adapter.getItemCount());
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        count.set(adapter.getItemCount());
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        count.set(adapter.getItemCount());
    }
}
