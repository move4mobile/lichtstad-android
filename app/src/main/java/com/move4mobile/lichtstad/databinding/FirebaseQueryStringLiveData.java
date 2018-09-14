package com.move4mobile.lichtstad.databinding;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class FirebaseQueryStringLiveData extends LiveData<String> {

    private static final String TAG = FirebaseQueryStringLiveData.class.getSimpleName();
    private final Query query;

    public FirebaseQueryStringLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        super.onActive();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setValue(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "databaseError while observing " + query, databaseError.toException());
            }
        });
    }
}
