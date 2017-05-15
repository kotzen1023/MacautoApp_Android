package com.macauto.macautoapp_android.Receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


import com.macauto.macautoapp_android.Data.Constants;

import com.macauto.macautoapp_android.Meeting.Service.AlarmService;

public class Receive_BootCompleted extends BroadcastReceiver {
    private static final String TAG = Receive_BootCompleted.class.getName();
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
        {
            //here we start the service
            Log.e(TAG, "receive ACTION_BOOT_COMPLETED");
            //Intent serviceIntent = new Intent(context, AlarmService.class);
            //context.startService(serviceIntent);

            Log.e(TAG, "start AlarmService");
            //alarm service
            Intent alarmserviceIntent = new Intent(context, AlarmService.class);
            context.startService(alarmserviceIntent);
            //Log.e(TAG, "start ChatService");
            //chat chat service
            //Intent chatserviceIntent = new Intent(context, ConnectionService.class);
            //context.startService(chatserviceIntent);
            //Log.e(TAG, "start MqttService");
            //Mqtt
            //Intent mqttserviceIntent = new Intent(context, MqttMainService.class);
            //context.startService(mqttserviceIntent);

        } else if (intent.getAction().equalsIgnoreCase(Constants.ACTION.GET_START_ALARM_SERVICE_ACTION)) {
            Log.e(TAG, "receive GET_START_ALARM_SERVICE_ACTION");
            /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT ) {
                if (!isMyServiceRunning(AlarmService.class, context)) {
                    Log.e(TAG, "start AlarmService");
                    Intent serviceIntent = new Intent(context, AlarmService.class);
                    context.startService(serviceIntent);
                } else {
                    Log.e(TAG, "AlarmService is running");
                }
            } else {
                Log.e(TAG, "start AlarmService");
                Intent serviceIntent = new Intent(context, AlarmService.class);
                context.startService(serviceIntent);
            }*/
        }
        /*else if (intent.getAction().equalsIgnoreCase(Constants.ACTION.CHAT_START_SERVICE_ACTION)) { //chat
            Log.e(TAG, "receive CHAT_START_SERVICE_ACTION");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT ) {
                if (!isMyServiceRunning(ConnectionService.class, context)) {
                    Log.e(TAG, "start ConnectionService");
                    Intent serviceIntent = new Intent(context, ConnectionService.class);
                    context.startService(serviceIntent);
                } else {
                    Log.e(TAG, "ConnectionService is running");
                }
            } else {
                Log.e(TAG, "start ConnectionService");
                Intent serviceIntent = new Intent(context, ConnectionService.class);
                context.startService(serviceIntent);
            }
        }

        else if (intent.getAction().equalsIgnoreCase(Constants.ACTION.MQTT_START_SERVICE_ACTION)) { //mqtt
            Log.e(TAG, "receive MQTT_START_SERVICE_ACTION");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT ) {
                if (!isMyServiceRunning(MqttMainService.class, context)) {
                    Log.e(TAG, "start MqttMainService");
                    Intent serviceIntent = new Intent(context, MqttMainService.class);
                    context.startService(serviceIntent);
                } else {
                    Log.e(TAG, "MqttMainService is running");
                }
            } else {
                Log.e(TAG, "start MqttMainService");
                Intent serviceIntent = new Intent(context, MqttMainService.class);
                context.startService(serviceIntent);
            }
        }*/
    }

    private boolean isMyServiceRunning(Class<?> serviceClass, Context mContext) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
