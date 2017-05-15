package com.macauto.macautoapp_android.Meeting.Data;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.macauto.macautoapp_android.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MeetingArrayAdapter extends ArrayAdapter<MeetingListItem> {

    public static final String TAG = MeetingArrayAdapter.class.getName();
    private Context context;
    //private LayoutInflater inflater = null;
    //SparseBooleanArray mSparseBooleanArray;
    private int layoutResourceId;
    private ArrayList<MeetingListItem> items = new ArrayList<>();


    public MeetingArrayAdapter(Context context, int textViewResourceId,
                               ArrayList<MeetingListItem> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutResourceId = textViewResourceId;
        this.items = objects;

        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //mSparseBooleanArray = new SparseBooleanArray();
    }

    public int getCount() {
        return items.size();

    }

    public MeetingListItem getItem(int position)
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

            holder.title = (TextView) convertView.findViewById(R.id.subjectHeader);
            holder.date = (TextView) convertView.findViewById(R.id.startDate);
            holder.place = (TextView) convertView.findViewById(R.id.place);
            holder.cancel = (TextView) convertView.findViewById(R.id.cancelStat);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            MeetingListItem item = items.get(position);


            holder.title.setText(item.getSubject());
            String start_date = item.getStart_date().substring(0, item.getStart_date().length() - 3);
            String end_date_aray[] = item.getEnd_date().split(" ");
            String end_date = end_date_aray[1].substring(0, end_date_aray[1].length() -3);
            String interval = start_date+" - "+end_date;
            holder.date.setText(interval);
            holder.place.setText(item.getRoom_name());

            if (item.getBad_sp().equals("Y")) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    holder.cancel.setText(Html.fromHtml(context.getResources().getString(R.string.macauto_cancel),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.cancel.setText(Html.fromHtml("<font color='#FF0000'>" + context.getResources().getString(R.string.macauto_cancel) + "</font>"));
                }
            }
            else {
                Calendar c = Calendar.getInstance();

                Date start_date_compare=null;
                Date end_date_compare= null;

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.TAIWAN);
                try {
                    start_date_compare = formatter.parse(item.getStart_date());
                    end_date_compare = formatter.parse(item.getEnd_date());
                    //System.out.println("start time " + start_date.getTime()/1000 + ", end_time = "+end_date.getTime()/1000);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (start_date_compare != null && end_date_compare != null) {

                    if (c.getTimeInMillis() > start_date_compare.getTime() && c.getTimeInMillis() < end_date_compare.getTime()) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            holder.cancel.setText(Html.fromHtml(context.getResources().getString(R.string.macauto_going),Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            holder.cancel.setText(Html.fromHtml("<font color='#e5e812'>" + context.getResources().getString(R.string.macauto_going) + "</font>"));
                        }

                    } else if (c.getTimeInMillis() > end_date_compare.getTime()) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            holder.cancel.setText(Html.fromHtml(context.getResources().getString(R.string.macauto_closed),Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            holder.cancel.setText(Html.fromHtml("<font color='#c916f6'>" + context.getResources().getString(R.string.macauto_closed) + "</font>"));
                        }

                    } else {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            holder.cancel.setText(Html.fromHtml(context.getResources().getString(R.string.macauto_on_time),Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            holder.cancel.setText(Html.fromHtml("<font color='#144b14'>" + context.getResources().getString(R.string.macauto_on_time) + "</font>"));
                        }

                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }




        return convertView;
    }

    class ViewHolder {
        //ImageView icon;
        TextView title;
        TextView date;
        TextView place;
        TextView cancel;
        //TextView date;
    }
}
