package com.move4mobile.lichtstad.widget;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.R;


public abstract class FirebaseViewPagerAdapter<T> extends PagerAdapter implements ChangeEventListener {
    private static final String TAG = FirebaseViewPagerAdapter.class.getSimpleName();
    private static final int VIEW_TAG = R.string.app_name;

    protected final ObservableSnapshotArray<T> mSnapshots;

    public FirebaseViewPagerAdapter(ObservableSnapshotArray<T> snapshots) {
        mSnapshots = snapshots;

        startListening();
    }

    public FirebaseViewPagerAdapter(SnapshotParser<T> parser,
                                    Query query) {
        this(new FirebaseArray<>(query, parser));
    }

    public FirebaseViewPagerAdapter(Class<T> modelClass,
                                    Query query) {
        this(new ClassSnapshotParser<>(modelClass), query);
    }

    public void startListening() {
        if (!mSnapshots.isListening(this)) {
            mSnapshots.addChangeEventListener(this);
        }
    }

    public void cleanup() {
        mSnapshots.removeChangeEventListener(this);
    }

    public T getItem(int position) {
        return mSnapshots.getObject(position);
    }

    public DatabaseReference getRef(int position) {
        return mSnapshots.get(position).getRef();
    }

    @Override
    public void onChildChanged(EventType type, DataSnapshot snapshot, int index, int oldIndex) {
        notifyDataSetChanged();
    }

    @Override
    public void onDataChanged() {
    }

    @Override
    public void onCancelled(DatabaseError error) {
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
            if (mSnapshots.get(i).getKey().equals(view.getTag(VIEW_TAG))) {
                return i;
            }
        }
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(mSnapshots.getObject(position), container);

        view.setTag(VIEW_TAG, mSnapshots.get(position).getKey());

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
