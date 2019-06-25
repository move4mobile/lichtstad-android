package com.move4mobile.lichtstad.datasource;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FilterableSnapshotArray<T> extends ObservableSnapshotArray<T> {

    private Predicate<? super T> predicate = Predicate.TRUE;
    private final ObservableSnapshotArray<T> backingArray;
    /**
     * The map of backing index to our index.
     * Null if filtered out.
     */
    private final List<Integer> internalIndices = new ArrayList<>();

    /**
     * Create an ObservableSnapshotArray with a custom {@link SnapshotParser}.
     *
     * @param parser the {@link SnapshotParser} to use
     */
    public FilterableSnapshotArray(@NonNull ObservableSnapshotArray<T> backingArray, @NonNull SnapshotParser<T> parser) {
        super(parser);
        this.backingArray = backingArray;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        backingArray.addChangeEventListener(backingChangeEventListener);
    }

    @Override
    protected void onDestroy() {
        backingArray.removeChangeEventListener(backingChangeEventListener);
        internalIndices.clear();
        super.onDestroy();
    }

    @NonNull
    @Override
    protected List<DataSnapshot> getSnapshots() {
        List<DataSnapshot> snapshots = new ArrayList<>();
        for (int i = 0; i < backingArray.size(); i++) {
            if (predicate.test(backingArray.get(i))) {
                snapshots.add(backingArray.getSnapshot(i));
            }
        }
        return snapshots;
    }

    @Override
    public int size() {
        int count = 0;
        for (Integer internalIndex : internalIndices) {
            if (internalIndex != null) {
                count++;
            }
        }
        return count;
    }

    @NonNull
    @Override
    public DataSnapshot getSnapshot(int index) {
        return backingArray.getSnapshot(internalIndices.indexOf(index));
    }

    @NonNull
    public Predicate<? super T> getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate<? super T> predicate) {
        this.predicate = predicate != null ? predicate : Predicate.TRUE;
        retest();
    }

    private void retest() {
        for (int i = 0; i < backingArray.size(); i++) {
            backingChangeEventListener.onChildChanged(ChangeEventType.CHANGED, backingArray.getSnapshot(i), i, i);
        }
    }

    private final ChangeEventListener backingChangeEventListener = new ChangeEventListener() {
        @Override
        public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {
            switch (type) {
                case ADDED: {
                    boolean shouldInclude = predicate.test(backingArray.get(newIndex));
                    Integer internalIndex = shouldInclude ? findInsertIndex(newIndex) : null;
                    addIndex(newIndex, internalIndex);
                    if (shouldInclude) {
                        notifyOnChildChanged(ChangeEventType.ADDED, snapshot, internalIndex, -1);
                    }
                }
                    break;
                case CHANGED: {
                    Integer thisIndex = internalIndices.get(newIndex);
                    boolean shouldInclude = predicate.test(backingArray.get(newIndex));
                    if (thisIndex != null) {
                        if (shouldInclude) {
                            notifyOnChildChanged(ChangeEventType.CHANGED, snapshot, thisIndex, thisIndex);
                        } else {
                            internalIndices.set(newIndex, null);
                            updateAfterIndexChanged(newIndex, false);
                            notifyOnChildChanged(ChangeEventType.REMOVED, snapshot, thisIndex, -1);
                        }
                    } else if (shouldInclude) {
                        int insertedIndex = findInsertIndex(newIndex);
                        internalIndices.set(newIndex, insertedIndex);
                        updateAfterIndexChanged(newIndex, true);
                        notifyOnChildChanged(ChangeEventType.ADDED, snapshot, insertedIndex, -1);
                    }
                    // Else the value was filtered and is still filtered, so do nothing
                }
                    break;
                case REMOVED:
                    Integer thisIndex = internalIndices.get(newIndex);
                    removeIndex(newIndex);
                    if (thisIndex != null) {
                        notifyOnChildChanged(ChangeEventType.REMOVED, snapshot, thisIndex, -1);
                    }
                    break;
                case MOVED:
                    Integer currentInternalIndex = internalIndices.get(oldIndex);
                    removeIndex(oldIndex);
                    if (currentInternalIndex != null) {
                        int internalIndex = findInsertIndex(newIndex);
                        addIndex(newIndex, internalIndex);
                        if (currentInternalIndex != internalIndex) {
                            notifyOnChildChanged(ChangeEventType.MOVED, snapshot, internalIndex, currentInternalIndex);
                        }
                    } else {
                        addIndex(newIndex, null);
                    }
                    break;
            }
        }

        @Override
        public void onDataChanged() {
            notifyOnDataChanged();
        }

        @Override
        public void onError(@NonNull DatabaseError databaseError) {
            notifyOnError(databaseError);
        }
    };

    /**
     * Find the index at which an item at backingIndex should be inserted
     */
    private int findInsertIndex(int backingIndex) {
        int insertIndex = -1;
        for (int i = 0; i < backingIndex; i++) {
            if (internalIndices.get(i) != null) {
                insertIndex = internalIndices.get(i);
            }
        }
        return insertIndex + 1;
    }

    private void addIndex(int backingIndex, Integer internalIndex) {
        internalIndices.add(backingIndex, internalIndex);
        if (internalIndex != null) {
            updateAfterIndexChanged(backingIndex, true);
        }
    }

    private void removeIndex(int backingIndex) {
        Integer removedIndex = internalIndices.remove(backingIndex);
        if (removedIndex != null) {
            updateAfterIndexChanged(backingIndex, false);
        }
    }

    /**
     * Update the internal indices to their new value after the filtered values changed
     */
    private void updateAfterIndexChanged(int backingIndex, boolean added) {
        for (int i = added ? backingIndex + 1 : backingIndex; i < internalIndices.size(); i++) {
            Integer valueAt = internalIndices.get(i);
            if (valueAt != null) {
                int newValue = added ? valueAt + 1 : valueAt - 1;
                internalIndices.set(i, newValue);
            }
        }
    }

    public interface Predicate<T> {

        /**
         * Whether the object should be in the filtered array.
         * Should be consistent.
         */
        boolean test(T t);

        Predicate<Object> TRUE = t -> true;

    }

}
