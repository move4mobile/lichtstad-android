package com.move4mobile.lichtstad.initprovider;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.move4mobile.lichtstad.BuildConfig;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.util.ConfigUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class LichtstadFirebaseReferences extends FirebaseReferences {

    private Map<String, DatabaseReference> references = new HashMap<>();

    LichtstadFirebaseReferences(Context context) {
        String eventYear = ConfigUtil.getEventYear(context);
        references.put("program", FirebaseDatabase.getInstance().getReference("program_v2").child(eventYear));
        references.put("result", FirebaseDatabase.getInstance().getReference("result"));
        references.put("result_content", FirebaseDatabase.getInstance().getReference("result_content"));
        references.put("youtube", FirebaseDatabase.getInstance().getReference("youtube"));
        references.put("album", FirebaseDatabase.getInstance().getReference("album"));
        references.put("album_content", FirebaseDatabase.getInstance().getReference("album_content"));
        references.put("route", FirebaseDatabase.getInstance().getReference("map").child(eventYear).child("route"));
        references.put("markers", FirebaseDatabase.getInstance().getReference("map").child(eventYear).child("markers"));
    }

    @NonNull
    @Override
    public DatabaseReference get(@NonNull String key) {
        if (references.isEmpty()) {
            throw new IllegalStateException("Uninitialized");
        }
        if (!references.containsKey(key)) {
            throw new IllegalArgumentException("Unknown key: " + key);
        }
        return references.get(key);
    }

    Collection<DatabaseReference> allReferences() {
        return references.values();
    }
    
    Collection<DatabaseReference> allSyncedReferences() {
        return Arrays.asList(
                get("program"),
                get("result"),
                get("result_content"),
                get("youtube"),
                get("album"),
                get("route"),
                get("markers")
        );
    }

}
