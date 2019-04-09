package com.move4mobile.lichtstad.initprovider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.move4mobile.lichtstad.BuildConfig;
import com.move4mobile.lichtstad.FirebaseReferences;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class LichtstadFirebaseReferences extends FirebaseReferences {

    /**
     * The reference to the database holding the program.
     */
    private static final DatabaseReference PROGRAM = FirebaseDatabase.getInstance().getReference("program_v2").child(BuildConfig.EVENT_YEAR);

    /**
     * The reference to the database holding the results.
     */
    private static final DatabaseReference RESULT = FirebaseDatabase.getInstance().getReference("result");

    /**
     * The reference to the database holding the content of the results.
     */
    private static final DatabaseReference RESULT_CONTENT = FirebaseDatabase.getInstance().getReference("result_content");

    /**
     * The reference to the database holding the list of videos.
     */
    private static final DatabaseReference YOUTUBE = FirebaseDatabase.getInstance().getReference("youtube");

    /**
     * The reference to the database holding the list of albums.
     */
    private static final DatabaseReference ALBUM = FirebaseDatabase.getInstance().getReference("album");

    /**
     * The reference to the database holding the list of album content.
     */
    private static final DatabaseReference ALBUM_CONTENT = FirebaseDatabase.getInstance().getReference("album_content");

    /**
     * The reference to the database holding the route kml.
     */
    private static final DatabaseReference ROUTE = FirebaseDatabase.getInstance().getReference("map").child(BuildConfig.EVENT_YEAR).child("route");

    /**
     * The reference to the database holding the markers kml.
     */
    private static final DatabaseReference MARKERS = FirebaseDatabase.getInstance().getReference("map").child(BuildConfig.EVENT_YEAR).child("markers");
    
    private static Map<String, DatabaseReference> references = new HashMap<>();
    static {
        references.put("program", PROGRAM);
        references.put("result", RESULT);
        references.put("result_content", RESULT_CONTENT);
        references.put("youtube", YOUTUBE);
        references.put("album", ALBUM);
        references.put("album_content", ALBUM_CONTENT);
        references.put("route", ROUTE);
        references.put("markers", MARKERS);
    }

    @NonNull
    @Override
    public DatabaseReference get(@NonNull String key) {
        if (!references.containsKey(key)) {
            throw new IllegalArgumentException("Unknown key: " + key);
        }
        return references.get(key);
    }

    Collection<DatabaseReference> allReferences() {
        return references.values();
    }
    
    Collection<DatabaseReference> allSyncedReferences() {
        return Collections.unmodifiableCollection(Arrays.asList(
                PROGRAM,
                RESULT,
                RESULT_CONTENT,
                YOUTUBE,
                ALBUM,
                ROUTE,
                MARKERS
        ));
    }

}
