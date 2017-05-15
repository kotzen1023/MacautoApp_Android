package com.macauto.macautoapp_android.Meeting.Service;

import android.app.IntentService;

import android.content.Intent;

import android.os.Build;

import android.util.Log;
import android.util.Xml;

import com.macauto.macautoapp_android.Meeting.AssignmentFragment;
import com.macauto.macautoapp_android.Data.Constants;
import com.macauto.macautoapp_android.Meeting.Data.JobListItem;


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



public class GetJobService extends IntentService {
    //static SharedPreferences pref ;
    //static SharedPreferences.Editor editor;
    //private static final String FILE_NAME = "Preference";

    public static final String TAG = "GetPersonMeetingService";

    private static final String NAMESPACE = "http://tempuri.org/"; // 命名空間

    private static final String METHOD_NAME = "meeting_job_search_info"; // 方法名稱

    private static final String SOAP_ACTION1 = "http://tempuri.org/meeting_job_search_info"; // SOAP_ACTION

    private static final String URL = "http://60.249.239.47/service.asmx"; // 網址

    public GetJobService() {
        super("GetJobService");
    }

    //private String name;
    //private int alarm_interval;
    //private String type;
    //private Context context;
    //private DateFormat formatter;
    //private String requestJobStart;
    //private String requestJobEnd;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");

        //NumberFormat f = new DecimalFormat("00");

        //Calendar c = Calendar.getInstance();


        //pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        //name = pref.getString("NAME", "");
        //alarm_interval = pref.getInt("ALARM_INTERVAL", 30);
        //requestJobStart = pref.getString("REQUEST_JOB_START", String.valueOf(c.get(Calendar.YEAR))+f.format(c.get(Calendar.MONTH)+1)+f.format(c.get(Calendar.DAY_OF_MONTH)));
        //requestJobEnd = pref.getString("REQUEST_JOB_END", String.valueOf(c.get(Calendar.YEAR))+f.format(c.get(Calendar.MONTH)+1)+f.format(c.get(Calendar.DAY_OF_MONTH)));
       // Log.e(TAG, "requestJobStart = "+requestJobStart);

        //context = getApplicationContext();

        //formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.TAIWAN);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Handle");



        //type = intent.getStringExtra("TYPE");

        if (intent.getAction().equals(Constants.ACTION.GET_PERSONAL_JOB_LIST_ACTION)) {
            Log.i(TAG, "GET_PERSONAL_JOB_LIST_ACTIONN");
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
            request.addProperty("meeting_no", "");
            request.addProperty("finish_date_period_start", AssignmentFragment.requestJobStart);
            request.addProperty("finish_date_period_end", AssignmentFragment.requestJobEnd);
            request.addProperty("meeting_result", "");
            request.addProperty("leader_no", "");
            request.addProperty("close_sp", "");
            request.addProperty("dept_no_of_leader_start", "");
            request.addProperty("dept_no_of_leader_end", "");
            request.addProperty("auditor_no", "");
            request.addProperty("audit_sp", "");
            request.addProperty("meeting_type_id", "");
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


        //Intent decryptDoneIntent = new Intent(Constants.ACTION.GET_PERSONAL_MEETING_LIST_COMPLETE);
        //sendBroadcast(decryptDoneIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        Intent intent = new Intent(Constants.ACTION.GET_PERSONAL_JOB_LIST_COMPLETE);
        sendBroadcast(intent);
    }

