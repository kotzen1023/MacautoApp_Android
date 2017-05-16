package com.macauto.macautoapp_android.Meeting;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.macauto.macautoapp_android.Data.Constants;
import com.macauto.macautoapp_android.R;
import com.macauto.macautoapp_android.Meeting.Service.GetPersonMeetingService;

import java.util.ArrayList;
import java.util.HashSet;

import static com.macauto.macautoapp_android.Meeting.Data.InitData.alarmList;
import static com.macauto.macautoapp_android.Meeting.Data.InitData.calendarEventsList;
import static com.macauto.macautoapp_android.Meeting.Data.InitData.calendarRemindersList;

public class MeetingAlarm extends Activity {
    private static final String TAG = MeetingAlarm.class.getName();

    //private Spinner spinner;
    //private Switch aSwitch;
    private ArrayList<String> optionList = new ArrayList<>();
    private ArrayList<String> syncOption = new ArrayList<>();

    static SharedPreferences pref ;
    static SharedPreferences.Editor editor;
    private static final String FILE_NAME = "Preference";
    public static int last_sync_setting = 0;

    private Context context;
    private static int alarm_interval;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        context = getBaseContext();

        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.setStatusBarColor(getResources().getColor(R.color.black));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getResources().getColor(R.color.black, getTheme()));
        }

        pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        alarm_interval = pref.getInt("ALARM_INTERVAL", 30);
        final int sync_setting = pref.getInt("SYNC_SETTING", 0);
        last_sync_setting = sync_setting;

        Log.e(TAG, "alarm_interval = "+alarm_interval);

        ArrayAdapter<String> listAdapter;
        ArrayAdapter<String> listSyncAdapter;

        for (int i=5;i<=90; i=i+5 ) {
            String option = getResources().getString(R.string.macauto_meeting_alarm_interval, String.valueOf(i));
            optionList.add(option);
        }

        syncOption.add(getResources().getString(R.string.macauto_none));
        syncOption.add(getResources().getString(R.string.macauto_sync_notification));
        syncOption.add(getResources().getString(R.string.macauto_sync_calendar));


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinSync = (Spinner) findViewById(R.id.spinnerSync);


        listAdapter = new ArrayAdapter<>(MeetingAlarm.this, R.layout.myspinner, optionList);
        spinner.setAdapter(listAdapter);

        listSyncAdapter = new ArrayAdapter<>(MeetingAlarm.this, R.layout.myspinner, syncOption);
        spinSync.setAdapter(listSyncAdapter);

        spinner.setSelection(alarm_interval/5-1);
        spinSync.setSelection(sync_setting);

        if (sync_setting == 0) {
            spinner.setVisibility(View.INVISIBLE);
        } else {
            spinner.setVisibility(View.VISIBLE);
        }

        spinSync.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(TAG, "select position "+i);


                if (last_sync_setting != i) {

                    editor = pref.edit();
                    editor.putInt("SYNC_SETTING", i);
                    editor.apply();

                    if (i > 0) {

                        Intent intent = new Intent(MeetingAlarm.this, GetPersonMeetingService.class);
                        intent.setAction(Constants.ACTION.GET_PERSONAL_MEETING_LIST_ACTION);
                        startService(intent);
                    } else {
                        if (alarmList != null && alarmList.size() > 0) {
                            for (PendingIntent pendingintent : alarmList) {
                                removeSysNotification(pendingintent);
                            }
                            alarmList.clear();
                        } else {
                            alarmList = new ArrayList<>();
                        }

                        //if calendar list is not empty, clear all events and reminders
                        if (calendarRemindersList != null && calendarRemindersList.size() > 0) {
                            for (String remindersID : calendarRemindersList) {
                                removeReminder(remindersID);
                            }
                            calendarRemindersList.clear();
                        } else {
                            calendarRemindersList = new HashSet<>();
                        }

                        if (calendarEventsList != null && calendarEventsList.size() > 0) {
                            for (String eventID : calendarEventsList) {
                                removeEvent(eventID);
                            }
                            calendarEventsList.clear();
                        } else {
                            calendarEventsList = new HashSet<>();

                        }
                    }

                    last_sync_setting = i;
                }

                if (i == 0) {
                    spinner.setVisibility(View.GONE);
                } else {
                    spinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "select position "+position);
                if (position != (alarm_interval/5-1)) {
                    alarm_interval = (position + 1) * 5;

                    if (spinSync.getSelectedItemPosition() == 2) {
                        //clear all reminders before add
                        for (String reminderId : calendarRemindersList) {
                            removeReminder(reminderId);
                        }
                        calendarRemindersList.clear();

                        //create new reminders from events
                        for (String eventId : calendarEventsList) {
                            addReminder(Long.parseLong(eventId), alarm_interval);
                        }
                    }
                    //save
                    editor = pref.edit();
                    editor.putInt("ALARM_INTERVAL", alarm_interval);
                    editor.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /*public void toast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }*/

    public void addReminder(long eventID, int timeBefore) {

        ContentResolver cr = getContentResolver();

        try {
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.MINUTES, timeBefore);
            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            try {
                Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
                Cursor c = CalendarContract.Reminders.query(cr, eventID,
                        new String[]{CalendarContract.Reminders.MINUTES});
                if (c.moveToFirst()) {
                    System.out.println("calendar "
                            + c.getInt(c.getColumnIndex(CalendarContract.Reminders.MINUTES)));
                }
                c.close();
                if (uri.getLastPathSegment().toString().length() > 0)
                    calendarRemindersList.add(uri.getLastPathSegment().toString());
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeReminder(String reminderID) {
        int iNumRowsDeleted = 0;
        ContentResolver cr = context.getContentResolver();

        Uri reminderUri = ContentUris.withAppendedId(
                CalendarContract.Reminders.CONTENT_URI, Long.parseLong(reminderID));

        if (reminderID != null) {

            try {

                iNumRowsDeleted = cr.delete(reminderUri, null, null);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "Deleted " + iNumRowsDeleted + " reminder entry.");
        }
    }

    public void removeEvent(String eventID) {
        int iNumRowsDeleted = 0;
        //Date start_date=null;
        //Date end_date=null;
        ContentResolver cr = context.getContentResolver();

        Uri baseUri;
        if (Build.VERSION.SDK_INT >= 8) {
            baseUri = Uri.parse("content://com.android.calendar/events");
        } else {
            baseUri = Uri.parse("content://calendar/events");
        }

        //Uri eventsUri = Uri.parse(CALENDAR_URI_BASE+"events");
        Uri eventUri = ContentUris.withAppendedId(baseUri, Long.parseLong(eventID));
        iNumRowsDeleted = cr.delete(eventUri, null, null);

        Log.i(TAG, "Deleted " + iNumRowsDeleted + " calendar entry.");

    }

    public void removeSysNotification(PendingIntent pendingIntent) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);

    }
}
