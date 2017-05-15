package com.macauto.macautoapp_android.Meeting.Task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.macauto.macautoapp_android.Data.Constants;
import com.macauto.macautoapp_android.Meeting.Service.GetPersonMeetingService;

import java.util.TimerTask;

public class CheckMeetingTask extends TimerTask {
    public static final String TAG = CheckMeetingTask.class.getName();
    private Context mContext;
    public CheckMeetingTask(Context context) {
        mContext = context;
    }

    //private SharedPreferences pref ;
    //static SharedPreferences.Editor editor;
    private static final String FILE_NAME = "Preference";
    //private boolean login = false;
    //private boolean alarm = false;

    @Override
    public void run() {
        SharedPreferences pref ;
        pref = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        boolean login = pref.getBoolean("LOGIN", false);
        int sync_option = pref.getInt("SYNC_SETTING", 0);
        //boolean alarm = pref.getBoolean("MEETING_ALARM", false);

        Log.e(TAG, "CheckMeetingTask is running...(login = "+login+")");


        //String ret = DataBaseUtil.testSQL(mContext);
        //Log.d(TAG, "ret = "+ ret);

        if (login) {

            if (sync_option > 0 ) {
                Intent intent = new Intent(mContext, GetPersonMeetingService.class);
                intent.setAction(Constants.ACTION.GET_PERSONAL_MEETING_LIST_ACTION);
                intent.putExtra("TYPE", "ALARM");
                mContext.startService(intent);
            } else {
                Log.d(TAG, "Do not call while none");
            }
        } else {
            Log.e(TAG, "Do not send request while not login enable!");
        }
    }
}
