package com.macauto.macautoapp_android.Meeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.macauto.macautoapp_android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JobShow extends Activity {
    //private static final String TAG = JobShow.class.getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.job_show);

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

        ListView listView = (ListView) findViewById(R.id.jobListViewShow);

        Intent intent = getIntent();

        /*
                intent.putExtra("JOB_ID", item.getId());
                intent.putExtra("JOB_MEETING_NO", item.getMeeting_no());
                intent.putExtra("JOB_ISSUE", item.getIssue());
                intent.putExtra("JOB_RESULT", item.getResult());
                intent.putExtra("JOB_LEADER", item.getLeader());
                intent.putExtra("JOB_START_DATE",  item.getStart_date());
                intent.putExtra("JOB_END_DATE", item.getEnd_date());
                intent.putExtra("JOB_REAL_END_DATE", item.getReal_end_date());
                intent.putExtra("JOB_CANCEL_SP", item.getCancel_sp());
                intent.putExtra("JOB_CLOSE_SP", item.getClose_sp());
                intent.putExtra("JOB_EMP_NAME", item.getEmp_name());
                intent.putExtra("JOB_DELAY_TIMES", item.getDelay_times());
                intent.putExtra("JOB_DEPT_NAME", item.getDept_name());
                intent.putExtra("JOB_DEPT_NO", item.getDept_no());
                intent.putExtra("JOB_FIRST_END_DATE", item.getFirst_end_date());
                intent.putExtra("JOB_AUDITOR", item.getAuditor());
                intent.putExtra("JOB_AUDITOR_NAME", item.getAuditor_name());
                intent.putExtra("JOB_EXTN", item.getExtn());
                intent.putExtra("JOB_AUDITOR_EXTN", item.getAuditor_extn());
                intent.putExtra("JOB_DELAY_WAIT", item.getDelay_wait());
                intent.putExtra("JOB_FMEA_TYPE", item.getFmea_type());
                intent.putExtra("JOB_FMEA_NO", item.getFmea_no());
         */


        //String job_id = getintent.getStringExtra("JOB_ID");
        String job_meeting_no = intent.getStringExtra("JOB_MEETING_NO");
        //String job_issue = getintent.getStringExtra("JOB_ISSUE");
        String job_result = intent.getStringExtra("JOB_RESULT");
        //String job_leader = getintent.getStringExtra("JOB_LEADER");
        String job_start_date = intent.getStringExtra("JOB_START_DATE");
        String job_end_date = intent.getStringExtra("JOB_END_DATE");
        String job_real_end_date = intent.getStringExtra("JOB_REAL_END_DATE");
        //String job_cancel_sp = getintent.getStringExtra("JOB_CANCEL_SP");
        //String job_close_sp = getintent.getStringExtra("JOB_CLOSE_SP");
        String job_emp_name = intent.getStringExtra("JOB_EMP_NAME");
        //String job_delay_time = getintent.getStringExtra("JOB_DELAY_TIMES");
        //String job_dept_name = getintent.getStringExtra("JOB_DEPT_NAME");
        //String job_dept_no = getintent.getStringExtra("JOB_DEPT_NO");
        //String job_first_end_date = getintent.getStringExtra("JOB_FIRST_END_DATE");
        //String job_auditor = getintent.getStringExtra("JOB_AUDITOR");
        String job_auditor_name = intent.getStringExtra("JOB_AUDITOR_NAME");
        //String job_extn = getintent.getStringExtra("JOB_EXTN");
        //String job_auditor_extn = getintent.getStringExtra("JOB_AUDITOR_EXTN");
        String job_delay_wait = intent.getStringExtra("JOB_DELAY_WAIT");
        //String job_fmea_type = getintent.getStringExtra("JOB_FMEA_TYPE");
        //String job_fmea_no = getintent.getStringExtra("JOB_FMEA_NO");

        /*Log.i(TAG, "room_no = "+room_no);
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
        Log.i(TAG, "recorder = "+recorder);*/

        /*Calendar c = Calendar.getInstance();

        Date start_date_compare = null;
        Date end_date_compare = null;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.TAIWAN);
        try {
            start_date_compare = formatter.parse(start_date);
            end_date_compare = formatter.parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


        /*
        String job_id = getintent.getStringExtra("JOB_ID");
        String job_meeting_no = getintent.getStringExtra("JOB_MEETING_NO");
        String job_issue = getintent.getStringExtra("JOB_ISSUE");
        String job_result = getintent.getStringExtra("JOB_RESULT");
        String job_leader = getintent.getStringExtra("JOB_LEADER");
        String job_start_date = getintent.getStringExtra("JOB_START_DATE");
        String job_end_date = getintent.getStringExtra("JOB_END_DATE");
        String job_real_end_date = getintent.getStringExtra("JOB_REAL_END_DATE");
        String job_cancel_sp = getintent.getStringExtra("JOB_CANCEL_SP");
        String job_close_sp = getintent.getStringExtra("JOB_CLOSE_SP");
        String job_emp_name = getintent.getStringExtra("JOB_EMP_NAME");
        String job_delay_time = getintent.getStringExtra("JOB_DELAY_TIMES");
        String job_dept_name = getintent.getStringExtra("JOB_DEPT_NAME");
        String job_dept_no = getintent.getStringExtra("JOB_DEPT_NO");
        String job_first_end_date = getintent.getStringExtra("JOB_FIRST_END_DATE");
        String job_auditor = getintent.getStringExtra("JOB_AUDITOR");
        String job_auditor_name = getintent.getStringExtra("JOB_AUDITOR_NAME");
        String job_extn = getintent.getStringExtra("JOB_EXTN");
        String job_auditor_extn = getintent.getStringExtra("JOB_AUDITOR_EXTN");
        String job_fmea_type = getintent.getStringExtra("JOB_FMEA_TYPE");
        String job_fmea_no = getintent.getStringExtra("JOB_FMEA_NO");
         */

        List<Map<String, String>> items = new ArrayList<>();

        /*Map<String, String> item1 = new HashMap<>();
        item1.put("show_header", "ID");
        item1.put("show_msg", job_id);
        items.add(item1);*/

        Map<String, String> item2 = new HashMap<>();
        item2.put("show_header", getResources().getString(R.string.macauto_job_show_meeting_no));
        item2.put("show_msg", job_meeting_no);
        items.add(item2);

        Map<String, String> item6 = new HashMap<>();
        item6.put("show_header", getResources().getString(R.string.macauto_job_show_start_date));
        String meeting_date_splitter[] = job_start_date.split(" ");
        item6.put("show_msg", meeting_date_splitter[0]);
        items.add(item6);

        /*Map<String, String> item3 = new HashMap<>();
        item3.put("show_header", "Issue");
        item3.put("show_msg", job_issue);
        items.add(item3);*/

        Map<String, String> item4 = new HashMap<>();
        item4.put("show_header", getResources().getString(R.string.macauto_job_show_result));
        item4.put("show_msg", job_result);
        items.add(item4);

        /*Map<String, String> item5 = new HashMap<>();
        item5.put("show_header", "Leader");
        item5.put("show_msg", job_leader);
        items.add(item5);*/
        Map<String, String> item11= new HashMap<>();
        item11.put("show_header", getResources().getString(R.string.macauto_job_show_emp_name));
        item11.put("show_msg", job_emp_name);
        items.add(item11);

        Map<String, String> item7 = new HashMap<>();
        item7.put("show_header", getResources().getString(R.string.macauto_job_show_end_date));
        item7.put("show_msg", job_end_date);
        items.add(item7);

        Map<String, String> item8 = new HashMap<>();
        item8.put("show_header", getResources().getString(R.string.macauto_job_show_real_end_date));
        item8.put("show_msg", job_real_end_date);
        items.add(item8);

        /*Map<String, String> item9= new HashMap<>();
        item9.put("show_header", "Cancel sp");
        item9.put("show_msg", job_cancel_sp);
        items.add(item9);

        Map<String, String> item10= new HashMap<>();
        item10.put("show_header", "Close sp");
        item10.put("show_msg", job_close_sp);
        items.add(item10);



        Map<String, String> item12= new HashMap<>();
        item12.put("show_header", "Delay time");
        item12.put("show_msg", job_delay_time);
        items.add(item12);

        Map<String, String> item13= new HashMap<>();
        item13.put("show_header", "Dept name");
        item13.put("show_msg", job_dept_name);
        items.add(item13);

        Map<String, String> item14= new HashMap<>();
        item14.put("show_header", "Dept no");
        item14.put("show_msg", job_dept_no);
        items.add(item14);

        Map<String, String> item15= new HashMap<>();
        item15.put("show_header", "First end date");
        item15.put("show_msg", job_first_end_date);
        items.add(item15);

        Map<String, String> item16= new HashMap<>();
        item16.put("show_header", "Auditor");
        item16.put("show_msg", job_auditor);
        items.add(item16);*/



        Map<String, String> item17= new HashMap<>();
        item17.put("show_header", getResources().getString(R.string.macauto_job_show_auditor_name));
        item17.put("show_msg", job_auditor_name);
        items.add(item17);

        if (job_delay_wait.equals("1")) {
            Map<String, String> item18= new HashMap<>();
            item18.put("show_header", getResources().getString(R.string.macauto_job_delay_wait));
            item18.put("show_msg", getResources().getString(R.string.macauot_job_show_wait_for_check));
            items.add(item18);
        }



        /*Map<String, String> item18= new HashMap<>();
        item18.put("show_header", "Extn");
        item18.put("show_msg", job_extn);
        items.add(item18);

        Map<String, String> item19= new HashMap<>();
        item19.put("show_header", "Auditor extn");
        item19.put("show_msg", job_auditor_extn);
        items.add(item19);

        Map<String, String> item20= new HashMap<>();
        item20.put("show_header", "Fmea type");
        item20.put("show_msg", job_fmea_type);
        items.add(item20);

        Map<String, String> item21= new HashMap<>();
        item21.put("show_header", "Fmea no");
        item21.put("show_msg", job_fmea_no);
        items.add(item21);
        */
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
                items, R.layout.job_show_item, new String[]{"show_header", "show_msg"},
                new int[]{R.id.job_show_header, R.id.job_show_msg});
        listView.setAdapter(simpleAdapter);

        //Log.i(TAG, "item[1] = "+listView.getAdapter().);

    }
}

