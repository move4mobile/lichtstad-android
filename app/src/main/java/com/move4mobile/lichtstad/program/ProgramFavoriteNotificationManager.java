package com.move4mobile.lichtstad.program;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.move4mobile.lichtstad.R;
import com.move4mobile.lichtstad.model.Program;

import java.text.DateFormat;
import java.util.Date;

public class ProgramFavoriteNotificationManager implements FavoriteChangedListener {

    private static final String TAG = ProgramFavoriteNotificationManager.class.getSimpleName();

    private static final String EXTRA_PROGRAM = "program";
    private static final long ALARM_TRIGGER_ADVANCE = 15 * 60 * 1000L;
    private static final String CHANNEL_ID = "program_notifications";

    private final Context context;

    public ProgramFavoriteNotificationManager(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public void onFavoriteChanged(Program program, boolean isFavorite) {
        if (isFavorite) {
            addScheduledNotification(program);
        } else {
            removeScheduledNotification(program);
        }
    }

    private void removeScheduledNotification(Program program) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getNotificationPendingIntent(program));
    }

    private void addScheduledNotification(Program program) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getAlarmTriggerMillis(program), getNotificationPendingIntent(program));
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getAlarmTriggerMillis(program), getNotificationPendingIntent(program));
        }
    }

    private long getAlarmTriggerMillis(Program program) {
        return program.getTime() - ALARM_TRIGGER_ADVANCE;
    }

    private PendingIntent getNotificationPendingIntent(Program program) {
        Intent intent = new Intent(context, ProgramFavoriteNotificationBroadcastReceiver.class);
        intent.putExtra(EXTRA_PROGRAM, program);
        return PendingIntent.getBroadcast(context, (int)(program.getTime() / 1000L), intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public static class ProgramFavoriteNotificationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Program program = intent.getParcelableExtra(EXTRA_PROGRAM);
            publishProgramNotification(context, program);
        }

        private void publishProgramNotification(Context context, Program program) {
            if (program.getTimeAsDate().after(new Date())) {
                Log.w(TAG, "Ignoring late notification broadcast");
                return;
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(getChannel(context));
            }
            notificationManager.notify(program.getKey().hashCode(), getNotification(context, program));
        }

        private Notification getNotification(Context context, Program program) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(program.getTitle())
                    .setContentText(getContentText(context, program))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground));
            return builder.build();
        }

        private String getContentText(Context context, Program program) {
            return context.getString(R.string.notification_favorite_body, program.getTitle(), DateFormat.getTimeInstance(DateFormat.SHORT).format(program.getTimeAsDate()));
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private NotificationChannel getChannel(Context context) {
            return new NotificationChannel(CHANNEL_ID, context.getString(R.string.notification_favorite_channel_name), NotificationManager.IMPORTANCE_DEFAULT);
        }
    }

}
