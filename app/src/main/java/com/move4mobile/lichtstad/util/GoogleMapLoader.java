package com.move4mobile.lichtstad.util;

import android.content.Context;

import com.google.android.gms.maps.MapView;

public class GoogleMapLoader {

    /**
     * Preloads a MapView on a background thread so the initial show of the MapView
     * isn't as slow.
     */
    public static void preloadGoogleMap(Context context) {
        Context applicationContext = context.getApplicationContext();
        new Thread(() -> {
            try {
                MapView mv = new MapView(applicationContext);
                mv.onCreate(null);
                mv.onPause();
                mv.onDestroy();
            }catch (Exception ignored){}
        }).start();
    }

}
