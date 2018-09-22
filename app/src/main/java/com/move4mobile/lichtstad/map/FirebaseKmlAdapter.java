package com.move4mobile.lichtstad.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class FirebaseKmlAdapter implements LifecycleObserver, ValueEventListener {

    private Context context;
    private GoogleMap map;
    private KmlLayer layer;
    private DatabaseReference reference;

    private FirebaseKmlAdapter(Context context, LifecycleOwner owner, GoogleMap map, DatabaseReference reference) {
        this.context = context;
        this.map = map;
        this.reference = reference;
        owner.getLifecycle().addObserver(this);
    }

    public static void startObserving(Context context, LifecycleOwner owner, GoogleMap map, DatabaseReference reference) {
        new FirebaseKmlAdapter(context, owner, map, reference);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startListening() {
        reference.addValueEventListener(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopListening() {
        reference.removeEventListener(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void cleanup(LifecycleOwner source) {
        source.getLifecycle().removeObserver(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (layer != null) {
            layer.removeLayerFromMap();
        }

        String kml = dataSnapshot.getValue(String.class);

        if (kml != null) {
            InputStream stream = new ByteArrayInputStream(kml.getBytes(StandardCharsets.UTF_8));
            try {
                layer = new KmlLayer(map, stream, context);
                layer.addLayerToMap();
            } catch (XmlPullParserException | IOException e) {
                //Crashlytics.logException(e);
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
