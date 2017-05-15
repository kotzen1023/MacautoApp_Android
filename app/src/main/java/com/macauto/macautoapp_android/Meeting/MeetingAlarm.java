package com.macauto.macautoapp_android.Meeting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

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

        final int alarm_interval = pref.getInt("ALARM_INTERVAL", 30);
        final int sync_setting = pref.getInt("SYNC_SETTING", 0);
        last_sync_setting = sync_setting;

        Log.e(TAG, "alarm_interval = "+alarm_interval);

        ArrayAdapter<String> listAdapter;
        ArrayAdapter<String> listSyncAdapter;

        for (int i=5;i<=90; i=i+5 ) {
            String option = getResources().getString(R.string.macauto_meeting_alarm_interval, i);
            optionList.add(option);
        }

        syncOption.add(getResources().getString(R.string.macauto_none));
        syncOption.add(getResources().getString(R.string.macauto_sync_notification));
        syncOption.add(getResources().getString(R.string.macauto_sync_calendar));


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinSync = (Spinner) findViewById(R.id.spinnerSync);


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

                    Intent intent = new Intent(MeetingAlarm.this, GetPersonMeetingService.class);
                    intent.setAction(Constants.ACTION.GET_PERSONAL_MEETING_LIST_ACTION);

                    /*if (i == 1) {
                        intent.putExtra("TYPE", "SYSTEM_NOTIFY");
                    } else if (i == 2) {
                        intent.putExtra("TYPE", "SYNC_CALENDAR");

                    }*/


                    startService(intent);
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
                    editor = pref.edit();
                    editor.putInt("ALARM_INTERVAL", (position + 1) * 5);
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
}
