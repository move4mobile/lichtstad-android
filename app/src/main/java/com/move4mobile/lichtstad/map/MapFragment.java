package com.move4mobile.lichtstad.map;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.move4mobile.lichtstad.BaseContentFragment;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.databinding.FragmentMapBinding;
import com.move4mobile.lichtstad.util.ResourceFloatUtil;

public class MapFragment extends BaseContentFragment implements OnMapReadyCallback {

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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(
                        ResourceFloatUtil.getFloat(getResources(), R.dimen.map_center_lat),
                        ResourceFloatUtil.getFloat(getResources(), R.dimen.map_center_lng)
                ),
                ResourceFloatUtil.getFloat(getResources(), R.dimen.map_zoom)
        ));
    }
}
