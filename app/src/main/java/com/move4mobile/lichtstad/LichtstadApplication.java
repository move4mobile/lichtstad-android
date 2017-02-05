package com.move4mobile.lichtstad;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.TimeZone;


public class LichtstadApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDatabase();

        TimeZone.setDefault(BuildConfig.EVENT_TIMEZONE);
    }

    /**
     * Sets up the database with the correct settings. The database can be accessed through the default
     * {@link FirebaseDatabase} methods, or through {@link FirebaseReferences}
     */
    private void initializeDatabase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        for (DatabaseReference reference : FirebaseReferences.ALL) {
            reference.keepSynced(true);
        }
    }
}
