package com.macauto.macautoapp_android.Meeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.macauto.macautoapp_android.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MeetingShow extends Activity {
    private static final String TAG = MeetingShow.class.getName();

    //private ListView listView;
    //private SimpleAdapter simpleAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.meeting_show);

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

        ListView listView = (ListView) findViewById(R.id.listViewShow);

        Intent intent = getIntent();


        String room_no = intent.getStringExtra("ROOM_NO");
        String master = intent.getStringExtra("MASTER");
        String emp_name = intent.getStringExtra("EMP_NAME");
        String dept_name = intent.getStringExtra("DEPT_NAME");
        String room_name = intent.getStringExtra("ROOM_NAME");
        String meeting_no = intent.getStringExtra("MEETING_NO");
        String start_date = intent.getStringExtra("START_DATE");
        String end_date = intent.getStringExtra("END_DATE");
        String approve = intent.getStringExtra("APPROVE");
        String approve_date = intent.getStringExtra("APPROVE_DATE");
        String subject = intent.getStringExtra("SUBJECT");
        String bad_sp = intent.getStringExtra("BAD_SP");
        String memo = intent.getStringExtra("MEMO");
        String meeting_type = intent.getStringExtra("MEETING_TYPE");
        String recorder = intent.getStringExtra("RECORDER");

        Log.i(TAG, "room_no = "+room_no);
        Log.i(TAG, "master = "+master);
        Log.i(TAG, "emp_name = "+emp_name);
        Log.i(TAG, "dept_name = "+dept_name);
        Log.i(TAG, "room_name = "+room_name);
        Log.i(TAG, "meeting_no = "+meeting_no);
        Log.i(TAG, "start_date = "+start_date);
        Log.i(TAG, "end_date = "+end_date);
        Log.i(TAG, "approve = "+approve);
        Log.i(TAG, "approve_date = "+approve_date);
        Log.i(TAG, "subject = "+subject);
        Log.i(TAG, "bad_sp = "+bad_sp);
        Log.i(TAG, "memo = "+memo);
        Log.i(TAG, "meeting_type = "+meeting_type);
        Log.i(TAG, "recorder = "+recorder);

        Calendar c = Calendar.getInstance();

        Date start_date_compare = null;
        Date end_date_compare = null;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.TAIWAN);
        try {
            start_date_compare = formatter.parse(start_date);
            end_date_compare = formatter.parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        List<Map<String, String>> items = new ArrayList<>();

        Map<String, String> item1 = new HashMap<>();
        item1.put("show_header", getResources().getString(R.string.macauto_meeting_show_id));
        item1.put("show_msg", meeting_no);
        items.add(item1);

        Map<String, String> item2 = new HashMap<>();
        item2.put("show_header", getResources().getString(R.string.macauto_meeting_show_start_time));
        item2.put("show_msg", start_date);
        items.add(item2);

        Map<String, String> item3 = new HashMap<>();
        item3.put("show_header", getResources().getString(R.string.macauto_meeting_show_end_time));
        item3.put("show_msg", end_date);
        items.add(item3);

        Map<String, String> item4 = new HashMap<>();
        item4.put("show_header", getResources().getString(R.string.macauto_meeting_show_subject));
        item4.put("show_msg", subject);
        items.add(item4);

        Map<String, String> item5 = new HashMap<>();
        item5.put("show_header", getResources().getString(R.string.macauto_meeting_show_dept_name));
        item5.put("show_msg", dept_name);
        items.add(item5);

        Map<String, String> item6 = new HashMap<>();
        item6.put("show_header", getResources().getString(R.string.macauto_meeting_show_master));
        item6.put("show_msg", master);
        items.add(item6);

        Map<String, String> item7 = new HashMap<>();
        item7.put("show_header", getResources().getString(R.string.macauto_meeting_show_room_name));
        item7.put("show_msg", room_name);
        items.add(item7);

        Map<String, String> item8 = new HashMap<>();
        item8.put("show_header", getResources().getString(R.string.macauto_meeting_show_type));
        item8.put("show_msg", meeting_type);
        items.add(item8);

        Map<String, String> item9 = new HashMap<>();
        item9.put("show_header", getResources().getString(R.string.macauto_meeting_members));
        item9.put("show_msg", emp_name);
        items.add(item9);

        Map<String, String> item10 = new HashMap<>();
        item10.put("show_header", getResources().getString(R.string.macauto_meeting_show_status));

        if (bad_sp.equals("Y"))
            item10.put("show_msg", getResources().getString(R.string.macauto_cancel));
            //holder.cancel.setText(Html.fromHtml("<font color='#FF0000'>" + context.getResources().getString(R.string.macauto_cancel) + "</font>"));
        else {
            if (start_date_compare != null && end_date_compare != null) {
                if (c.getTimeInMillis() > start_date_compare.getTime() && c.getTimeInMillis() < end_date_compare.getTime()) {
                    item10.put("show_msg", getResources().getString(R.string.macauto_going));
                    //holder.cancel.setText(Html.fromHtml("<font color='#0099FF'>" + context.getResources().getString(R.string.macauto_going) + "</font>"));
                } else if (c.getTimeInMillis() > end_date_compare.getTime()) {
                    item10.put("show_msg", getResources().getString(R.string.macauto_closed));
                    //holder.cancel.setText(Html.fromHtml("<font color='#FF8C00'>" + context.getResources().getString(R.string.macauto_closed) + "</font>"));
                } else
                    item10.put("show_msg", getResources().getString(R.string.macauto_on_time));
                //holder.cancel.setText(Html.fromHtml("<font color='#00FF00'>" + context.getResources().getString(R.string.macauto_on_time) + "</font>"));
                //item9.put("show_msg", bad_sp);
            }
        }
        items.add(item10);

        if (memo != null && memo.length() > 0) {

            Map<String, String> item11 = new HashMap<>();
            item11.put("show_header", getResources().getString(R.string.macauto_meeting_show_memo));
            item11.put("show_msg", memo);
            items.add(item11);
        }

        /*Map<String, String> item10 = new HashMap<String, String>();
        item10.put("show_header", getResources().getString(R.string.macauto_meeting_show_memo));
        item10.put("show_msg", "");
        items.add(item10);

        Map<String, String> item11 = new HashMap<String, String>();
        item11.put("show_header", "");
        item11.put("show_msg", memo);
        items.add(item11);*/

        /*Map<String, String> item1 = new HashMap<String, String>();
        item1.put("show_header", "room_no");
        item1.put("show_msg", room_no);
        items.add(item1);

        Map<String, String> item2 = new HashMap<String, String>();
        item2.put("show_header", "master");
        item2.put("show_msg", master);
        items.add(item2);

        Map<String, String> item9 = new HashMap<String, String>();
        item9.put("show_header", "approve");
        item9.put("show_msg", approve);
        items.add(item9);

        Map<String, String> item10 = new HashMap<String, String>();
        item10.put("show_header", "approve_date");
        item10.put("show_msg", approve_date);
        items.add(item10);



        Map<String, String> item15 = new HashMap<String, String>();
        item15.put("show_header", "recorder");
        item15.put("show_msg", recorder);
        items.add(item15);*/

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                items, R.layout.meeting_show_item, new String[]{"show_header", "show_msg"},
                new int[]{R.id.show_header, R.id.show_msg});
        listView.setAdapter(simpleAdapter);

        //Log.i(TAG, "item[1] = "+listView.getAdapter().);

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
