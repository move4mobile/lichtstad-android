package com.move4mobile.lichtstad.snapshotparser;

import com.firebase.ui.database.ClassSnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.move4mobile.lichtstad.model.Program;

public class ProgramSnapshotParser extends ClassSnapshotParser<Program> {

    public ProgramSnapshotParser() {
        super(Program.class);
    }

    @Override
    public Program parseSnapshot(DataSnapshot snapshot) {
        Program program = super.parseSnapshot(snapshot);
        program.setKey(snapshot.getKey());
        return program;
    }
}
