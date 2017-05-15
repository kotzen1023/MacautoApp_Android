package com.macauto.macautoapp_android.Meeting;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.macauto.macautoapp_android.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class SetupRequestMeeting extends Activity {
    private static final String TAG = SetupRequestMeeting.class.getName();

    private Spinner spinnerYear;
    private Spinner spinnerMonth;
    private Spinner spinnerDay;
    private Spinner spinnerYearEnd;
    private Spinner spinnerMonthEnd;
    private Spinner spinnerDayEnd;

    public ArrayAdapter<String> bigMonthDayAdapter;
    public ArrayAdapter<String> smallMonthDayAdapter;
    public ArrayAdapter<String> febMonthNormalAdapter;
    public ArrayAdapter<String> febMonthLeapAdapter;
    public ArrayAdapter<String> bigMonthDayAdapterEnd;
    public ArrayAdapter<String> smallMonthDayAdapterEnd;
    public ArrayAdapter<String> febMonthNormalAdapterEnd;
    public ArrayAdapter<String> febMonthLeapAdapterEnd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_meeting_request_setup);

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

        Calendar c = Calendar.getInstance();
        //NumberFormat f = new DecimalFormat("00");

        //pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        //requestJobStart = pref.getString("REQUEST_JOB_START", String.valueOf(c.get(Calendar.YEAR))+f.format(c.get(Calendar.MONTH)+1)+f.format(c.get(Calendar.DAY_OF_MONTH)));
        //requestJobEnd = pref.getString("REQUEST_JOB_END", String.valueOf(c.get(Calendar.YEAR))+f.format(c.get(Calendar.MONTH)+1)+f.format(c.get(Calendar.DAY_OF_MONTH)));
        Log.e(TAG, "requestMeetingStart = "+ AllFragment.requestMeetingStart.substring(0,4)+" "+AllFragment.requestMeetingStart.substring(4,6)+" "+AllFragment.requestMeetingStart.substring(6,8));

        spinnerYear = (Spinner) findViewById(R.id.spinnerMeetingYear);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMeetingMonth);
        spinnerDay = (Spinner) findViewById(R.id.spinnerMeetingDay);
        spinnerYearEnd = (Spinner) findViewById(R.id.spinnerMeetingYearEnd);
        spinnerMonthEnd = (Spinner) findViewById(R.id.spinnerMeetingMonthEnd);
        spinnerDayEnd = (Spinner) findViewById(R.id.spinnerMeetingDayEnd);


        c.get(Calendar.YEAR);

        ArrayAdapter<String> yearAdapter;
        ArrayAdapter<String> monthAdapter;

        ArrayAdapter<String> yearEndAdapter;
        ArrayAdapter<String> monthEndAdapter;


        String[] yearlist = {
                String.valueOf(c.get(Calendar.YEAR)-5),
                String.valueOf(c.get(Calendar.YEAR)-4),
                String.valueOf(c.get(Calendar.YEAR)-3),
                String.valueOf(c.get(Calendar.YEAR)-2),
                String.valueOf(c.get(Calendar.YEAR)-1),
                String.valueOf(c.get(Calendar.YEAR)),
                String.valueOf(c.get(Calendar.YEAR)+1),
                String.valueOf(c.get(Calendar.YEAR)+2),
                String.valueOf(c.get(Calendar.YEAR)+3),
                String.valueOf(c.get(Calendar.YEAR)+4),
                String.valueOf(c.get(Calendar.YEAR)+5)};

        String[] monthlist = {getResources().getString(R.string.macauto_date_month_jan),
                            getResources().getString(R.string.macauto_date_month_feb),
                            getResources().getString(R.string.macauto_date_month_mar),
                            getResources().getString(R.string.macauto_date_month_apr),
                            getResources().getString(R.string.macauto_date_month_may),
                            getResources().getString(R.string.macauto_date_month_jun),
                            getResources().getString(R.string.macauto_date_month_jul),
                            getResources().getString(R.string.macauto_date_month_aug),
                            getResources().getString(R.string.macauto_date_month_sep),
                            getResources().getString(R.string.macauto_date_month_oct),
                            getResources().getString(R.string.macauto_date_month_nov),
                            getResources().getString(R.string.macauto_date_month_dec)
        };

        final String[] bigMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

        final String[] smallMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};

        final String[] FebMonthNormal = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28"};

        final String[] FebMonthLeap = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29"};

        yearAdapter = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, yearlist);
        spinnerYear.setAdapter(yearAdapter);

        for(int i=0; i<yearlist.length;i++) {
            if (AllFragment.requestMeetingStart.substring(0,4).equals(yearlist[i])) {
                spinnerYear.setSelection(i);
            }
        }

        monthAdapter = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, monthlist);
        spinnerMonth.setAdapter(monthAdapter);

        /*for(int i=0; i<monthlist.length;i++) {
            if (Integer.valueOf(AllFragment.requestMeetingStart.substring(4,6)) == Integer.valueOf(monthlist[i].toString())) {
                spinnerMonth.setSelection(i);
            }
        }*/

        spinnerMonth.setSelection(Integer.valueOf(AllFragment.requestMeetingStart.substring(4,6))-1);

        //set day selection
        bigMonthDayAdapter = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, bigMonth);
        smallMonthDayAdapter = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, smallMonth);
        febMonthNormalAdapter = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, FebMonthNormal);
        febMonthLeapAdapter = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, FebMonthLeap);

        int year = Integer.valueOf(AllFragment.requestMeetingStart.substring(0,4));
        switch (AllFragment.requestMeetingStart.substring(4,6)) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, bigMonth);
                spinnerDay.setAdapter(bigMonthDayAdapter);
                for (int j=0;j<bigMonth.length; j++) {
                    if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(bigMonth[j]))) {
                        spinnerDay.setSelection(j);
                    }
                }
                break;
            case "02":
                if ((year%400 == 0 && year%100 != 0) || year%4 == 0) {
                    Log.e(TAG, "Leap year!");
                    //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, FebMonthLeap);
                    spinnerDay.setAdapter(febMonthLeapAdapter);
                    for (int j=0;j<FebMonthLeap.length; j++) {
                        if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(FebMonthLeap[j]))) {
                            spinnerDay.setSelection(j);
                        }
                    }
                } else {
                    //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, FebMonthNormal);
                    spinnerDay.setAdapter(febMonthNormalAdapter);
                    for (int j=0;j<FebMonthLeap.length; j++) {
                        if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(FebMonthNormal[j]))) {
                            spinnerDay.setSelection(j);
                        }
                    }
                }
                break;
            case "04":
            case "06":
            case "09":
            case "11":
                //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, smallMonth);
                spinnerDay.setAdapter(smallMonthDayAdapter);
                for (int j=0;j<FebMonthLeap.length; j++) {
                    if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(smallMonth[j]))) {
                        spinnerDay.setSelection(j);
                    }
                }
                break;
            default:
                break;
        }


        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "get position "+position+", value = "+ spinnerYear.getSelectedItem().toString());

                int year = Integer.valueOf(spinnerYear.getSelectedItem().toString());

                switch (spinnerMonth.getSelectedItem().toString())
                {
                    case "1":
                    case "3":
                    case "5":
                    case "7":
                    case "8":
                    case "10":
                    case "12":
                        //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, bigMonth);
                        spinnerDay.setAdapter(bigMonthDayAdapter);
                        for (int j=0;j<bigMonth.length; j++) {
                            if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(bigMonth[j]))) {
                                spinnerDay.setSelection(j);
                            }
                        }
                        break;
                    case "2":
                        if ((year%400 == 0 && year%100 != 0) || year%4 == 0) {
                            Log.e(TAG, "Leap year!");
                            //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, FebMonthLeap);
                            spinnerDay.setAdapter(febMonthLeapAdapter);
                            for (int j=0;j<FebMonthLeap.length; j++) {
                                if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(FebMonthLeap[j]))) {
                                    spinnerDay.setSelection(j);
                                }
                            }
                        } else {
                            //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, FebMonthNormal);
                            spinnerDay.setAdapter(febMonthNormalAdapter);
                            for (int j=0;j<FebMonthNormal.length; j++) {
                                if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(FebMonthNormal[j]))) {
                                    spinnerDay.setSelection(j);
                                }
                            }
                        }
                        break;
                    case "4":
                    case "6":
                    case "9":
                    case "11":
                        //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, smallMonth);
                        spinnerDay.setAdapter(smallMonthDayAdapter);
                        for (int j=0;j<smallMonth.length; j++) {
                            if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(smallMonth[j]))) {
                                spinnerDay.setSelection(j);
                            }
                        }
                        break;
                    default:
                        break;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "get position "+position+", value = "+ spinnerMonth.getSelectedItem().toString());

                int year = Integer.valueOf(spinnerYear.getSelectedItem().toString());

                switch (spinnerMonth.getSelectedItem().toString())
                {
                    case "1":
                    case "3":
                    case "5":
                    case "7":
                    case "8":
                    case "10":
                    case "12":
                        //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, bigMonth);
                        spinnerDay.setAdapter(bigMonthDayAdapter);
                        for (int j=0;j<bigMonth.length; j++) {
                            if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(bigMonth[j]))) {
                                spinnerDay.setSelection(j);
                            }
                        }
                        break;
                    case "2":
                        if ((year%400 == 0 && year%100 != 0) || year%4 == 0) {
                            Log.e(TAG, "Leap year!");
                            //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, FebMonthLeap);
                            spinnerDay.setAdapter(febMonthLeapAdapter);
                            for (int j=0;j<FebMonthLeap.length; j++) {
                                if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(FebMonthLeap[j]))) {
                                    spinnerDay.setSelection(j);
                                }
                            }
                        } else {
                            //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, FebMonthNormal);
                            spinnerDay.setAdapter(febMonthNormalAdapter);
                            for (int j=0;j<FebMonthNormal.length; j++) {
                                if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(FebMonthNormal[j]))) {
                                    spinnerDay.setSelection(j);
                                }
                            }
                        }
                        break;
                    case "4":
                    case "6":
                    case "9":
                    case "11":
                        //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, smallMonth);
                        spinnerDay.setAdapter(smallMonthDayAdapter);
                        for (int j=0;j<smallMonth.length; j++) {
                            if (Integer.valueOf(AllFragment.requestMeetingStart.substring(6,8)).equals(Integer.valueOf(smallMonth[j]))) {
                                spinnerDay.setSelection(j);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearEndAdapter = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, yearlist);
        spinnerYearEnd.setAdapter(yearEndAdapter);

        for(int i=0; i<yearlist.length;i++) {
            if (AllFragment.requestMeetingEnd.substring(0,4).equals(yearlist[i])) {
                spinnerYearEnd.setSelection(i);
            }
        }

        monthEndAdapter = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, monthlist);
        spinnerMonthEnd.setAdapter(monthEndAdapter);

        /*for(int i=0; i<monthlist.length;i++) {
            if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(4,6)) == Integer.valueOf(monthlist[i].toString())) {
                spinnerMonthEnd.setSelection(i);
            }
        }*/
        spinnerMonthEnd.setSelection(Integer.valueOf(AllFragment.requestMeetingEnd.substring(4,6))-1);

        //set day selection
        bigMonthDayAdapterEnd = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, bigMonth);
        smallMonthDayAdapterEnd = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, smallMonth);
        febMonthNormalAdapterEnd = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, FebMonthNormal);
        febMonthLeapAdapterEnd = new ArrayAdapter<>(SetupRequestMeeting.this, R.layout.myspinner, FebMonthLeap);

        int yearEnd = Integer.valueOf(AllFragment.requestMeetingEnd.substring(0,4));
        switch (AllFragment.requestMeetingEnd.substring(4,6)) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, bigMonth);
                spinnerDayEnd.setAdapter(bigMonthDayAdapterEnd);
                for (int j=0;j<bigMonth.length; j++) {
                    if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(bigMonth[j]))) {
                        spinnerDayEnd.setSelection(j);
                    }
                }
                break;
            case "02":
                if ((yearEnd%400 == 0 && yearEnd%100 != 0) || yearEnd%4 == 0) {
                    Log.e(TAG, "Leap year!");
                    //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, FebMonthLeap);
                    spinnerDayEnd.setAdapter(febMonthLeapAdapterEnd);
                    for (int j=0;j<FebMonthLeap.length; j++) {
                        if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(FebMonthLeap[j]))) {
                            spinnerDayEnd.setSelection(j);
                        }
                    }
                } else {
                    //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, FebMonthNormal);
                    spinnerDayEnd.setAdapter(febMonthNormalAdapterEnd);
                    for (int j=0;j<FebMonthNormal.length; j++) {
                        if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(FebMonthNormal[j]))) {
                            spinnerDayEnd.setSelection(j);
                        }
                    }
                }
                break;
            case "04":
            case "06":
            case "09":
            case "11":
                //dayAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, smallMonth);
                spinnerDayEnd.setAdapter(smallMonthDayAdapterEnd);
                for (int j=0;j<smallMonth.length; j++) {
                    if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(smallMonth[j]))) {
                        spinnerDayEnd.setSelection(j);
                    }
                }
                break;
            default:
                break;
        }

        spinnerYearEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "get position "+position+", value = "+ spinnerYearEnd.getSelectedItem().toString());

                int year = Integer.valueOf(spinnerYearEnd.getSelectedItem().toString());

                switch (spinnerMonthEnd.getSelectedItem().toString())
                {
                    case "1":
                    case "3":
                    case "5":
                    case "7":
                    case "8":
                    case "10":
                    case "12":
                        //dayEndAdapter = new ArrayAdapter<>(SetupRequestJob.this, android.R.layout.simple_spinner_item, bigMonth);
                        spinnerDayEnd.setAdapter(bigMonthDayAdapterEnd);
                        for (int j=0;j<bigMonth.length; j++) {
                            if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(bigMonth[j]))) {
                                spinnerDayEnd.setSelection(j);
                            }
                        }
                        break;
                    case "2":
                        if ((year%400 == 0 && year%100 != 0) || year%4 == 0) {
                            Log.e(TAG, "Leap year!");
                            spinnerDayEnd.setAdapter(febMonthLeapAdapterEnd);
                            for (int j=0;j<FebMonthLeap.length; j++) {
                                if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(FebMonthLeap[j]))) {
                                    spinnerDayEnd.setSelection(j);
                                }
                            }
                        } else {
                            spinnerDayEnd.setAdapter(febMonthNormalAdapterEnd);
                            for (int j=0;j<FebMonthNormal.length; j++) {
                                if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(FebMonthNormal[j]))) {
                                    spinnerDayEnd.setSelection(j);
                                }
                            }
                        }
                        break;
                    case "4":
                    case "6":
                    case "9":
                    case "11":
                        spinnerDayEnd.setAdapter(smallMonthDayAdapterEnd);
                        for (int j=0;j<smallMonth.length; j++) {
                            if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(smallMonth[j]))) {
                                spinnerDayEnd.setSelection(j);
                            }
                        }
                        break;
                    default:
                        break;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMonthEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "get position "+position+", value = "+ spinnerMonthEnd.getSelectedItem().toString());

                int year = Integer.valueOf(spinnerYearEnd.getSelectedItem().toString());

                switch (spinnerMonthEnd.getSelectedItem().toString())
                {
                    case "1":
                    case "3":
                    case "5":
                    case "7":
                    case "8":
                    case "10":
                    case "12":
                        spinnerDayEnd.setAdapter(bigMonthDayAdapterEnd);
                        for (int j=0;j<bigMonth.length; j++) {
                            if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(bigMonth[j]))) {
                                spinnerDayEnd.setSelection(j);
                            }
                        }
                        break;
                    case "2":
                        if ((year%400 == 0 && year%100 != 0) || year%4 == 0) {
                            Log.e(TAG, "Leap year!");
                            spinnerDayEnd.setAdapter(febMonthLeapAdapterEnd);
                            for (int j=0;j<FebMonthLeap.length; j++) {
                                if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(FebMonthLeap[j]))) {
                                    spinnerDayEnd.setSelection(j);
                                }
                            }
                        } else {
                            spinnerDayEnd.setAdapter(febMonthNormalAdapterEnd);
                            for (int j=0;j<FebMonthNormal.length; j++) {
                                if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(FebMonthNormal[j]))) {
                                    spinnerDayEnd.setSelection(j);
                                }
                            }
                        }
                        break;
                    case "4":
                    case "6":
                    case "9":
                    case "11":
                        spinnerDayEnd.setAdapter(smallMonthDayAdapterEnd);
                        for (int j=0;j<smallMonth.length; j++) {
                            if (Integer.valueOf(AllFragment.requestMeetingEnd.substring(6,8)).equals(Integer.valueOf(smallMonth[j]))) {
                                spinnerDayEnd.setSelection(j);
                            }
                        }
                        break;
                    default:
                        break;
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
        NumberFormat f = new DecimalFormat("00");


        /*AllFragment.requestMeetingStart = spinnerYear.getSelectedItem().toString()+
                f.format(Long.valueOf(spinnerMonth.getSelectedItem().toString()))+
                f.format(Long.valueOf(spinnerDay.getSelectedItem().toString()));*/
        AllFragment.requestMeetingStart = spinnerYear.getSelectedItem().toString()+
                f.format(Integer.valueOf(spinnerMonth.getSelectedItemPosition()+1))+
                f.format(Integer.valueOf(spinnerDay.getSelectedItemPosition()+1));

        AllFragment.requestMeetingEnd = spinnerYearEnd.getSelectedItem().toString()+
                f.format(Integer.valueOf(spinnerMonthEnd.getSelectedItemPosition()+1))+
                f.format(Integer.valueOf(spinnerDayEnd.getSelectedItemPosition()+1));

        finish();
    }

    /*public void toast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }*/
}
