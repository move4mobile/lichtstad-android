package com.move4mobile.lichtstad.map;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Layer;
import com.move4mobile.lichtstad.BaseContentFragment;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentMapBinding;
import com.move4mobile.lichtstad.model.MarkerContent;
import com.move4mobile.lichtstad.util.ResourceFloatUtil;
import com.squareup.moshi.Moshi;

import java.io.IOException;

public class MapFragment extends BaseContentFragment implements OnMapReadyCallback, Layer.OnFeatureClickListener, GoogleMap.OnMarkerClickListener {

    private static final String TAG = MapFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMapBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment;
        if (savedInstanceState == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.container, supportMapFragment)
                    .commit();
        } else {
            supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.container);
        }
        supportMapFragment.getMapAsync(this);

        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Context context = getContext();

        if (context == null) {
            return;
        }
        boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style));
        if (!success) {
            Log.e(TAG, "Style parsing failed");
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                getLatLngBounds(),
                0
        ));

        FirebaseKmlAdapter.startObserving(getContext(), this, googleMap, FirebaseReferences.ROUTE);
        FirebaseKmlAdapter.startObserving(getContext(), this, googleMap, FirebaseReferences.MARKERS);

        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onFeatureClick(Feature feature) {
        //TODO: Show popup
    }

    private LatLngBounds getLatLngBounds() {
        return new LatLngBounds(
                new LatLng(
                        ResourceFloatUtil.getFloat(getResources(), R.dimen.map_min_lat),
                        ResourceFloatUtil.getFloat(getResources(), R.dimen.map_min_lng)
                ),
                new LatLng(
                        ResourceFloatUtil.getFloat(getResources(), R.dimen.map_max_lat),
                        ResourceFloatUtil.getFloat(getResources(), R.dimen.map_max_lng)
                )
        );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getSnippet() == null) {
            return true;
        }

        try {
            MarkerContent markerContent = new Moshi.Builder().build().adapter(MarkerContent.class).fromJson(marker.getSnippet());
            MarkerDetailFragment fragment = MarkerDetailFragment.newInstance(markerContent);
            fragment.show(getChildFragmentManager(), null);
        } catch (IOException e) {
            Crashlytics.logException(e);
        }

        return true;
    }
}
