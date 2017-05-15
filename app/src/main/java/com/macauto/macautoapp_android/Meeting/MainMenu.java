package com.macauto.macautoapp_android.Meeting;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.macauto.macautoapp_android.Data.Constants;

import com.macauto.macautoapp_android.R;
import com.macauto.macautoapp_android.Receiver.Receive_BootCompleted;
import com.macauto.macautoapp_android.Meeting.Service.AlarmService;
import com.macauto.macautoapp_android.TopMenu;

import java.util.Calendar;
public class MainMenu extends AppCompatActivity {

    private static final String TAG = MainMenu.class.getName();

    private static final String TAB_1_TAG = "tab_1";
    private static final String TAB_2_TAG = "tab_2";
    private static final String TAB_3_TAG = "tab_3";
    private static final String TAB_4_TAG = "tab_4";
    //private static final String TAB_5_TAG = "tab_5";

    //private FragmentTabHost mTabHost;

    static SharedPreferences pref ;
    static SharedPreferences.Editor editor;
    private static final String FILE_NAME = "Preference";
    private  Context context;
    public static ActionBar actionBar;
    public static MenuItem item_find, item_search;
    public  SearchView searchView;
    public static String current_tab = "tab_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        setTheme(R.style.macauto);

        //for action bar
        actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_24dp);
            //actionBar.setTitle(getResources().getString(R.string.macauto_tab_personal_meeting));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                actionBar.setTitle(Html.fromHtml(context.getResources().getString(R.string.macauto_tab_personal_meeting),Html.FROM_HTML_MODE_LEGACY));
            } else {
                actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getResources().getString(R.string.macauto_tab_personal_meeting) + "</font>"));
            }
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3191a7")));
        }



        setContentView(R.layout.main_menu);

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


        InitView();



        /*if (!isMyServiceRunning(AlarmService.class)) {
            Log.d(TAG, "MyService is not running");
            Intent serviceIntent = new Intent(MainMenu.this, AlarmService.class);
            startService(serviceIntent);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 3);

            Intent alarmService = new Intent(this, Receive_BootCompleted.class);
            alarmService.setAction(Constants.ACTION.GET_START_ALARM_SERVICE_ACTION);
            alarmService.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);

            PendingIntent pending = PendingIntent.getBroadcast(this.getApplicationContext(), 1, alarmService, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Log.e(TAG, "getTimeInMillis = "+String.valueOf(cal.getTimeInMillis())+", System time"+ String.valueOf(System.currentTimeMillis()));
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT ) {


                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending);
                //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(), 5 * 1000, pending);
            }
            else {

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending);
                //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(), 5 * 1000, pending);
            }
        } else {
            Log.d(TAG, "MyService is running");
        }*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);



        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);


        item_search = menu.findItem(R.id.action_search);
        item_find = menu.findItem(R.id.action_search_setup);



        item_find.setVisible(false);

        try {
            //SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search_keeper));
            searchView.setOnQueryTextListener(queryListener);
        }catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_search_setup:
                if (current_tab.equals("tab_2")) {
                    AllFragment.onSetupPress = true;
                    intent = new Intent(context, SetupRequestMeeting.class);
                    startActivity(intent);
                } else if (current_tab.equals("tab_3")) {
                    AssignmentFragment.onSetupPress = true;
                    intent = new Intent(context, SetupRequestJob.class);
                    startActivity(intent);
                }
                //intent = new Intent(Password_Keeper.this, Password_Keeper_Detail.class);
                //startActivity(intent);
                //finish();
                break;
            case android.R.id.home:
                AlertDialog.Builder confirmdialog = new AlertDialog.Builder(MainMenu.this);
                confirmdialog.setIcon(R.drawable.ic_exit_to_app_black_48dp);
                confirmdialog.setTitle(getResources().getString(R.string.macauto_more_logout_title));
                confirmdialog.setMessage(getResources().getString(R.string.macauto_more_logout_descrypt));
                confirmdialog.setPositiveButton(getResources().getString(R.string.macauto_confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putBoolean("LOGIN", false);
                        editor.apply();

                        Intent intent = new Intent(MainMenu.this, Login.class);
                        startActivity(intent);
                        finish();

                    }
                });
                confirmdialog.setNegativeButton(getResources().getString(R.string.macauto_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                confirmdialog.show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    private void InitView() {
        FragmentTabHost mTabHost;

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);


        mTabHost.addTab(setIndicator(MainMenu.this, mTabHost.newTabSpec(TAB_1_TAG),
                R.drawable.tab_indicator_gen, getResources().getString(R.string.macauto_tab_personal_meeting), R.drawable.personal), PersonalFragment.class, null);
        mTabHost.addTab(setIndicator(MainMenu.this, mTabHost.newTabSpec(TAB_2_TAG),
                R.drawable.tab_indicator_gen, getResources().getString(R.string.macauto_tab_all_meeting), R.drawable.group), AllFragment.class, null);
        mTabHost.addTab(setIndicator(MainMenu.this, mTabHost.newTabSpec(TAB_3_TAG),
                R.drawable.tab_indicator_gen, getResources().getString(R.string.macauto_tab_assignment), R.drawable.assignement), AssignmentFragment.class, null);
        mTabHost.addTab(setIndicator(MainMenu.this, mTabHost.newTabSpec(TAB_4_TAG),
                R.drawable.tab_indicator_gen, getResources().getString(R.string.macauto_tab_more), R.drawable.more), MoreFragment.class, null);
       // mTabHost.addTab(setIndicator(MainMenu.this, mTabHost.newTabSpec(TAB_5_TAG),
                //R.drawable.tab_indicator_gen, getResources().getString(R.string.macauto_tab_more), R.drawable.more), MoreFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.e("===>Selected Tab", "Im currently in tab with index::" + tabId);
                current_tab = tabId;


                if (tabId.equals("tab_1") || tabId.equals("tab_4")) {
                    if (tabId.equals("tab_4")) {
                        item_search.setVisible(false);
                    } else {
                        item_search.setVisible(true);
                    }
                    item_find.setVisible(false);
                } else {
                    item_search.setVisible(true);
                    item_find.setVisible(true);
                }



                switch (tabId) {
                    case "tab_1":
                        actionBar.show();
                        //actionBar.setTitle(getResources().getString(R.string.macauto_tab_personal_meeting));
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            actionBar.setTitle(Html.fromHtml(context.getResources().getString(R.string.macauto_tab_personal_meeting),Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getResources().getString(R.string.macauto_tab_personal_meeting) + "</font>"));
                        }
                        break;
                    case "tab_2":
                        actionBar.show();
                        //actionBar.setTitle(getResources().getString(R.string.macauto_tab_all_meeting));
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            actionBar.setTitle(Html.fromHtml(context.getResources().getString(R.string.macauto_tab_all_meeting),Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getResources().getString(R.string.macauto_tab_all_meeting) + "</font>"));
                        }
                        break;
                    case "tab_3":
                        actionBar.show();
                        //actionBar.setTitle(getResources().getString(R.string.macauto_tab_assignment));
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            actionBar.setTitle(Html.fromHtml(context.getResources().getString(R.string.macauto_tab_assignment),Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getResources().getString(R.string.macauto_tab_assignment) + "</font>"));
                        }
                        break;
                    case "tab_4":
                        actionBar.hide();
                        //actionBar.setTitle(getResources().getString(R.string.macauto_tab_more));
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            actionBar.setTitle(Html.fromHtml(context.getResources().getString(R.string.macauto_tab_more),Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getResources().getString(R.string.macauto_tab_more) + "</font>"));
                        }

                        break;
                    default:
                        actionBar.setTitle("");
                        break;

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder confirmdialog = new AlertDialog.Builder(MainMenu.this);
        confirmdialog.setIcon(R.drawable.ic_exit_to_app_black_48dp);
        confirmdialog.setTitle(getResources().getString(R.string.macauto_back_to_menu_title));
        confirmdialog.setMessage(getResources().getString(R.string.macauto_back_to_menu_description));
        confirmdialog.setPositiveButton(getResources().getString(R.string.macauto_confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                /*pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
                editor = pref.edit();
                editor.putBoolean("LOGIN", false);
                editor.apply();*/

                Intent intent = new Intent(MainMenu.this, TopMenu.class);
                startActivity(intent);
                finish();

            }
        });
        confirmdialog.setNegativeButton(getResources().getString(R.string.macauto_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        confirmdialog.show();
    }

    private TabHost.TabSpec setIndicator(Context ctx, TabHost.TabSpec spec,
                                         int resid, String string, int genresIcon) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.tab_item, null);
        v.setBackgroundResource(resid);
        TextView tv = (TextView)v.findViewById(R.id.txt_tabtxt);
        ImageView img = (ImageView)v.findViewById(R.id.img_tabtxt);

        tv.setText(string);
        img.setBackgroundResource(genresIcon);
        return spec.setIndicator(v);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    final private android.support.v7.widget.SearchView.OnQueryTextListener queryListener = new android.support.v7.widget.SearchView.OnQueryTextListener() {
        //searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Intent intent;
            switch (current_tab) {
                case "tab_1":
                    Log.e(TAG, "tab_1 search ");
                    //ArrayList<MeetingListItem> list = new ArrayList<>();
                    PersonalFragment.sortedMeetingList.clear();
                    if (!newText.equals("")) {



                        //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                        for (int i = 0; i < PersonalFragment.meetingList.size(); i++) {
                            if (PersonalFragment.meetingList.get(i).getSubject().contains(newText)) {
                                PersonalFragment.sortedMeetingList.add(PersonalFragment.meetingList.get(i));
                            } else if (PersonalFragment.meetingList.get(i).getRoom_name().contains(newText)) {
                                PersonalFragment.sortedMeetingList.add(PersonalFragment.meetingList.get(i));
                            } else if (PersonalFragment.meetingList.get(i).getStart_date().contains(newText)) {
                                PersonalFragment.sortedMeetingList.add(PersonalFragment.meetingList.get(i));
                            } else if (PersonalFragment.meetingList.get(i).getMaster().contains(newText)) {
                                PersonalFragment.sortedMeetingList.add(PersonalFragment.meetingList.get(i));
                            } else if (PersonalFragment.meetingList.get(i).getEmp_name().contains(newText)) {
                                PersonalFragment.sortedMeetingList.add(PersonalFragment.meetingList.get(i));
                            }
                        }

                        //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                        //listView.setAdapter(passwordKeeperArrayAdapter);

                    } else {
                        //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                        for (int i = 0; i < PersonalFragment.meetingList.size(); i++) {
                            PersonalFragment.sortedMeetingList.add(PersonalFragment.meetingList.get(i));
                        }

                        //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                        //listView.setAdapter(passwordKeeperArrayAdapter);
                    }

                    //PersonalFragment.meetingArrayAdapter = new MeetingArrayAdapter(context, R.layout.meeting_list_item, list);
                    //PersonalFragment.resetAdapter(list);
                    //PersonalFragment.listView.setAdapter(PersonalFragment.meetingArrayAdapter);
                    intent = new Intent(Constants.ACTION.GET_PERSONAL_MEETING_LIST_SORT_COMPLETE);
                    sendBroadcast(intent);
                    break;
                case "tab_2":
                    Log.e(TAG, "tab_2 search ");
                    //ArrayList<MeetingListItem> list = new ArrayList<>();
                    AllFragment.sortedMeetingList.clear();
                    if (!newText.equals("")) {



                        //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                        for (int i = 0; i < AllFragment.meetingList.size(); i++) {
                            if (AllFragment.meetingList.get(i).getSubject().contains(newText)) {
                                AllFragment.sortedMeetingList.add(AllFragment.meetingList.get(i));
                            } else if (AllFragment.meetingList.get(i).getRoom_name().contains(newText)) {
                                AllFragment.sortedMeetingList.add(AllFragment.meetingList.get(i));
                            } else if (AllFragment.meetingList.get(i).getStart_date().contains(newText)) {
                                AllFragment.sortedMeetingList.add(AllFragment.meetingList.get(i));
                            } else if (AllFragment.meetingList.get(i).getMaster().contains(newText)) {
                                AllFragment.sortedMeetingList.add(AllFragment.meetingList.get(i));
                            } else if (AllFragment.meetingList.get(i).getEmp_name().contains(newText)) {
                                AllFragment.sortedMeetingList.add(AllFragment.meetingList.get(i));
                            }
                        }

                        //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                        //listView.setAdapter(passwordKeeperArrayAdapter);

                    } else {
                        //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                        for (int i = 0; i < AllFragment.meetingList.size(); i++) {
                            AllFragment.sortedMeetingList.add(AllFragment.meetingList.get(i));
                        }

                        //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                        //listView.setAdapter(passwordKeeperArrayAdapter);
                    }

                    //meetingArrayAdapter = new MeetingArrayAdapter(context, R.layout.meeting_list_item, list);
                    //AllFragment.resetAdapter(list);
                    //AllFragment.listView.setAdapter(AllFragment.meetingArrayAdapter);
                    intent = new Intent(Constants.ACTION.GET_ALL_MEETING_LIST_SORT_COMPLETE);
                    sendBroadcast(intent);
                    break;
                case "tab_3":
                    Log.e(TAG, "tab_3 search ");
                    //ArrayList<JobListItem> list = new ArrayList<>();
                    AssignmentFragment.sortJobList.clear();
                    if (!newText.equals("")) {



                        //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                        for (int i = 0; i < AssignmentFragment.jobList.size(); i++) {
                            if (AssignmentFragment.jobList.get(i).getResult().contains(newText)) {
                                AssignmentFragment.sortJobList.add(AssignmentFragment.jobList.get(i));
                            } else if (AssignmentFragment.jobList.get(i).getEnd_date().contains(newText)) {
                                AssignmentFragment.sortJobList.add(AssignmentFragment.jobList.get(i));
                            } else if (AssignmentFragment.jobList.get(i).getDept_name().contains(newText)) {
                                AssignmentFragment.sortJobList.add(AssignmentFragment.jobList.get(i));
                            } else if (AssignmentFragment.jobList.get(i).getEmp_name().contains(newText)) {
                                AssignmentFragment.sortJobList.add(AssignmentFragment.jobList.get(i));
                            }
                        }

                        //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                        //listView.setAdapter(passwordKeeperArrayAdapter);

                    } else {
                        //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                        for (int i = 0; i < AssignmentFragment.jobList.size(); i++) {
                            AssignmentFragment.sortJobList.add(AssignmentFragment.jobList.get(i));
                        }

                        //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                        //listView.setAdapter(passwordKeeperArrayAdapter);
                    }

                    //AssignmentFragment.resetAdapter(list);
                    //AssignmentFragment.listView.setAdapter(AssignmentFragment.jobArrayAdapter);
                    intent = new Intent(Constants.ACTION.GET_PERSONAL_JOB_LIST_SORT_COMPLETE);
                    sendBroadcast(intent);
                    break;
            }





            return false;
        }
    };
}
