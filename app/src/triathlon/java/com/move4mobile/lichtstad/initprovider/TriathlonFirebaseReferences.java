package com.move4mobile.lichtstad.initprovider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.move4mobile.lichtstad.BuildConfig;
import com.move4mobile.lichtstad.FirebaseReferences;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class TriathlonFirebaseReferences extends FirebaseReferences {

    private static Map<String, DatabaseReference> references = new HashMap<>();
    static {
        references.put("home", FirebaseDatabase.getInstance().getReference("pages").child("home"));
        references.put("program", FirebaseDatabase.getInstance().getReference("program").child(BuildConfig.EVENT_YEAR));
        references.put("info", FirebaseDatabase.getInstance().getReference("info"));
        references.put("info_content", FirebaseDatabase.getInstance().getReference("info_content"));
        references.put("youtube", FirebaseDatabase.getInstance().getReference("youtube"));
        references.put("route", FirebaseDatabase.getInstance().getReference("map").child(BuildConfig.EVENT_YEAR).child("route"));
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

}
