package com.move4mobile.lichtstad.initprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.move4mobile.lichtstad.FirebaseReferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseReferencesInitProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DMRFirebaseReferences references = new DMRFirebaseReferences(getContext());
        for (DatabaseReference databaseReference : references.allReferences()) {
            databaseReference.keepSynced(true);
        }
        FirebaseReferences.registerInstance(references);

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
