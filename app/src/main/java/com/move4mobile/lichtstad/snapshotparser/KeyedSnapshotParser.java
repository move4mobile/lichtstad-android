package com.move4mobile.lichtstad.snapshotparser;

import android.support.annotation.NonNull;

import com.firebase.ui.database.ClassSnapshotParser;
import com.google.firebase.database.DataSnapshot;

public class KeyedSnapshotParser<T extends Keyed> extends ClassSnapshotParser<T> {

    public KeyedSnapshotParser(@NonNull Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T parseSnapshot(DataSnapshot snapshot) {
        T parsed = super.parseSnapshot(snapshot);
        parsed.setKey(snapshot.getKey());
        return parsed;
    }
}
