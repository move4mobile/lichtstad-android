package com.move4mobile.lichtstad;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


public class LichtstadApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
