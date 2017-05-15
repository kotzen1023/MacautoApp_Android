package com.macauto.macautoapp_android.Meeting.Data;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.macauto.macautoapp_android.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JobArrayAdapter extends ArrayAdapter<JobListItem> {
    public static final String TAG = JobArrayAdapter.class.getName();
    private Context context;
    //private LayoutInflater inflater = null;
    //SparseBooleanArray mSparseBooleanArray;
    private int layoutResourceId;
    private ArrayList<JobListItem> items = new ArrayList<>();

    public JobArrayAdapter(Context context, int textViewResourceId,
                           ArrayList<JobListItem> objects) {
        super(context, textViewResourceId, objects);

        this.context = context;
        this.layoutResourceId = textViewResourceId;
        this.items = objects;

        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return items.size();

    }

    public JobListItem getItem(int position)
    {
        return items.get(position);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        //Log.e(TAG, "getView = "+ position);
        ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.decision = (TextView) convertView.findViewById(R.id.decisionHeader);
            holder.estimatedheader = (TextView) convertView.findViewById(R.id.estimatedDateHeader);
            holder.estimated = (TextView) convertView.findViewById(R.id.estimatedTime);
            holder.department = (TextView) convertView.findViewById(R.id.department);
            holder.response = (TextView) convertView.findViewById(R.id.response);
            //holder.delayheader = (TextView) convertView.findViewById(R.id.delayHeader);
            holder.delayday = (TextView) convertView.findViewById(R.id.delayStat);
            holder.imgDelay = (ImageView) convertView.findViewById(R.id.imageDelay);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            long diff;
            JobListItem item = items.get(position);

            Calendar c = Calendar.getInstance();

            Date date_compare=null;
            Date first_end_compare=null;
            Date real_end_compare=null;

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.TAIWAN);
            DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
            String splitter_end[] = item.getEnd_date().split(" ");
            try {
                date_compare = formatter2.parse(splitter_end[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                first_end_compare = formatter.parse(item.getFirst_end_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                real_end_compare = formatter.parse(item.getReal_end_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "pos = "+position+", end = " +item.getEnd_date()+", real = "+item.getReal_end_date()+", first = "+item.getFirst_end_date());

            if (date_compare != null) {

                if (real_end_compare == null && first_end_compare == null) {
                    //diff = real_end_compare.getTime() - first_end_compare.getTime();
                    diff = c.getTimeInMillis() - date_compare.getTime();
                    Log.e(TAG, "1 diff = " + diff);
                } else if (real_end_compare != null && first_end_compare == null) {
                    diff = real_end_compare.getTime() - date_compare.getTime();
                    Log.e(TAG, "2 diff = " + diff);
                //} else if (real_end_compare == null && first_end_compare != null) {
                } else if (real_end_compare == null) {
                    diff = c.getTimeInMillis() - date_compare.getTime();
                    Log.e(TAG, "3 diff = " + diff);
                } else {
                    diff = real_end_compare.getTime() - date_compare.getTime();
                    Log.e(TAG, "4 diff = " + diff);
                }

                holder.decision.setText(item.getResult());
                holder.estimatedheader.setText(context.getResources().getString(R.string.macauto_job_estimated_time));
                //String splitter_end[] = item.getEnd_date().split(" ");
                holder.estimated.setText(splitter_end[0]);
                holder.department.setText((item.getDept_name()));
                holder.response.setText(item.getEmp_name());
                if (diff / 86400000 <= 0) {
                    //holder.delayheader.setText(context.getResources().getString(R.string.macauto_job_delay_header));
                    //holder.delayday.setText(String.valueOf(diff / 86400000) + " " + context.getResources().getString(R.string.macauto_job_day));
                    holder.delayday.setText(context.getResources().getString(R.string.macauto_job_delay_header, diff / 86400000));
                } else {

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        holder.delayday.setText(Html.fromHtml(context.getResources().getString(R.string.macauto_job_delay_header),Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        holder.delayday.setText(Html.fromHtml("<font color='#FFFF00'>"+context.getResources().getString(R.string.macauto_job_delay_header, diff / 86400000) + "</font>"));
                    }

                }
                if (item.getDelay_wait().equals("1")) {
                    holder.imgDelay.setImageResource(R.drawable.ic_transfer_within_a_station_black_48dp);
                } else {
                    holder.imgDelay.setImageResource(0);
                }
            }
            else {
                Log.e(TAG, "date_+compare = null");
            }



        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }




        return convertView;
    }

    class ViewHolder {
        TextView decision;
        TextView estimatedheader;
        TextView estimated;
        TextView department;
        TextView response;
        //TextView delayheader;
        TextView delayday;
        ImageView imgDelay;
    }
}
