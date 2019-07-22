package com.move4mobile.lichtstad.util;

import android.content.Context;

import com.move4mobile.lichtstad.R;

import java.util.TimeZone;

public class ConfigUtil {

    public static String getEventYear(Context context) {
        return context.getString(R.string.event_year);
    }

    public static TimeZone getEventTimeZone(Context context) {
        return TimeZone.getTimeZone(context.getString(R.string.event_timezone));
    }

}
