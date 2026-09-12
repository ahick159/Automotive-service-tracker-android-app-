package com.example.automotivetracker.ui;


import com.example.automotivetracker.R;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ServiceReceiver extends BroadcastReceiver {
    String channel_id = "Service Alert";
    static int notificationID=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String title = null;
        String contentText = null;
        if (action != null) {
            switch (action) {
                case"service action":
                    title="Service Reminder";
                    contentText=intent.getStringExtra("key");
                    break;
            }
        }
        createNotificationChannel(context, channel_id);

        Notification notification =
                new NotificationCompat.Builder(context, channel_id)
                        .setSmallIcon(R.drawable.baseline_add_24)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .build();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(
                        Context.NOTIFICATION_SERVICE
                );

        notificationManager.notify(

                notificationID++,
                notification
        );
    }
    private void createNotificationChannel(
            Context context,
            String channel_id) {

        CharSequence name = "Vacation Alerts";
        String description = "Vacation start and end alerts";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            channel_id,
                            name,
                            importance
                    );

            channel.setDescription(description);

            NotificationManager notificationManager =
                    context.getSystemService(
                            NotificationManager.class
                    );

            notificationManager.createNotificationChannel(channel);
        }
    }
}
