package com.move4mobile.lichtstad.snapshotparser;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateSnapshotParser implements SnapshotParser<Calendar> {

    private static final String TAG = DateSnapshotParser.class.getSimpleName();
    private final DateFormat format;

    @SuppressLint("SimpleDateFormat")
    public DateSnapshotParser(String format) {
        this.format = new SimpleDateFormat(format);
    }

    @NonNull
    @Override
    public Calendar parseSnapshot(@NonNull DataSnapshot snapshot) {
        Calendar day = Calendar.getInstance();
        try {
            day.setTime(format.parse(snapshot.getKey()));
        } catch (ParseException e) {
            Log.e(TAG, "Error while parsing snapshot " + snapshot, e);
            Crashlytics.logException(e);
        }
        return day;
    }
}
