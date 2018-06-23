package com.move4mobile.lichtstad.databinding;

import android.database.DataSetObserver;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;

public class ItemCountPagerAdapterDataObserver extends DataSetObserver implements ItemCountDataObserver {

    private final ObservableInt count = new ObservableInt();
    public final PagerAdapter adapter;

    public ItemCountPagerAdapterDataObserver(PagerAdapter adapter) {
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(this);
    }

    @Override
    public void onChanged() {
        count.set(adapter.getCount());
    }

    @NonNull
    @Override
    public ObservableInt getCount() {
        return count;
    }

    @Override
    public void cleanup() {
        this.adapter.unregisterDataSetObserver(this);
    }
}
