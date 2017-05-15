package com.macauto.macautoapp_android.Meeting.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.util.Xml;

import com.macauto.macautoapp_android.Meeting.AllFragment;
import com.macauto.macautoapp_android.Data.Constants;
import com.macauto.macautoapp_android.Meeting.Data.MeetingListItem;


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

public class GetAllMeetingService extends IntentService {
    public static final String TAG = "GetAllMeetingService";

    private static final String NAMESPACE = "http://tempuri.org/"; // 命名空間

    private static final String METHOD_NAME = "get_meeting_list_to_string"; // 方法名稱

    private static final String SOAP_ACTION1 = "http://tempuri.org/get_meeting_list_to_string"; // SOAP_ACTION

    private static final String URL = "http://60.249.239.47/service.asmx"; // 網址

    public GetAllMeetingService() {
        super("GetAllMeetingService");
    }

    private int meeting_count = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG, "Handle");

        if (intent.getAction().equals(Constants.ACTION.GET_ALL_MEETING_LIST_ACTION)) {
            Log.i(TAG, "GET_ALL_MEETING_LIST_ACTION");
        }

        try {
            // 建立一個 WebService 請求

            SoapObject request = new SoapObject(NAMESPACE,
                    METHOD_NAME);

            // 輸出值，帳號(account)、密碼(password)


            request.addProperty("start_date", AllFragment.requestMeetingStart);
            request.addProperty("end_date", AllFragment.requestMeetingEnd);
            //request.addProperty("emp_no", "1050636");
            request.addProperty("room_no", "");
            //request.addProperty("emp_name", "方炳強");
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
                Log.e(TAG, "Total meeting = "+meeting_count);

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


        //Intent decryptDoneIntent = new Intent(Constants.ACTION.GET_ALL_MEETING_LIST_COMPLETE);
        //sendBroadcast(decryptDoneIntent);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        Intent intent = new Intent(Constants.ACTION.GET_ALL_MEETING_LIST_COMPLETE);
        sendBroadcast(intent);
    }

    public void LoadAndParseXML(InputStream xmlString) {

        AllFragment.meetingList.clear();
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
                            AllFragment.meetingList.add(item);
                            meeting_count++;
                        }
                    }




                    /*if (name.equals("MEETING_LIST")) {
                        Log.i(TAG, "=== End of MEETING_LIST ===");

                        MeetingListItem item = new MeetingListItem();
                        item.setRoom_no(myArrayList.get(0));
                        item.setMaster(myArrayList.get(1));
                        item.setEmp_name(myArrayList.get(2));
                        item.setDept_name(myArrayList.get(3));
                        item.setRoom_name(myArrayList.get(4));
                        item.setMeeting_no(myArrayList.get(5));
                        item.setStart_date(myArrayList.get(6));
                        item.setEnd_date(myArrayList.get(7));
                        item.setApprover(myArrayList.get(8));
                        item.setApprove_date(myArrayList.get(9));
                        item.setSubject(myArrayList.get(10));
                        item.setBad_sp(myArrayList.get(11));
                        item.setMemo(myArrayList.get(12));
                        item.setMeeting_type(myArrayList.get(13));
                        item.setRecorder(myArrayList.get(14));

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

                        meetingList.add(item);

                    } else {
                        myArrayList.add(value);
                    }*/


                }
                //分析下一個XML Tag
                try {
                    eventType = pullParser.next();
                } catch (XmlPullParserException ep) {
                    ep.printStackTrace();
                }
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


    }
}
