package com.move4mobile.lichtstad.initprovider;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.move4mobile.lichtstad.BuildConfig;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.util.ConfigUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class DMRFirebaseReferences extends FirebaseReferences {

    private Map<String, DatabaseReference> references = new HashMap<>();
    DMRFirebaseReferences(Context context) {
        String eventYear = ConfigUtil.getEventYear(context);
        references.put("home", FirebaseDatabase.getInstance().getReference("pages").child("home"));
        references.put("program", FirebaseDatabase.getInstance().getReference("program").child(eventYear));
        references.put("info", FirebaseDatabase.getInstance().getReference("info"));
        references.put("info_content", FirebaseDatabase.getInstance().getReference("info_content"));
        references.put("album", FirebaseDatabase.getInstance().getReference("album"));
        references.put("album_content", FirebaseDatabase.getInstance().getReference("album_content"));
        references.put("route", FirebaseDatabase.getInstance().getReference("map").child(eventYear).child("route"));
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
