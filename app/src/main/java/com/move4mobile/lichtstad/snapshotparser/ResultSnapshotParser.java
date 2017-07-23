package com.move4mobile.lichtstad.snapshotparser;

import com.google.firebase.database.DataSnapshot;
import com.move4mobile.lichtstad.model.Result;

public class ResultSnapshotParser extends KeyedSnapshotParser<Result> {

    public ResultSnapshotParser() {
        super(Result.class);
    }

    @Override
    public Result parseSnapshot(DataSnapshot snapshot) {
        Result result = super.parseSnapshot(snapshot);
        result.setYear(snapshot.getRef().getParent().getKey());
        return result;
    }
}
