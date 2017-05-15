package com.macauto.macautoapp_android.Meeting.Service;

import android.app.AlarmManager;
import android.app.IntentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;

import com.macauto.macautoapp_android.Data.Constants;
import com.macauto.macautoapp_android.Meeting.Data.InitData;
import com.macauto.macautoapp_android.Meeting.Data.MeetingListItem;
import com.macauto.macautoapp_android.Meeting.Data.NotificationPublisher;
import com.macauto.macautoapp_android.Meeting.MeetingAlarm;
import com.macauto.macautoapp_android.Meeting.PersonalFragment;
import com.macauto.macautoapp_android.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class GetPersonMeetingService extends IntentService {

    static SharedPreferences pref ;
    static SharedPreferences.Editor editor;
    private static final String FILE_NAME = "Preference";

    public static final String TAG = "GetPersonMeetingService";

    private static final String NAMESPACE = "http://tempuri.org/"; // 命名空間

    private static final String METHOD_NAME = "get_meeting_list_to_string"; // 方法名稱

    private static final String SOAP_ACTION1 = "http://tempuri.org/get_meeting_list_to_string"; // SOAP_ACTION

    private static final String URL = "http://60.249.239.47/service.asmx"; // 網址

    public GetPersonMeetingService() {
        super("GetPersonMeetingService");
    }

    private String name;
    private int alarm_interval;
    private int sync_option;
    private String type;
    private Context context;
    private DateFormat formatter;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");



        pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        name = pref.getString("NAME", "");
        alarm_interval = pref.getInt("ALARM_INTERVAL", 30);
        sync_option = pref.getInt("SYNC_SETTING", 0);

        context = getApplicationContext();

        formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.TAIWAN);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG, "Handle");



        type = intent.getStringExtra("TYPE");

        if (intent.getAction().equals(Constants.ACTION.GET_PERSONAL_MEETING_LIST_ACTION)) {
            Log.i(TAG, "GET_PERSONAL_MEETING_LIST_ACTION");
        }

        try {
            // 建立一個 WebService 請求

            SoapObject request = new SoapObject(NAMESPACE,
                    METHOD_NAME);

            // 輸出值，帳號(account)、密碼(password)


            //request.addProperty("start_date", "");
            //request.addProperty("end_date", "");
            //request.addProperty("emp_no", "1050636");
            //request.addProperty("room_no", "");
            request.addProperty("emp_name", name);
            //request.addProperty("meeting_room_name", "");
            //request.addProperty("subject_or_content", "");
            //request.addProperty("meeting_type_id", "");
            //request.addProperty("passWord", "sunnyhitest");

            // 擴充 SOAP 序列化功能為第11版

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true; // 設定為 .net 預設編碼

            envelope.setOutputSoapObject(request); // 設定輸出的 SOAP 物件


            // 建立一個 HTTP 傳輸層

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            httpTransport.debug = true; // 測試模式使用

            httpTransport.call(SOAP_ACTION1, envelope); // 設定 SoapAction 所需的標題欄位


            // 將 WebService 資訊轉為 DataTable
            if (envelope.bodyIn instanceof SoapFault) {
                String str= ((SoapFault) envelope.bodyIn).faultstring;
                Log.e(TAG, str);
            } else {
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                Log.d(TAG, String.valueOf(resultsRequestSOAP));
                //result.setText(String.valueOf(resultsRequestSOAP));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    InputStream stream = new ByteArrayInputStream(String.valueOf(resultsRequestSOAP).getBytes(StandardCharsets.UTF_8));
                    LoadAndParseXML(stream);
                } else {
                    InputStream stream = new ByteArrayInputStream(String.valueOf(resultsRequestSOAP).getBytes(Charset.forName("UTF-8")));
                    LoadAndParseXML(stream);
                }

            }

            //meetingArrayAdapter = new MeetingArrayAdapter(MainActivity.this, R.layout.list_item, meetingList);
            //listView.setAdapter(meetingArrayAdapter);


            //Intent meetingAddintent = new Intent(Constants.ACTION.MEETING_NEW_BROCAST);
            //context.sendBroadcast(meetingAddintent);
            //SoapObject bodyIn = (SoapObject) envelope.bodyIn; // KDOM 節點文字編碼

            //Log.e(TAG, bodyIn.toString());

            //DataTable dt = soapToDataTable(bodyIn);

        } catch (Exception e) {
            // 抓到錯誤訊息

            e.printStackTrace();
            Intent decryptDoneIntent = new Intent(Constants.ACTION.SOAP_CONNECTION_FAIL);
            sendBroadcast(decryptDoneIntent);
        }

        MeetingAlarm.last_sync_setting = sync_option;
        //Intent decryptDoneIntent = new Intent(Constants.ACTION.GET_PERSONAL_MEETING_LIST_COMPLETE);
        //sendBroadcast(decryptDoneIntent);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        Intent intent = new Intent(Constants.ACTION.GET_PERSONAL_MEETING_LIST_COMPLETE);
        sendBroadcast(intent);
    }

    public void LoadAndParseXML(InputStream xmlString) {

        PersonalFragment.meetingList.clear();
        XmlPullParser pullParser = Xml.newPullParser();
        //int i=0;
        //String value="";
        String tag_start, tag_value="";
        //boolean start_get_item_from_tag = false;
        try {
            pullParser.setInput(xmlString, "utf-8");

            //利用eventType來判斷目前分析到XML是哪一個部份
            int eventType = pullParser.getEventType();
            //XmlPullParser.END_DOCUMENT表示已經完成分析XML
            MeetingListItem item = null;
            //ArrayList<String> myArrayList = new ArrayList<>();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //i++;
                //XmlPullParser.START_TAG表示目前分析到的是XML的Tag，如<title>

                if (eventType == XmlPullParser.START_TAG) {
                    tag_start = pullParser.getName();
                    Log.e(TAG, "<"+tag_start+">");
                    if (tag_start.equals("MEETING_LIST")) {
                        Log.i(TAG, "=== Start of MEETING_LIST ===");
                        //myArrayList.clear();
                        item = new MeetingListItem();
                    }
                }
                //XmlPullParser.TEXT表示目前分析到的是XML Tag的值，如：台南美食吃不完
                if (eventType == XmlPullParser.TEXT) {
                    tag_value = pullParser.getText();
                    //Log.e(TAG, "value = "+tag_value);

                    //tv02.setText(tv02.getText() + ", " + value);
                }

                if (eventType == XmlPullParser.END_TAG) {
                    String name = pullParser.getName();
                    Log.e(TAG, "value = "+tag_value);
                    //myArrayList.add(tag_value);
                    Log.e(TAG, "</"+name+">");

                    if (name != null && item != null) {

                        switch (name) {
                            case "room_no":
                                item.setRoom_no(tag_value);
                                break;
                            case "master":
                                item.setMaster(tag_value);
                                break;
                            case "emp_name":
                                item.setEmp_name(tag_value);
                                break;
                            case "dept_name":
                                item.setDept_name(tag_value);
                                break;
                            case "room_name":
                                item.setRoom_name(tag_value);

                                break;
                            case "meeting_no":
                                item.setMeeting_no(tag_value);
                                break;
                            case "start_date":
                                String remove_string_start = tag_value.substring(0, tag_value.length() - 6);
                                String splitter_start[] = remove_string_start.split("T");
                                item.setStart_date(splitter_start[0]+ " " +splitter_start[1]);

                                break;
                            case "end_date":
                                String remove_string_end = tag_value.substring(0, tag_value.length() - 6);
                                String splitter_end[] = remove_string_end.split("T");
                                item.setEnd_date(splitter_end[0]+ " " +splitter_end[1]);
                                break;
                            case "approver":
                                item.setApprove(tag_value);
                                break;
                            case "approve_date":
                                String remove_string_approve = tag_value.substring(0, tag_value.length() - 6);
                                String splitter_approve[] = remove_string_approve.split("T");
                                item.setApprove_date(splitter_approve[0]+ " " +splitter_approve[1]);
                                break;
                            case "subject":
                                item.setSubject(tag_value);

                                break;
                            case "bad_sp":
                                item.setBad_sp(tag_value);
                                break;
                            case "memo":
                                item.setMemo(tag_value);
                                break;
                            case "meeting_type":
                                item.setMeeting_type(tag_value);
                                break;
                            case "recorder":
                                item.setRecorder(tag_value);
                                break;
                            default:
                                break;
                        }

                        if (name.equals("MEETING_LIST")) {
                            Log.i(TAG, "=== End of MEETING_LIST ===");
                            PersonalFragment.meetingList.add(item);

                            /*if (sync_option == 1) { //with calendar
                                if (item.getBad_sp().equals("N")) {
                                    if (updateEvent(item) == 0) {
                                        AddEvent(item);
                                    }
                                } else { // item.getBad_sp().equals("Y")
                                    removeEvent(item);
                                }
                            } else {
                                if (MeetingAlarm.last_sync_setting == 1) {
                                    removeEvent(item);
                                }

                            }*/
                            if (sync_option == 2) { //with calendar
                                if (item.getBad_sp().equals("N")) {
                                    if (updateEvent(item) == 0) {
                                        Log.e(TAG, "===>Add new event!");
                                        AddEvent(item);
                                    }
                                } else { // item.getBad_sp().equals("Y")
                                    removeEvent(item);
                                }
                            } else if (sync_option == 1) {
                                long time = 0;
                                Date start_date_compare = null;

                                if (MeetingAlarm.last_sync_setting == 2) {
                                    removeEvent(item);
                                }

                                try {
                                    start_date_compare = formatter.parse(item.getStart_date());
                                    time = start_date_compare.getTime() - alarm_interval * 60000;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Log.d(TAG, "===> get time = "+time+" System.currentTimeMillis() = "+System.currentTimeMillis());

                                if (time > 0) {
                                    if (start_date_compare != null &&
                                            item.getBad_sp().equals("N") &&
                                            start_date_compare.getTime() > System.currentTimeMillis()) {
                                        addSysMotification(getNotification(item), time);
                                    }

                                }
                            } else {
                                if (MeetingAlarm.last_sync_setting == 2) {
                                    removeEvent(item);
                                }

                            }



                        }
                    }
                }
                //分析下一個XML Tag
                try {
                    eventType = pullParser.next();
                } catch (XmlPullParserException ep) {
                    ep.printStackTrace();
                }
            }


            /*if (sync_option == 0) { //with notification
                switch (type) {
                    case "NORMAL":
                        Log.i(TAG, "NORMAL");
                        break;
                    case "ALARM":
                        Log.i(TAG, "ALARM");
                        for (int i = 0; i < PersonalFragment.meetingList.size(); i++) {
                            Calendar c = Calendar.getInstance();

                            Date start_date;
                            long interval = 0;


                            try {
                                //get start date time and get current time = interval
                                start_date = formatter.parse(PersonalFragment.meetingList.get(i).getStart_date());
                                interval = start_date.getTime() - c.getTimeInMillis();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //alarm_interval - before_interval = 1 minute, means send notify just one time

                            int before_interval = (alarm_interval * 60000) - 300000;
                            //Log.e(TAG, "interval = "+interval+" before_interval = "+before_interval+" alarm_interval = "+alarm_interval);

                            if (interval > before_interval && interval < (alarm_interval * 60000)) { // in one minute
                                int current_id = (int) System.currentTimeMillis();
                                //Intent myintent = new Intent(GetPersonMeetingService.this, MainActivity.class);

                                //PendingIntent pendingIntent = PendingIntent.getActivity(context, current_id, myintent, 0);


                                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                                //
                                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                builder.setSmallIcon(R.drawable.ic_priority_high_white_18dp)
                                        .setWhen(System.currentTimeMillis())
                                        .setContentTitle(context.getResources().getString(R.string.macauto_meeting_alarm_title))
                                        .setContentText(PersonalFragment.meetingList.get(i).getRoom_name() + " " + PersonalFragment.meetingList.get(i).getStart_date())
                                        //.setContentIntent(pendingIntent)
                                        .setSound(alarmSound)
                                        .setAutoCancel(true);
                                //.setContentInfo(Data.notification_success + " " + getResources().getString(R.string.main_menu_notification_success) + ", " + Data.notification_error + " " + getResources().getString(R.string.main_menu_notification_error));


                                Notification notification = builder.build();

                                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                manager.notify(current_id, notification);
                            }

                        }
                        break;
                    default:
                        break;

                }
            }*/



        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


    }

    public boolean AddEvent(MeetingListItem item) {

        Log.i(TAG, "AddToCalendar");
        long _eventId;

        ContentValues values = new ContentValues();
        long cal_Id = 1;
        TimeZone tz = TimeZone.getDefault();
        //Calendar cal = Calendar.getInstance();
        ContentResolver cr = getContentResolver();

        Date start_date=null;
        Date end_date=null;
        try {
            start_date = formatter.parse(item.getStart_date());
            end_date = formatter.parse(item.getEnd_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (start_date != null) {
            values.put(CalendarContract.Events.DTSTART, start_date.getTime());
        } else {
            Log.e(TAG, "Can't save start time");
            return false;
        }
        if (end_date != null) {
            values.put(CalendarContract.Events.DTEND, end_date.getTime());
        } else {
            Log.e(TAG, "Can't save end time");
            return false;
        }
        values.put(CalendarContract.Events.TITLE, item.getSubject());
        values.put(CalendarContract.Events.DESCRIPTION, item.getRoom_name());
        //values.put(CalendarContract.Events.DURATION, end_date.getTime()-start_date.getTime());
        //values.put(CalendarContract.Events.CALENDAR_COLOR, Color.BLUE);
        values.put(CalendarContract.Events.CALENDAR_ID, cal_Id);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getID());


        Uri baseUri;
        if (Build.VERSION.SDK_INT >= 8) {
            baseUri = Uri.parse("content://com.android.calendar/events");
        } else {
            baseUri = Uri.parse("content://calendar/events");
        }


        try {
            Uri uri = cr.insert(baseUri, values);
            // Save the eventId into the Task object for possible future delete.
            Log.e(TAG, "uri = "+uri.getLastPathSegment().toString());
            if (uri != null) {
                _eventId = Long.parseLong(uri.getLastPathSegment());
                setReminder(cr, _eventId, alarm_interval);
            } else {
                Log.e(TAG, "uri = null");
            }
            //Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // routine to add reminders with the event
    public void setReminder(ContentResolver cr, long eventID, int timeBefore) {
        try {
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.MINUTES, timeBefore);
            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            try {
                //Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
                Cursor c = CalendarContract.Reminders.query(cr, eventID,
                        new String[]{CalendarContract.Reminders.MINUTES});
                if (c.moveToFirst()) {
                    System.out.println("calendar"
                            + c.getInt(c.getColumnIndex(CalendarContract.Reminders.MINUTES)));
                }
                c.close();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // function to remove an event from the calendar using the eventId stored within the Task object.
    public void removeEvent(MeetingListItem item) {
        int iNumRowsDeleted = 0;
        Date start_date=null;
        //Date end_date=null;
        ContentResolver cr = context.getContentResolver();

        Uri baseUri;
        if (Build.VERSION.SDK_INT >= 8) {
            baseUri = Uri.parse("content://com.android.calendar/events");
        } else {
            baseUri = Uri.parse("content://calendar/events");
        }

        try {
            start_date = formatter.parse(item.getStart_date());
            //end_date = formatter.parse(item.getEnd_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //String selection = "TITLE = ?"+item.getSubject();

        if (start_date != null) {

            try {
                iNumRowsDeleted = cr.delete(baseUri, CalendarContract.Events.TITLE + "=? AND " +
                                CalendarContract.Events.DTSTART + "=? AND " +
                                CalendarContract.Events.DESCRIPTION + "=?",
                        new String[]{item.getSubject(), String.valueOf(start_date.getTime()), item.getRoom_name()});
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Can't delete from calendar.");
        }

        Log.i(TAG, "Deleted " + iNumRowsDeleted + " calendar entry.");
    }


    public int updateEvent(MeetingListItem item) {
        int iNumRowsUpdated = 0;
        Date start_date=null;
        Date end_date=null;
        //long _eventId = 0;

        ContentResolver cr = getContentResolver();

        Uri baseUri;
        if (Build.VERSION.SDK_INT >= 8) {
            baseUri = Uri.parse("content://com.android.calendar/events");
        } else {
            baseUri = Uri.parse("content://calendar/events");
        }

        try {
            start_date = formatter.parse(item.getStart_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            end_date = formatter.parse(item.getEnd_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //String[] vec = new String[] { "calendar_id", "title", "description", "dtstart", "dtend", "allDay", "eventLocation" };

        ContentValues cv = new ContentValues();
        cv.put("title", item.getSubject()); //These Fields should be your String values of actual column names
        cv.put("description", item.getRoom_name());
        if (start_date != null) {
            cv.put("dtstart", start_date.getTime());
        }
        if (end_date != null) {
            cv.put("dtend", end_date.getTime());


        }



        //String selection = "TITLE = ?"+item.getSubject();

        if (start_date != null) {

            try {
                iNumRowsUpdated = cr.update(baseUri, cv, CalendarContract.Events.TITLE + "=? AND " + CalendarContract.Events.DTSTART + "=?",
                        new String[]{item.getSubject(), String.valueOf(start_date.getTime())});
            //} catch (SecurityException e) {
            } catch (SQLiteException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "Updated " + iNumRowsUpdated + " calendar entry.");

        } else {
            Log.e(TAG, "Can't update to calendar.");
        }

        return iNumRowsUpdated;
    }

    private Notification getNotification(MeetingListItem item) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                //.setWhen(System.currentTimeMillis())
                .setContentTitle(item.getSubject())
                //.setContentText(jid_split[0] + " send you an image" + fileTransferRequest.getFileName())
                .setContentText(item.getRoom_name())
                //.setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .setAutoCancel(true);

        Notification notification = builder.build();

        return notification;

        //return null;
    }

    public void addSysMotification(Notification notification, long time) {

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date netDate = (new Date(time));
        //return sdf.format(netDate);

        Log.i(TAG, "addSysMotification: " + sdf.format(netDate));


        Intent myintent = new Intent(this, NotificationPublisher.class);
        myintent.putExtra(NotificationPublisher.NOTIFICATION_ID, (int) System.currentTimeMillis());
        myintent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), myintent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d(TAG, "<remove pendingIntent> "+ pendingIntent.toString());

        InitData.add(pendingIntent);

        long futureInMillis = time;
        //long futureInMillis = System.currentTimeMillis() + delay_time;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }
}
