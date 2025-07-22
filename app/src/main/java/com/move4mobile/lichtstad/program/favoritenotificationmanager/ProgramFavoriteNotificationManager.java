package com.move4mobile.lichtstad.program.favoritenotificationmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.move4mobile.lichtstad.FirebaseReferences;
import com.move4mobile.lichtstad.MainActivity;
import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.model.Program;
import com.move4mobile.lichtstad.program.favorite.FavoriteManager;
import com.move4mobile.lichtstad.snapshotparser.KeyedSnapshotParser;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class ProgramFavoriteNotificationManager implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = ProgramFavoriteNotificationManager.class.getSimpleName();

    private static final String EXTRA_PROGRAM_DATEKEY = "program";
    private static final long ALARM_TRIGGER_ADVANCE = 15 * 60 * 1000L;

    private final Context context;

    /* package */ ProgramFavoriteNotificationManager(@NonNull Context context) {
        this.context = context;
        FavoriteManager.registerFavoriteChangeListener(context, this);
    }

    /* package */ void createNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getChannel(context));
        }
    }

    /* package */ void reregisterNotifications() {
        for (Map.Entry<String, Collection<String>> dayFavorites : FavoriteManager.getAllFavorites(context).entrySet()) {
            for (String key : dayFavorites.getValue()) {
                addScheduledNotification(FavoriteManager.getPreferenceKey(dayFavorites.getKey(), key), true);
            }
        }
    }

    private void removeScheduledNotification(String dateAndKey) {
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(getNotificationPendingIntent(dateAndKey));
    }

    private void addScheduledNotification(String dateAndKey, boolean reschedule) {
//        getProgram(context, dateAndKey, program -> {
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            if (alarmManager == null || !shouldSchedule(program, reschedule) || !FavoriteManager.isFavorite(context, dateAndKey)) return;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getAlarmTriggerMillis(program), getNotificationPendingIntent(dateAndKey));
//            } else {
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, getAlarmTriggerMillis(program), getNotificationPendingIntent(dateAndKey));
//            }
//        });
    }

    private long getAlarmTriggerMillis(Program program) {
        return program.getTime() - ALARM_TRIGGER_ADVANCE;
    }

    private boolean shouldSchedule(Program program, boolean reschedule) {
        // Rescheduled notifications can be scheduled until the program starts
        // User scheduled notifications can only be scheduled if it's still before the alarm schedule time
        if (reschedule) {
            return new Date().before(program.getTimeAsDate());
        }

        return new Date().before(new Date(getAlarmTriggerMillis(program)));
    }

    private PendingIntent getNotificationPendingIntent(String dateAndKey) {
        Intent intent = new Intent(context, ProgramFavoriteNotificationBroadcastReceiver.class);
        intent.putExtra(EXTRA_PROGRAM_DATEKEY, dateAndKey);
        int flags = PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT | Intent.FILL_IN_DATA;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        return PendingIntent.getBroadcast(context, dateAndKey.hashCode(), intent, flags);
    }

    private static void getProgram(Context context, String dateAndKey, ProgramUser programUser) {
        FirebaseReferences.instance().get("program").child(dateAndKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Log.w(TAG, dateAndKey + " seems to have been deleted");
                            return;
                        }

//                        Program program = new KeyedSnapshotParser<>(Program.class).parseSnapshot(dataSnapshot);
//                        programUser.use(program);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "Error while trying to get program for " + dateAndKey + ": " + databaseError);
                    }
                });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String preferenceKey) {
        if (sharedPreferences.getBoolean(preferenceKey, false)) {
            addScheduledNotification(preferenceKey, false);
        } else {
            removeScheduledNotification(preferenceKey);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel getChannel(Context context) {
        return new NotificationChannel(
                context.getString(R.string.notification_channel_id_program),
                context.getString(R.string.notification_channel_name_favorite),
                NotificationManager.IMPORTANCE_DEFAULT
        );
    }

    private interface ProgramUser {

        void use(Program program);

    }

    public static class ProgramFavoriteNotificationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String dateAndKey = intent.getStringExtra(EXTRA_PROGRAM_DATEKEY);
            getProgram(context, dateAndKey, program -> {
                // The user could have deselected the favorite while we were loading it
                if (FavoriteManager.isFavorite(context, dateAndKey)) {
                    publishProgramNotification(context, program);
                }
            });
        }

        private void publishProgramNotification(Context context, Program program) {
            if (program == null || program.getTimeAsDate().before(new Date())) {
                Log.w(TAG, "Ignoring late notification broadcast");
                return;
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(program.getKey().hashCode(), getNotification(context, program));
        }

        private Notification getNotification(Context context, Program program) {
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags |= PendingIntent.FLAG_IMMUTABLE;
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id_program))
                    .setContentTitle(program.getTitle())
                    .setContentText(getContentText(context, program))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_notification_small)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_small))
                    .setContentIntent(PendingIntent.getActivity(context, program.getKey().hashCode(), new Intent(context, MainActivity.class), flags));
            return builder.build();
        }

        private String getContentText(Context context, Program program) {
            return context.getString(R.string.notification_body_favorite, program.getTitle(), DateFormat.getTimeInstance(DateFormat.SHORT).format(program.getTimeAsDate()));
        }
    }

}
