package com.move4mobile.lichtstad.remotenotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.move4mobile.lichtstad.BuildConfig;
import com.move4mobile.lichtstad.MainActivity;
import com.move4mobile.lichtstad.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = FirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(remoteMessage.getMessageId().hashCode(), getLocalNotification(remoteMessage.getNotification()));
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Firebase token: " + token);
    }

    private Notification getLocalNotification(RemoteMessage.Notification notification) {
        return new NotificationCompat.Builder(this, getChannelId(notification))
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                // Not setting small icon to notification small icon to reduce complexity a bit
                .setSmallIcon(R.drawable.ic_notification_small)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
                // Not setting large icon for now to reduce complexity by a lot
    }

    private String getChannelId(RemoteMessage.Notification notification) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return getString(R.string.notification_channel_id_default);
        }

        String requestedChannelId = notification.getChannelId();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (TextUtils.isEmpty(requestedChannelId) || notificationManager.getNotificationChannel(requestedChannelId) == null) {
            if (!TextUtils.isEmpty(requestedChannelId)) {
                if (BuildConfig.DEBUG) {
                    throw new IllegalArgumentException("Channel " + requestedChannelId + " does not exist.");
                } else {
                    Log.e(TAG, "Channel " + requestedChannelId + " does not exist. Falling back to default");
                }
            }
            return getString(R.string.notification_channel_id_default);
        }

        return requestedChannelId;
    }
}
