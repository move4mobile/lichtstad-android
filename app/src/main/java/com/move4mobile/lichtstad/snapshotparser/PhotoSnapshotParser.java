package com.move4mobile.lichtstad.snapshotparser;

import com.firebase.ui.database.ClassSnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.move4mobile.lichtstad.model.Photo;

public class PhotoSnapshotParser extends ClassSnapshotParser<Photo> {
    public PhotoSnapshotParser() {
        super(Photo.class);
    }

    @Override
    public Photo parseSnapshot(DataSnapshot snapshot) {
        Photo photo = super.parseSnapshot(snapshot);
        photo.setKey(snapshot.getKey());
        return photo;
    }
}
