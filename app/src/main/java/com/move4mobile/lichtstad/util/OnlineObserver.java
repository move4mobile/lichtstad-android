package com.move4mobile.lichtstad.util;

import android.databinding.ObservableBoolean;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineObserver implements ValueEventListener {

    public static final OnlineObserver INSTANCE = new OnlineObserver();
    static {
        INSTANCE.startObserving();
    }

    public final ObservableBoolean isOnline = new ObservableBoolean(false);
    private final DatabaseReference connectedReference = FirebaseDatabase.getInstance().getReference(".info/connected");

    public void startObserving() {
        connectedReference.addValueEventListener(this);
    }

    public void stopObserving() {
        connectedReference.removeEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Boolean online = dataSnapshot.getValue(Boolean.class);
        Log.v("OnlineObserver", "New state: " + online);
        isOnline.set(online != null && online);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e("OnlineObserver", "DatabaseError: " + databaseError);
    }
}
