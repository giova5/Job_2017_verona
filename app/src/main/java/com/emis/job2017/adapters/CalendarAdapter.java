package com.emis.job2017.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.CalendarEventModel;

import java.util.ArrayList;
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
            holder.idProgram = (TextView) convertView.findViewById(R.id.event_id_program);
            holder.title = (TextView) convertView.findViewById(R.id.event_title);
            holder.description = (TextView) convertView.findViewById(R.id.event_description);
            holder.typeOfProgramString = (TextView) convertView.findViewById(R.id.event_type_of_program);
            holder.dateTimestamp= (TextView) convertView.findViewById(R.id.event_date);
            convertView.setTag(holder);
        } else {
            holder = (CalendarViewHolder) convertView.getTag();
        }

        final CalendarEventModel item = items.get(position);
        holder.idProgram.setText(String.valueOf(item.getIdProgram()));
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.typeOfProgramString.setText(item.getTypeOfProgramString());

        return convertView;
    }

}
