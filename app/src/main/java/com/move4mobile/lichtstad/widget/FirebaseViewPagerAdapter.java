package com.move4mobile.lichtstad.widget;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.move4mobile.lichtstad.R;


public abstract class FirebaseViewPagerAdapter<T> extends PagerAdapter implements ChangeEventListener, LifecycleObserver {
    private static final String TAG = FirebaseViewPagerAdapter.class.getSimpleName();
    private static final int VIEW_TAG = R.string.app_name;

    protected final ObservableSnapshotArray<T> mSnapshots;

    public FirebaseViewPagerAdapter(@NonNull FirebaseRecyclerOptions<T> options) {
        mSnapshots = options.getSnapshots();

        if (options.getOwner() != null) {
            options.getOwner().getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startListening() {
        if (!mSnapshots.isListening(this)) {
            mSnapshots.addChangeEventListener(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopListening() {
        mSnapshots.removeChangeEventListener(this);
        notifyDataSetChanged();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void cleanup(LifecycleOwner source) {
        source.getLifecycle().removeObserver(this);
    }

    public T getItem(int position) {
        return mSnapshots.get(position);
    }

    @Override
    public void onChildChanged(ChangeEventType type, DataSnapshot snapshot, int index, int oldIndex) {
        notifyDataSetChanged();
    }

    @Override
    public void onDataChanged() {
    }

    @Override
    public void onError(DatabaseError error) {
        Log.w(TAG, error.toException());
    }

    protected abstract View getView(T object, ViewGroup container);

    @Override
    public int getCount() {
        return mSnapshots.size();
    }

    @Override
    public int getItemPosition(Object object) {
        View view = (View) object;
        for (int i = 0; i < mSnapshots.size(); i++) {
            if (mSnapshots.getSnapshot(i).getKey().equals(view.getTag(VIEW_TAG))) {
                return i;
            }
        }
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(mSnapshots.get(position), container);

        view.setTag(VIEW_TAG, mSnapshots.getSnapshot(position).getKey());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
