package com.move4mobile.lichtstad.snapshotparser;

import com.google.firebase.database.DataSnapshot;
import com.move4mobile.lichtstad.model.Album;

public class AlbumSnapshotParser extends KeyedSnapshotParser<Album> {

    public AlbumSnapshotParser() {
        super(Album.class);
    }

    @Override
    public Album parseSnapshot(DataSnapshot snapshot) {
        Album parsed = super.parseSnapshot(snapshot);
        parsed.setYear(snapshot.getRef().getParent().getKey());
        return parsed;
    }
}
