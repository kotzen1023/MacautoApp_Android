package com.macauto.macautoapp_android.Meeting.Data;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;


public class NotificationPublisher extends BroadcastReceiver {
    private static final String TAG = NotificationPublisher.class.getName();

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";



    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "=== NotificationPublisher onReceive start ===");

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        Log.d(TAG, "notification = "+ notification.toString());

        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
        Log.d(TAG, "=== NotificationPublisher onReceive end ===");
    }
}
