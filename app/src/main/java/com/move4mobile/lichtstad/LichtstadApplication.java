package com.move4mobile.lichtstad;

import android.app.Application;
import android.provider.Settings;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.move4mobile.context.ContextFixer;

import java.util.TimeZone;

import io.fabric.sdk.android.Fabric;


public class LichtstadApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String testLabSetting = Settings.System.getString(getContentResolver(), "firebase.test.lab");
        if (BuildConfig.DEBUG || "true".equals(testLabSetting)) {
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false);
        }

        ContextFixer.startFixing(this, R.string.default_locale_language);

        initializeDatabase();

        TimeZone.setDefault(BuildConfig.EVENT_TIMEZONE);
    }

    /**
     * Sets up the database with the correct settings. The database can be accessed through the default
     * {@link FirebaseDatabase} methods, or through {@link FirebaseReferences}
     */
    private void initializeDatabase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        for (DatabaseReference reference : FirebaseReferences.ALL_SYNCED) {
            reference.keepSynced(true);
        }
    }
}
