package com.move4mobile.lichtstad.program.favoritenotificationmanager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.move4mobile.lichtstad.util.ApplicationContext;

public class ProgramFavoriteNotificationManagerInitProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        Context context = getContext();

        ProgramFavoriteNotificationManager favoriteNotificationManager = new ProgramFavoriteNotificationManager(context);
        favoriteNotificationManager.createNotificationChannel();
        favoriteNotificationManager.reregisterNotifications();
        ApplicationContext.getApplication(context).registerApplicationService(ProgramFavoriteNotificationManager.class, favoriteNotificationManager);

        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
