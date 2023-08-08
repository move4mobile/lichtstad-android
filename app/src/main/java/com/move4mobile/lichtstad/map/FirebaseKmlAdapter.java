package com.move4mobile.lichtstad.map;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.collections.GroundOverlayManager;
import com.google.maps.android.collections.MarkerManager;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FirebaseKmlAdapter implements LifecycleObserver, ValueEventListener {

    private Context context;
    private GoogleMap map;
    private KmlLayer layer;
    private DatabaseReference reference;
    private GoogleMap.OnMarkerClickListener markerClickListener;

    private FirebaseKmlAdapter(Context context, LifecycleOwner owner, GoogleMap map, DatabaseReference reference) {
        this.context = context;
        this.map = map;
        this.reference = reference;
        owner.getLifecycle().addObserver(this);
    }

    public static FirebaseKmlAdapter startObserving(Context context, LifecycleOwner owner, GoogleMap map, DatabaseReference reference) {
        return new FirebaseKmlAdapter(context, owner, map, reference);
    }

    public void setMarkerClickListener(GoogleMap.OnMarkerClickListener markerClickListener) {
        this.markerClickListener = markerClickListener;
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

        MarkerManager mm = new MarkerManager(map) {

            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if (markerClickListener != null) {
                    return markerClickListener.onMarkerClick(marker);
                } else {
                    return super.onMarkerClick(marker);
                }
            }
        };


        if (kml != null) {
            InputStream stream = new ByteArrayInputStream(kml.getBytes(StandardCharsets.UTF_8));
            try {
                layer = new KmlLayer(map, stream, context, mm, null, null, null, null);
                layer.addLayerToMap();
            } catch (XmlPullParserException | IOException e) {
                FirebaseCrashlytics.getInstance().recordException(e);
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
