package com.move4mobile.lichtstad.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.move4mobile.lichtstad.R;

public class DefaultNotificationChannelInitProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        createDefaultNotificationChannel();

        return false;
    }

    private void createDefaultNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        getNotificationManager().createNotificationChannel(getDefaultNotificationChannel());
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel getDefaultNotificationChannel() {
        NotificationChannel defaultChannel = new NotificationChannel(
                getContext().getString(R.string.notification_channel_id_default),
                getContext().getString(R.string.notification_channel_name_default),
                NotificationManager.IMPORTANCE_DEFAULT
        );
        return defaultChannel;
    }


    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
