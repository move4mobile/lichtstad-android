package com.move4mobile.lichtstad.util;

import android.content.Context;

import com.move4mobile.lichtstad.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ConfigUtil {

    public static String getEventYear(Context context) {
        return context.getString(R.string.event_year);
    }

    public static TimeZone getEventTimeZone(Context context) {
        return TimeZone.getTimeZone(context.getString(R.string.event_timezone));
    }

    public static String getFavoritesPreferencesPrefix() {
        return "favorites_";
    }

    public static String getFavoritesPreferenceKey(Calendar day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        format.setTimeZone(day.getTimeZone());
        return getFavoritesPreferencesPrefix() + format.format(day.getTime());
    }

}
