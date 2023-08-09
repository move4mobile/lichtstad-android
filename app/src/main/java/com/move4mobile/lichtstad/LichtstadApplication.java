package com.move4mobile.lichtstad;

import android.app.Application;
import android.provider.Settings;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import com.move4mobile.context.ContextFixer;
import com.move4mobile.lichtstad.util.ConfigUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class LichtstadApplication extends Application {

    private static final String TAG = LichtstadApplication.class.getSimpleName();

    private final Map<Class<?>, Object> appServices = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG);

        String testLabSetting = Settings.System.getString(getContentResolver(), "firebase.test.lab");
        if (BuildConfig.DEBUG || "true".equals(testLabSetting)) {
//            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false);
        }

        ContextFixer.startFixing(this, R.string.default_locale_language);

        TimeZone.setDefault(ConfigUtil.getEventTimeZone(this));
    }

    public <T> void registerApplicationService(Class<T> clazz, T service) {
        appServices.put(clazz, service);
    }

    public <T> T getApplicationService(Class<T> clazz) {
        //noinspection unchecked
        return (T) appServices.get(clazz);
    }
}
