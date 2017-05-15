package com.macauto.macautoapp_android.Meeting;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.macauto.macautoapp_android.Data.Constants;
import com.macauto.macautoapp_android.Meeting.Data.JobArrayAdapter;
import com.macauto.macautoapp_android.Meeting.Data.JobListItem;
import com.macauto.macautoapp_android.R;
import com.macauto.macautoapp_android.Meeting.Service.GetJobService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AssignmentFragment extends Fragment {
    private static final String TAG = AssignmentFragment.class.getName();

    private Context context;
    public ListView listView;
    //private Button btnSetup;
    //private ImageView imgSetup;
    //private SearchView searchView;
    ProgressDialog loadDialog = null;

    public static ArrayList<JobListItem> jobList = new ArrayList<>();
    public static ArrayList<JobListItem> sortJobList = new ArrayList<>();

    public JobArrayAdapter jobArrayAdapter;
    private static BroadcastReceiver mReceiver = null;
    private static boolean isRegister = false;
    private static boolean onCreate = false;
    public static boolean onSetupPress = false;
    public static String requestJobStart;
    public static String requestJobEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        Log.e(TAG, "onCreateView");

        Calendar c = Calendar.getInstance();
        Calendar future_c = Calendar.getInstance();

        NumberFormat f = new DecimalFormat("00");
        long current_time = c.getTimeInMillis();
        future_c.setTimeInMillis(current_time+86400*7*1000);

        requestJobStart = String.valueOf(c.get(Calendar.YEAR))+f.format(c.get(Calendar.MONTH)+1)+f.format(c.get(Calendar.DAY_OF_MONTH));
        requestJobEnd = String.valueOf(future_c.get(Calendar.YEAR))+f.format(future_c.get(Calendar.MONTH)+1)+f.format(future_c.get(Calendar.DAY_OF_MONTH));

        onCreate = true;

        context = getContext();
        IntentFilter filter;

        listView = (ListView) view.findViewById(R.id.listViewAssignment);
        //btnSetup = (Button) view.findViewById(R.id.btnSetup);
        //imgSetup = (ImageView) view.findViewById(R.id.imgSetupAssignment);
        listView.setTextFilterEnabled(true);
        //SearchView searchView = (SearchView) view.findViewById(R.id.searchViewAssignment);
        //searchView.setSubmitButtonEnabled(false);


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase(Constants.ACTION.GET_PERSONAL_JOB_LIST_COMPLETE)) {
                    Log.d(TAG, "receive brocast !");

                    jobArrayAdapter = new JobArrayAdapter(context, R.layout.job_list_item, jobList);
                    listView.setAdapter(jobArrayAdapter);
                    loadDialog.dismiss();

                } else if (intent.getAction().equalsIgnoreCase(Constants.ACTION.SOAP_CONNECTION_FAIL)) {
                    loadDialog.dismiss();
                    toast(getResources().getString(R.string.macauto_login_soap_connect_fail));
                } else if (intent.getAction().equalsIgnoreCase(Constants.ACTION.GET_PERSONAL_JOB_LIST_SORT_COMPLETE)) {
                    Log.d(TAG, "receive brocast !");
                    jobArrayAdapter = new JobArrayAdapter(context, R.layout.job_list_item, sortJobList);
                    listView.setAdapter(jobArrayAdapter);
                }
            }
        };

        if (!isRegister) {
            filter = new IntentFilter();
            filter.addAction(Constants.ACTION.GET_PERSONAL_JOB_LIST_COMPLETE);
            filter.addAction(Constants.ACTION.SOAP_CONNECTION_FAIL);
            filter.addAction(Constants.ACTION.GET_PERSONAL_JOB_LIST_SORT_COMPLETE);
            context.registerReceiver(mReceiver, filter);
            isRegister = true;
            Log.d(TAG, "registerReceiver mReceiver");
        }

        /*btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetupPress = true;
                Intent intent = new Intent(context, SetupRequestJob.class);
                getActivity().startActivity(intent);
            }
        });*/

        /*imgSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetupPress = true;
                Intent intent = new Intent(context, SetupRequestJob.class);
                getActivity().startActivity(intent);
            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobListItem item = jobArrayAdapter.getItem(position);

                if (item != null) {
                    Intent intent = new Intent(context, JobShow.class);
                    intent.putExtra("JOB_ID", item.getId());
                    intent.putExtra("JOB_MEETING_NO", item.getMeeting_no());
                    intent.putExtra("JOB_ISSUE", item.getIssue());
                    intent.putExtra("JOB_RESULT", item.getResult());
                    intent.putExtra("JOB_LEADER", item.getLeader());
                    intent.putExtra("JOB_START_DATE", item.getStart_date());
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
                    startActivity(intent);
                }
            }
        });


        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<JobListItem> list = new ArrayList<>();
                if (!newText.equals("")) {



                    //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                    for (int i = 0; i < jobList.size(); i++) {
                        if (jobList.get(i).getResult().contains(newText)) {
                            list.add(jobList.get(i));
                        } else if (jobList.get(i).getEnd_date().contains(newText)) {
                            list.add(jobList.get(i));
                        } else if (jobList.get(i).getDept_name().contains(newText)) {
                            list.add(jobList.get(i));
                        } else if (jobList.get(i).getEmp_name().contains(newText)) {
                            list.add(jobList.get(i));
                        }
                    }

                    //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                    //listView.setAdapter(passwordKeeperArrayAdapter);

                } else {
                    //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                    for (int i = 0; i < jobList.size(); i++) {
                        list.add(jobList.get(i));
                    }

                    //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                    //listView.setAdapter(passwordKeeperArrayAdapter);
                }

                jobArrayAdapter = new JobArrayAdapter(context, R.layout.job_list_item, list);
                listView.setAdapter(jobArrayAdapter);

                return false;
            }
        });*/

        /*loadDialog = new ProgressDialog(context);
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadDialog.setTitle(R.string.macauto_loading);
        loadDialog.setIndeterminate(false);
        loadDialog.setCancelable(false);

        loadDialog.show();

        Intent intent = new Intent(context, GetJobService.class);
        intent.setAction(Constants.ACTION.GET_PERSONAL_JOB_LIST_ACTION);
        context.startService(intent);*/

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroy");
        if (isRegister && mReceiver != null) {
            try {
                context.unregisterReceiver(mReceiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            isRegister = false;
            mReceiver = null;
        }

        if (loadDialog != null) {
            loadDialog.dismiss();
            loadDialog = null;
        }

        super.onDestroyView();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }
    @Override
    public void onResume() {

        if (onCreate || onSetupPress) {
            loadDialog = new ProgressDialog(context);
            loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadDialog.setTitle(R.string.macauto_loading);
            loadDialog.setIndeterminate(false);
            loadDialog.setCancelable(false);

            loadDialog.show();

            Intent intent = new Intent(context, GetJobService.class);
            intent.setAction(Constants.ACTION.GET_PERSONAL_JOB_LIST_ACTION);
            context.startService(intent);
            onCreate = false;
            onSetupPress = false;
        }

        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    public void toast(String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    /*public static void resetAdapter(ArrayList<JobListItem> list) {
        jobArrayAdapter = new JobArrayAdapter(context, R.layout.job_list_item, list);
        listView.setAdapter(AssignmentFragment.jobArrayAdapter);
    }*/
}
