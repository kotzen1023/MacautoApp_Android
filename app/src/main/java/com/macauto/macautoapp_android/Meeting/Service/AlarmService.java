package com.macauto.macautoapp_android.Meeting.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.macauto.macautoapp_android.Data.Constants;

import com.macauto.macautoapp_android.Meeting.Data.InitData;
import com.macauto.macautoapp_android.Receiver.Receive_BootCompleted;
import com.macauto.macautoapp_android.Meeting.Task.CheckMeetingTask;

import java.util.Calendar;
import java.util.Timer;

public class AlarmService extends Service {
    private static final String TAG = AlarmService.class.getName();
    private Timer mTimer;
    CheckMeetingTask checkMeetingTask;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "=== MyService onCreate ===");
        InitData.init();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        stopTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "onStartCommand() executed");

        mTimer = null;

        startTimer();


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startTimer() {
        if (mTimer == null) {
            Log.i(TAG, "mTimer restart");
            mTimer = new Timer();
            checkMeetingTask = new CheckMeetingTask(this);
            mTimer.schedule(checkMeetingTask, 0L, 300000L);
        }
    }
    private void stopTimer(){

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.SECOND, 3);

        Intent usageServiceintent = new Intent(this, Receive_BootCompleted.class);
        usageServiceintent.setAction(Constants.ACTION.GET_START_ALARM_SERVICE_ACTION);
        usageServiceintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);

        PendingIntent pending = PendingIntent.getBroadcast(this.getApplicationContext(), 1, usageServiceintent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.e(TAG, "getTimeInMillis = "+String.valueOf(cal.getTimeInMillis())+", System time"+ String.valueOf(System.currentTimeMillis()));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT ) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending);
        }


        Log.e(TAG, "onTaskRemoved");

        super.onTaskRemoved(rootIntent);
    }
}
