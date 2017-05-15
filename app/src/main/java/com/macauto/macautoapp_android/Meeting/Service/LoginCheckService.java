package com.macauto.macautoapp_android.Meeting.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.macauto.macautoapp_android.Data.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class LoginCheckService extends IntentService {

    public static final String TAG = "LoginCheckService";

    public static final String USER_NO = "user_no";

    private static final String NAMESPACE = "http://tempuri.org/"; // 命名空間

    private static final String METHOD_NAME = "check_emp_exist"; // 方法名稱

    private static final String SOAP_ACTION1 = "http://tempuri.org/check_emp_exist"; // SOAP_ACTION

    private static final String URL = "http://60.249.239.47/service.asmx"; // 網址

    public LoginCheckService() {
        super("LoginCheckService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Handle");

        String user_no = intent.getStringExtra(USER_NO);

        if (intent.getAction().equals(Constants.ACTION.CHECK_EMPLOYEE_EXIST_ACTION)) {
            Log.i(TAG, "CHECK_EMPLOYEE_EXIST_ACTION");
        }

        try {
            // 建立一個 WebService 請求

            SoapObject request = new SoapObject(NAMESPACE,
                    METHOD_NAME);

            // 輸出值，帳號(account)、密碼(password)


            //request.addProperty("start_date", "");
            //request.addProperty("end_date", "");
            //request.addProperty("emp_no", "1050636");
            request.addProperty("user_no", user_no);
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

                if (String.valueOf(resultsRequestSOAP).indexOf("true") > 0) {
                    Log.e(TAG, "ret = true");
                    Intent decryptDoneIntent = new Intent(Constants.ACTION.CHECK_EMPLOYEE_EXIST_COMPLETE);
                    sendBroadcast(decryptDoneIntent);
                } else {
                    Log.e(TAG, "ret = false");
                    Intent decryptDoneIntent = new Intent(Constants.ACTION.CHECK_EMPLOYEE_EXIST_FAIL);
                    sendBroadcast(decryptDoneIntent);
                }
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    InputStream stream = new ByteArrayInputStream(String.valueOf(resultsRequestSOAP).getBytes(StandardCharsets.UTF_8));
                    LoadAndParseXML(stream);
                } else {
                    InputStream stream = new ByteArrayInputStream(String.valueOf(resultsRequestSOAP).getBytes(Charset.forName("UTF-8")));
                    LoadAndParseXML(stream);
                }*/

            }


        } catch (Exception e) {
            // 抓到錯誤訊息

            e.printStackTrace();
            Intent decryptDoneIntent = new Intent(Constants.ACTION.SOAP_CONNECTION_FAIL);
            sendBroadcast(decryptDoneIntent);
        }



    }

    /*
    public void LoadAndParseXML(InputStream xmlString) {

        AllFragment.meetingList.clear();
        XmlPullParser pullParser = Xml.newPullParser();
        int i=0;
        String value="";
        String tag_start="", tag_end="", tag_value="";
        //boolean start_get_item_from_tag = false;
        try {
            pullParser.setInput(xmlString, "utf-8");

            //利用eventType來判斷目前分析到XML是哪一個部份
            int eventType = pullParser.getEventType();
            //XmlPullParser.END_DOCUMENT表示已經完成分析XML
            MeetingListItem item = null;
            //ArrayList<String> myArrayList = new ArrayList<>();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                i++;
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

                    if (name != null) {

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
                                String splitter_start[] = tag_value.split("T|\\+");
                                item.setStart_date(splitter_start[0]+ " " +splitter_start[1]);
                                break;
                            case "end_date":
                                String splitter_end[] = tag_value.split("T|\\+");
                                item.setEnd_date(splitter_end[0]+ " " +splitter_end[1]);
                                break;
                            case "approver":
                                item.setApprove(tag_value);
                                break;
                            case "approve_date":
                                String splitter_approve[] = tag_value.split("T|\\+");
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

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


    }*/
}
