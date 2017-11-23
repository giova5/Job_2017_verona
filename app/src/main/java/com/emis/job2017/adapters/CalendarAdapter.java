package com.emis.job2017.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.CalendarEventModel;
import com.emis.job2017.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jo5 on 25/10/17.
 */

public class CalendarAdapter extends ArrayAdapter<CalendarEventModel> {

    private List<CalendarEventModel> items;
    Context mContext;

    private static class CalendarViewHolder {
        TextView idProgram;
        TextView title;
        TextView description;
        TextView typeOfProgramString;
        TextView dateTimestamp;
    }

    public CalendarAdapter(ArrayList<CalendarEventModel> data, Context context) {
        super(context, 0);
        this.items = data;
        this.mContext = context;
    }

    public void switchItems(List<CalendarEventModel> newItems){
        items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calendar_list_item, parent, false);
            holder = new CalendarViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.dealer_name);
            holder.dateTimestamp = (TextView) convertView.findViewById(R.id.dealer_category);
            convertView.setTag(holder);
        } else {
            holder = (CalendarViewHolder) convertView.getTag();
        }

        final CalendarEventModel item = items.get(position);

        holder.title.setText(Html.fromHtml(item.getTitle().replace("\n", "<br>")));
        Log.d("Title", item.getTitle());

        Date firstDate = new Date(item.getStartTime() * 1000);
        Date secondDate = new Date(item.getEndTime() * 1000);

        Log.d("Start date", String.valueOf(item.getStartTime() * 1000));

        String startTime = String.valueOf(firstDate.getHours()) + ":" + Utils.parseMinutes(String.valueOf(firstDate.getMinutes()));
        String endTime = String.valueOf(secondDate.getHours()) + ":" + Utils.parseMinutes(String.valueOf(secondDate.getMinutes()));

        Calendar dateCalendar = Utils.toCalendar(firstDate);

        String dateForTesting = String.valueOf(dateCalendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.valueOf(dateCalendar.get(Calendar.MONTH) + "-" + String.valueOf(dateCalendar.get(Calendar.YEAR)));
        String calendarDate = dateForTesting + " --- " + startTime + " - " + endTime;

        holder.dateTimestamp.setText(calendarDate);

        return convertView;
    }




}
