package com.move4mobile.lichtstad.snapshotparser;

import com.firebase.ui.database.ClassSnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.move4mobile.lichtstad.model.Album;

public class AlbumSnapshotParser extends ClassSnapshotParser<Album> {

    public AlbumSnapshotParser() {
        super(Album.class);
    }

    @Override
    public Album parseSnapshot(DataSnapshot snapshot) {
        Album album = super.parseSnapshot(snapshot);
        album.setKey(snapshot.getKey());
        album.setYear(snapshot.getRef().getParent().getKey());
        return album;
    }
}
