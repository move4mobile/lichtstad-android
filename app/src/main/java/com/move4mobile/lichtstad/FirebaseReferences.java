package com.move4mobile.lichtstad;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public final class FirebaseReferences {

    private FirebaseReferences(){}

    /**
     * The reference to the database holding the program.
     */
    public static final DatabaseReference PROGRAM = FirebaseDatabase.getInstance().getReference("program");

    /**
     * A collection of all {@link DatabaseReference}s used in this application.
     */
    public static final Collection<DatabaseReference> ALL = Collections.unmodifiableCollection(Arrays.asList(
            PROGRAM
    ));

}
