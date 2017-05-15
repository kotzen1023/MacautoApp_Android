package com.macauto.macautoapp_android.Meeting;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.macauto.macautoapp_android.Data.Constants;
import com.macauto.macautoapp_android.Meeting.Data.MeetingArrayAdapter;
import com.macauto.macautoapp_android.Meeting.Data.MeetingListItem;
import com.macauto.macautoapp_android.R;
import com.macauto.macautoapp_android.Meeting.Service.GetAllMeetingService;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AllFragment extends Fragment {
    private static final String TAG = AllFragment.class.getName();

    private Context context;
    public ListView listView;
    //private Button btnSetup;
    //private ImageView imgSetup;
    //private SearchView searchView;
    ProgressDialog loadDialog = null;

    public static ArrayList<MeetingListItem> meetingList = new ArrayList<>();
    public static ArrayList<MeetingListItem> sortedMeetingList = new ArrayList<>();

    public MeetingArrayAdapter meetingArrayAdapter;
    private static BroadcastReceiver mReceiver = null;
    private static boolean isRegister = false;
    private static boolean onCreate = false;
    public static boolean onSetupPress = false;
    public static String requestMeetingStart;
    public static String requestMeetingEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, container, false);

        Log.e(TAG, "onCreateView");

        Calendar c = Calendar.getInstance();
        Calendar future_c = Calendar.getInstance();

        NumberFormat f = new DecimalFormat("00");
        long current_time = c.getTimeInMillis();
        future_c.setTimeInMillis(current_time+86400*7*1000);

        requestMeetingStart = String.valueOf(c.get(Calendar.YEAR))+f.format(c.get(Calendar.MONTH)+1)+f.format(c.get(Calendar.DAY_OF_MONTH));
        requestMeetingEnd = String.valueOf(future_c.get(Calendar.YEAR))+f.format(future_c.get(Calendar.MONTH)+1)+f.format(future_c.get(Calendar.DAY_OF_MONTH));

        onCreate = true;

        context = getContext();
        IntentFilter filter;

        listView = (ListView) view.findViewById(R.id.listViewAll);
        //btnSetup = (Button) view.findViewById(R.id.btnAllSetup);
        //imgSetup = (ImageView) view.findViewById(R.id.imgSetupAll);
        listView.setTextFilterEnabled(true);
        //SearchView searchView = (SearchView) view.findViewById(R.id.searchViewAll);
        //searchView.setSubmitButtonEnabled(false);


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase(Constants.ACTION.GET_ALL_MEETING_LIST_COMPLETE)) {
                    Log.d(TAG, "receive brocast !");

                    meetingArrayAdapter = new MeetingArrayAdapter(context, R.layout.meeting_list_item, meetingList);
                    listView.setAdapter(meetingArrayAdapter);
                    loadDialog.dismiss();

                } else if (intent.getAction().equalsIgnoreCase(Constants.ACTION.SOAP_CONNECTION_FAIL)) {
                    loadDialog.dismiss();
                    toast(getResources().getString(R.string.macauto_login_soap_connect_fail));
                } else if (intent.getAction().equalsIgnoreCase(Constants.ACTION.GET_ALL_MEETING_LIST_SORT_COMPLETE)) {
                    Log.d(TAG, "receive brocast !");
                    meetingArrayAdapter = new MeetingArrayAdapter(context, R.layout.meeting_list_item, sortedMeetingList);
                    listView.setAdapter(meetingArrayAdapter);
                }
            }
        };

        if (!isRegister) {
            filter = new IntentFilter();
            filter.addAction(Constants.ACTION.GET_ALL_MEETING_LIST_COMPLETE);
            filter.addAction(Constants.ACTION.SOAP_CONNECTION_FAIL);
            filter.addAction(Constants.ACTION.GET_ALL_MEETING_LIST_SORT_COMPLETE);
            context.registerReceiver(mReceiver, filter);
            isRegister = true;
            Log.d(TAG, "registerReceiver mReceiver");
        }

        /*btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetupPress = true;
                Intent intent = new Intent(context, SetupRequestMeeting.class);
                getActivity().startActivity(intent);
            }
        });*/

        /*imgSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetupPress = true;
                Intent intent = new Intent(context, SetupRequestMeeting.class);
                getActivity().startActivity(intent);
            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MeetingListItem item = meetingArrayAdapter.getItem(position);

                /*
                        Log.i(TAG, "room no = "+ item.getRoom_no());
                        Log.i(TAG, "master = "+ item.getMaster());
                        Log.i(TAG, "emp_name = "+ item.getEmp_name());
                        Log.i(TAG, "dept_name = "+ item.getDept_name());
                        Log.i(TAG, "room_name = "+ item.getRoom_name());
                        Log.i(TAG, "meeting no = "+ item.getMeeting_no());
                        Log.i(TAG, "start_date = "+ item.getStart_date());
                        Log.i(TAG, "end_date = "+ item.getEnd_date());
                        Log.i(TAG, "approve = "+ item.getApprover());
                        Log.i(TAG, "approve_date = "+item.getApprove_date());
                        Log.i(TAG, "subject = "+ item.getSubject());
                        Log.i(TAG, "bad_sp = "+ item.getBad_sp());
                        Log.i(TAG, "memo = "+ item.getMemo());
                        Log.i(TAG, "meeting_type = "+item.getMeeting_type());
                        Log.i(TAG, "recorder = "+ item.getRecorder());
                 */

                if (item != null) {
                    Intent intent = new Intent(context, MeetingShow.class);
                    intent.putExtra("ROOM_NO", item.getRoom_no());
                    intent.putExtra("MASTER", item.getMaster());
                    intent.putExtra("EMP_NAME", item.getEmp_name());
                    intent.putExtra("DEPT_NAME", item.getDept_name());
                    intent.putExtra("ROOM_NAME", item.getRoom_name());
                    intent.putExtra("MEETING_NO", item.getMeeting_no());
                    intent.putExtra("START_DATE", item.getStart_date());
                    intent.putExtra("END_DATE", item.getEnd_date());
                    intent.putExtra("APPROVE", item.getApprover());
                    intent.putExtra("APPROVE_DATE", item.getApprove_date());
                    intent.putExtra("SUBJECT", item.getSubject());
                    intent.putExtra("BAD_SP", item.getBad_sp());
                    intent.putExtra("MEMO", item.getMemo());
                    intent.putExtra("MEETING_TYPE", item.getMeeting_type());
                    intent.putExtra("RECORDER", item.getRecorder());
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

                ArrayList<MeetingListItem> list = new ArrayList<>();
                if (!newText.equals("")) {



                    //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                    for (int i = 0; i < meetingList.size(); i++) {
                        if (meetingList.get(i).getSubject().contains(newText)) {
                            list.add(meetingList.get(i));
                        } else if (meetingList.get(i).getRoom_name().contains(newText)) {
                            list.add(meetingList.get(i));
                        } else if (meetingList.get(i).getStart_date().contains(newText)) {
                            list.add(meetingList.get(i));
                        }
                    }

                    //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                    //listView.setAdapter(passwordKeeperArrayAdapter);

                } else {
                    //ArrayList<PasswordKeeperItem> list = new ArrayList<PasswordKeeperItem>();
                    for (int i = 0; i < meetingList.size(); i++) {
                        list.add(meetingList.get(i));
                    }

                    //passwordKeeperArrayAdapter = new PasswordKeeperArrayAdapter(Password_Keeper.this, R.layout.passwd_keeper_browsw_item, list);
                    //listView.setAdapter(passwordKeeperArrayAdapter);
                }

                meetingArrayAdapter = new MeetingArrayAdapter(context, R.layout.meeting_list_item, list);
                listView.setAdapter(meetingArrayAdapter);

                return false;
            }
        });*/

        /*loadDialog = new ProgressDialog(context);
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadDialog.setTitle(R.string.macauto_loading);
        loadDialog.setIndeterminate(false);
        loadDialog.setCancelable(false);

        loadDialog.show();

        Intent intent = new Intent(context, GetAllMeetingService.class);
        intent.setAction(Constants.ACTION.GET_ALL_MEETING_LIST_ACTION);
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

            Intent intent = new Intent(context, GetAllMeetingService.class);
            intent.setAction(Constants.ACTION.GET_ALL_MEETING_LIST_ACTION);
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

    /*public static void resetAdapter(ArrayList<MeetingListItem> list) {
        meetingArrayAdapter = new MeetingArrayAdapter(context, R.layout.meeting_list_item, list);
        listView.setAdapter(AllFragment.meetingArrayAdapter);
    }*/
}
