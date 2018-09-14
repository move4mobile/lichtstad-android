package com.move4mobile.lichtstad;

import android.app.Application;
import android.provider.Settings;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.move4mobile.context.ContextFixer;

import java.util.TimeZone;

import io.fabric.sdk.android.Fabric;


public class LichtstadApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        Fabric.with(this, crashlyticsKit);

        String testLabSetting = Settings.System.getString(getContentResolver(), "firebase.test.lab");
        if (BuildConfig.DEBUG || "true".equals(testLabSetting)) {
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false);
        }

        ContextFixer.startFixing(this, R.string.default_locale_language);

        TimeZone.setDefault(BuildConfig.EVENT_TIMEZONE);
    }
}
