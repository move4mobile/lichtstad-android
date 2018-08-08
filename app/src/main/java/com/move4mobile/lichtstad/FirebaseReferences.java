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
    public static final DatabaseReference PROGRAM = FirebaseDatabase.getInstance().getReference("program_v2").child(BuildConfig.EVENT_YEAR);

    /**
     * The reference to the database holding the results.
     */
    public static final DatabaseReference RESULT = FirebaseDatabase.getInstance().getReference("result");

    /**
     * The reference to the database holding the content of the results.
     */
    public static final DatabaseReference RESULT_CONTENT = FirebaseDatabase.getInstance().getReference("result_content");

    /**
     * The reference to the database holding the list of videos.
     */
    public static final DatabaseReference YOUTUBE = FirebaseDatabase.getInstance().getReference("youtube");

    /**
     * The reference to the database holding the list of albums.
     */
    public static final DatabaseReference ALBUM = FirebaseDatabase.getInstance().getReference("album");

    /**
     * The reference to the database holding the list of album content.
     */
    public static final DatabaseReference ALBUM_CONTENT = FirebaseDatabase.getInstance().getReference("album_content");

    /**
     * The reference to the database holding the route kml.
     */
    public static final DatabaseReference ROUTE = FirebaseDatabase.getInstance().getReference("map").child(BuildConfig.EVENT_YEAR).child("route");

    /**
     * The reference to the database holding the markers kml.
     */
    public static final DatabaseReference MARKERS = FirebaseDatabase.getInstance().getReference("map").child(BuildConfig.EVENT_YEAR).child("markers");

    /**
     * A collection of all {@link DatabaseReference}s used in this application.
     */
    public static final Collection<DatabaseReference> ALL = Collections.unmodifiableCollection(Arrays.asList(
            PROGRAM,
            RESULT,
            RESULT_CONTENT,
            YOUTUBE,
            ALBUM,
            ALBUM_CONTENT,
            ROUTE,
            MARKERS
    ));

    /**
     * A collection of all {@link DatabaseReference}s used and kept synced in this application.
     */
    public static final Collection<DatabaseReference> ALL_SYNCED = Collections.unmodifiableCollection(Arrays.asList(
            PROGRAM,
            RESULT,
            RESULT_CONTENT,
            YOUTUBE,
            ALBUM,
            ROUTE
    ));

}
