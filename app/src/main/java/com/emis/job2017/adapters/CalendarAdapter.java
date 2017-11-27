package com.emis.job2017.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.CalendarEventModel;
import com.emis.job2017.models.ExhibitorsModel;
import com.emis.job2017.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jo5 on 25/10/17.
 */

public class CalendarAdapter extends ArrayAdapter<CalendarEventModel> implements Filterable{

    private List<CalendarEventModel> items;
    List<CalendarEventModel> listFiltered = new ArrayList<CalendarEventModel>();
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
        listFiltered = newItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listFiltered != null ? listFiltered.size() : 0;
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

        final CalendarEventModel item = listFiltered.get(position);

        holder.title.setText(Html.fromHtml(item.getTitle().replace("\n", "<br>")));
        Log.d("Title", item.getTitle());

        Date firstDate = new Date(item.getStartTime() * 1000);
        Date secondDate = new Date(item.getEndTime() * 1000);

        Log.d("Start date", String.valueOf(item.getStartTime() * 1000));

        String startTime = String.valueOf(firstDate.getHours()) + ":" + Utils.parseMinutes(String.valueOf(firstDate.getMinutes()));
        String endTime = String.valueOf(secondDate.getHours()) + ":" + Utils.parseMinutes(String.valueOf(secondDate.getMinutes()));

        Calendar dateCalendar = Utils.toCalendar(firstDate);

        String dateForTesting = String.valueOf(dateCalendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.valueOf(dateCalendar.get(Calendar.MONTH) + 1) + "-" + String.valueOf(dateCalendar.get(Calendar.YEAR));
        String calendarDate = dateForTesting + " ore " + startTime + " - " + endTime;

        holder.dateTimestamp.setText(calendarDate);

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = items.size();
                    results.values = items;
                }else{
                    List<CalendarEventModel> resultsData = new LinkedList<>();
                    String searchStr = constraint.toString().toUpperCase();

                    for(CalendarEventModel current : items){
                        String calTitle = current.getTitle();
                        if (calTitle.toUpperCase().contains(searchStr))
                            resultsData.add(current);
                    }
                    results.count = resultsData.size();
                    results.values = resultsData;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFiltered = (LinkedList<CalendarEventModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public List<CalendarEventModel> getListFiltered(){
        return listFiltered;
    }




}
