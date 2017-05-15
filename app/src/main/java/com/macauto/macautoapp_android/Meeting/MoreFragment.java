package com.macauto.macautoapp_android.Meeting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.macauto.macautoapp_android.R;


public class MoreFragment extends Fragment {

    private static final String TAG = MoreFragment.class.getName();
    //private String value = "";

    private Context context;

    static SharedPreferences pref ;
    static SharedPreferences.Editor editor;
    private static final String FILE_NAME = "Preference";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_more, container, false);

        ImageView imgMeetingAlarm = (ImageView) view.findViewById(R.id.imageViewMeetingAlarm);
        //ImageView imgSettings = (ImageView) view.findViewById(R.id.imageViewSetting);
        ImageView imgLogout = (ImageView) view.findViewById(R.id.imageViewLogout);
        //ImageView imgMessage = (ImageView) view.findViewById(R.id.imageViewChatting);

        TextView txtLogout = (TextView) view.findViewById(R.id.textLogout);
        TextView txtMeetingAlarm = (TextView) view.findViewById(R.id.textAlarm);
        //TextView txtChatting = (TextView) view.findViewById(R.id.textChatting);

        context = getContext();

        imgMeetingAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), MeetingAlarm.class);
                getActivity().startActivity(intent);
            }
        });

        txtMeetingAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), MeetingAlarm.class);
                getActivity().startActivity(intent);
            }
        });

        /*imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ChatLogin.class);
                getActivity().startActivity(intent);
            }
        });

        txtChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ChatLogin.class);
                getActivity().startActivity(intent);
            }
        });*/


        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder confirmdialog = new AlertDialog.Builder(view.getContext());
                confirmdialog.setIcon(R.drawable.ic_exit_to_app_black_48dp);
                confirmdialog.setTitle(view.getResources().getString(R.string.macauto_more_logout_title));
                confirmdialog.setMessage(view.getResources().getString(R.string.macauto_more_logout_descrypt));
                confirmdialog.setPositiveButton(view.getResources().getString(R.string.macauto_confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        pref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putBoolean("LOGIN", false);
                        editor.putString("ACCOUNT", "");
                        editor.putString("PASSWORD", "");
                        editor.putBoolean("KEEP_ACCOUNT_PASSWORD", false);
                        editor.putBoolean("AUTOLOGIN", false);
                        editor.apply();

                        Intent intent = new Intent(view.getContext(), Login.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();

                    }
                });
                confirmdialog.setNegativeButton(view.getResources().getString(R.string.macauto_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                confirmdialog.show();
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirmdialog = new AlertDialog.Builder(view.getContext());
                confirmdialog.setIcon(R.drawable.ic_exit_to_app_black_48dp);
                confirmdialog.setTitle(view.getResources().getString(R.string.macauto_more_logout_title));
                confirmdialog.setMessage(view.getResources().getString(R.string.macauto_more_logout_descrypt));
                confirmdialog.setPositiveButton(view.getResources().getString(R.string.macauto_confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        pref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putBoolean("LOGIN", false);
                        editor.putString("ACCOUNT", "");
                        editor.putString("PASSWORD", "");
                        editor.putBoolean("KEEP_ACCOUNT_PASSWORD", false);
                        editor.putBoolean("AUTOLOGIN", false);
                        editor.apply();

                        Intent intent = new Intent(view.getContext(), Login.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();

                    }
                });
                confirmdialog.setNegativeButton(view.getResources().getString(R.string.macauto_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                confirmdialog.show();
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroy");

        super.onDestroyView();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }
    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }


}
