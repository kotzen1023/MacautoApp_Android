package com.macauto.macautoapp_android.Meeting.Data;


import android.app.PendingIntent;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InitData {
    private static final String TAG = InitData.class.getName();
    public static ArrayList<PendingIntent> alarmList = new ArrayList<>();

    public static Set<String> calendarEventsList = new HashSet<>();
    public static Set<String> calendarRemindersList = new HashSet<>();

    private Context context;

    public InitData(Context context) {
        super();
        this.context = context;
    }


    public static void init() {
        Log.e(TAG, "=== InitData init ===");

        alarmList.clear();
        calendarEventsList.clear();

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

    /*public static void calendarEventsClear() {
        Log.d(TAG, "calendarEventsClear");
        calendarEvetsList.clear();
    }

    public static void canlendarEventAdd(String eventId) {
        calendarEvetsList.add(eventId);
    }

    public static void canlendarEventRemove(String eventId) {
        calendarEvetsList.remove(eventId);
    }

    public static void canlendarEventAddAll(Set<String> Sets) {
        calendarEvetsList = new HashSet<>(Sets);
    }*/
}
