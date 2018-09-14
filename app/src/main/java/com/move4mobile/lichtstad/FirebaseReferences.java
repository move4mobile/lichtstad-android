package com.move4mobile.lichtstad;

import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;

public abstract class FirebaseReferences {

    private static FirebaseReferences instance;

    public static void registerInstance(@NonNull FirebaseReferences references) {
        if (instance != null) {
            throw new IllegalStateException("Instance already registered");
        }
        instance = references;
    }

    @NonNull
    public static FirebaseReferences instance() {
        if (instance == null) {
            throw new IllegalStateException("No instance registered");
        }
        return instance;
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    @NonNull
    public abstract DatabaseReference get(@NonNull String key);

}
