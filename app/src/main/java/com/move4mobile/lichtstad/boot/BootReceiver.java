package com.move4mobile.lichtstad.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// Just an empty BroadcastReceiver that's used to make sure our app launches with the device
// so we can reschedule notifications and make sure our data will get synced sometime
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
