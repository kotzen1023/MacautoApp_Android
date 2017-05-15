package com.macauto.macautoapp_android.Meeting.Data;


import android.app.PendingIntent;


import android.util.Log;

import java.util.ArrayList;

public class InitData {
    private static final String TAG = InitData.class.getName();
    private static ArrayList<PendingIntent> alarmList = new ArrayList<>();





    public static void init() {
        Log.e(TAG, "=== InitData init ===");
        alarmList.clear();


    }

    public static void clear() {
        Log.e(TAG, "clear list");
        alarmList.clear();
    }

    public static ArrayList<PendingIntent> getAlarmList() {
        return alarmList;
    }

    public static void add(PendingIntent pendingIntent) {
        alarmList.add(pendingIntent);
    }

    public static PendingIntent get(int index) {
        return alarmList.get(index);
    }
}