    public void LoadAndParseXML(InputStream xmlString) {

        AssignmentFragment.jobList.clear();
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
            JobListItem item = null;
            //ArrayList<String> myArrayList = new ArrayList<>();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //i++;
                //XmlPullParser.START_TAG表示目前分析到的是XML的Tag，如<title>

                if (eventType == XmlPullParser.START_TAG) {
                    tag_start = pullParser.getName();
                    Log.e(TAG, "<"+tag_start+">");
                    if (tag_start.equals("MEETING_JOB_LIST")) {
                        Log.i(TAG, "=== Start of MEETING_JOB_LIST ===");
                        //myArrayList.clear();
                        item = new JobListItem();
                        item.setStart_date("");
                        item.setEnd_date("");
                        item.setReal_end_date("");
                        item.setFirst_end_date("");
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
                            case "id":
                                item.setId(tag_value);
                                break;
                            case "meeting_no":
                                item.setMeeting_no(tag_value);
                                break;
                            case "issue_item":
                                item.setIssue(tag_value);
                                break;
                            case "result":
                                item.setResult(tag_value);
                                break;
                            case "leader":
                                item.setLeader_no(tag_value);
                                break;
                            case "start_date":
                                String remove_string_start = tag_value.substring(0, tag_value.length() - 6);
                                String splitter_start[] = remove_string_start.split("T");
                                item.setStart_date(splitter_start[0]+ " " +splitter_start[1]);
                                //Log.e(TAG, "splitter_start[0]="+splitter_start[0]+"splitter_start[1]="+splitter_start[1]);
                                break;
                            case "end_date":
                                String remove_string_end = tag_value.substring(0, tag_value.length() - 6);
                                String splitter_end[] = remove_string_end.split("T");
                                item.setEnd_date(splitter_end[0]+ " "+splitter_end[1]);
                                break;
                            case "real_end_date":
                                String remove_string_real = tag_value.substring(0, tag_value.length() - 6);
                                String splitter_real_end[] = remove_string_real.split("T");
                                item.setReal_end_date(splitter_real_end[0]+ " " +splitter_real_end[1]);
                                break;
                            case "cancel_sp":
                                item.setCancel_sp(tag_value);
                                break;
                            case "close_sp":
                                item.setClose_sp(tag_value);
                                break;
                            case "emp_name":
                                item.setEmp_name(tag_value);
                                break;
                            case "delay_times":
                                item.setDelay_times(tag_value);
                                break;
                            case "dept_name":
                                item.setDept_name(tag_value);
                                break;
                            case "dept_no":
                                item.setDept_no(tag_value);
                                break;
                            case "first_end_date":
                                String remove_string_first = tag_value.substring(0, tag_value.length() - 6);
                                String splitter_first_end[] = remove_string_first.split("T");
                                item.setFirst_end_date(splitter_first_end[0]+ " " +splitter_first_end[1]);
                                break;
                            case "auditor":
                                item.setAuditor(tag_value);
                                break;
                            case "auditor_name":
                                item.setAuditor_name(tag_value);
                                break;
                            case "extn":
                                item.setExtn(tag_value);
                                break;
                            case "auditor_extn":
                                item.setAuditor_extn(tag_value);
                                break;
                            case "delay_wait":
                                item.setDelay_wait(tag_value);
                                break;
                            case "fmea_type":
                                item.setFmea_type(tag_value);
                                break;
                            case "fmea_no":
                                item.setFmea_no(tag_value);
                                break;
                            default:
                                break;
                        }

                        if (name.equals("MEETING_JOB_LIST")) {
                            Log.i(TAG, "=== End of MEETING_JOB_LIST ===");
                            AssignmentFragment.jobList.add(item);
                            /*if (item.getBad_sp().equals("N")) {
                                if (updateEvent(item) == 0 ) {
                                    AddEvent(item);
                                }
                            } else { // item.getBad_sp().equals("Y")
                                removeEvent(item);
                            }*/

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

            /*switch (type) {
                case "NORMAL":
                    Log.i(TAG, "NORMAL");
                    break;
                case "ALARM":
                    Log.i(TAG, "ALARM");
                    for (int j=0; j <PersonalFragment.meetingList.size(); j++) {
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

                        int before_interval = (alarm_interval*60000) - 60000;

                        if (interval > before_interval && interval < alarm_interval) { // in one minute
                            int current_id = (int) System.currentTimeMillis();
                            //Intent myintent = new Intent(GetPersonMeetingService.this, MainActivity.class);

                            //PendingIntent pendingIntent = PendingIntent.getActivity(context, current_id, myintent, 0);


                            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                            //
                            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                            builder.setSmallIcon(R.drawable.ic_priority_high_white_18dp)
                                    .setWhen(System.currentTimeMillis())
                                    .setContentTitle("Meeting is coming.")
                                    .setContentText(PersonalFragment.meetingList.get(i).getRoom_name()+" "+PersonalFragment.meetingList.get(i).getStart_date())
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

            }*/



        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


    }

    /*public boolean AddEvent(MeetingListItem item) {

        Log.i(TAG, "AddToCalendar");
        long _eventId;

        ContentValues values = new ContentValues();
        long cal_Id = 1;
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance();
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
        //values.put(CalendarContract.Events.CALENDAR_COLOR, Color.BLUE);
        values.put(CalendarContract.Events.CALENDAR_ID, cal_Id);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getID());





        try {
            Uri baseUri;
            if (Build.VERSION.SDK_INT >= 8) {
                baseUri = Uri.parse("content://com.android.calendar/events");
            } else {
                baseUri = Uri.parse("content://calendar/events");
            }

            Uri uri = cr.insert(baseUri, values);

            // Save the eventId into the Task object for possible future delete.
            if (uri != null) {
                _eventId = Long.parseLong(uri.getLastPathSegment());
                setReminder(cr, _eventId, alarm_interval);
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
                Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
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

        try {
            iNumRowsDeleted = cr.delete(baseUri, CalendarContract.Events.TITLE + "=? AND "+
                            CalendarContract.Events.DTSTART+ "=? AND "+
                            CalendarContract.Events.DESCRIPTION+ "=?",
                    new String[]{item.getSubject(), String.valueOf(start_date.getTime()), item.getRoom_name()});
        } catch (SecurityException e) {
            e.printStackTrace();
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
            end_date = formatter.parse(item.getEnd_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] vec = new String[] { "calendar_id", "title", "description", "dtstart", "dtend", "allDay", "eventLocation" };

        ContentValues cv = new ContentValues();
        cv.put("title", item.getSubject()); //These Fields should be your String values of actual column names
        cv.put("description", item.getRoom_name());
        cv.put("dtstart", start_date.getTime());
        cv.put("dtend", end_date.getTime());

        //String selection = "TITLE = ?"+item.getSubject();

        try {
            iNumRowsUpdated = cr.update(baseUri, cv, CalendarContract.Events.TITLE + "=? AND "+CalendarContract.Events.DTSTART+ "=?",
                    new String[]{item.getSubject(), String.valueOf(start_date.getTime())});
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "Updated " + iNumRowsUpdated + " calendar entry.");

        return iNumRowsUpdated;
    }*/
}
