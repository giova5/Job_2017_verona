package com.emis.job2017.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.CalendarEventModel;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;

/**
 * Created by jo5 on 10/11/17.
 */

public class CalendarDetailPage extends Fragment{

    private int calendarID;
    public static final String CALENDAR_ID = "CALENDAR_ID";

    private TextView eventTitle;
    private TextView eventDescription;
    private TextView eventPosition;
    private TextView eventDay;

    public CalendarDetailPage(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calendarID = getArguments().getInt(CALENDAR_ID, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calendar_detail, container, false);

        eventTitle = (TextView) view.findViewById(R.id.calendar_title);
        eventDescription = (TextView) view.findViewById(R.id.calendar_description);
        eventPosition = (TextView) view.findViewById(R.id.calendar_location);
        eventDay = (TextView) view.findViewById(R.id.calendar_day);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarEventModel currentEvent = RealmUtils.getCalendarPrograms(calendarID);

        eventTitle.setText(Html.fromHtml(currentEvent.getTitle().replace("\n", "<br>")));
        eventDescription.setText(Html.fromHtml(currentEvent.getDescription().replace("\n", "<br>")));
        eventPosition.setText(currentEvent.getLocation());

        Calendar startDate = Utils.toCalendar(new Date(currentEvent.getStartTime() * 1000));
        Calendar endDate = Utils.toCalendar(new Date(currentEvent.getEndTime() * 1000));

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");

        String startAndEndTime = format1.format(startDate.getTime()) + " ore " + format2.format(startDate.getTime()) + " - " + format2.format(endDate.getTime());
        eventDay.setText(startAndEndTime);

    }

    public static CalendarDetailPage newInstance(int calendarID){
        CalendarDetailPage calendarDetailPage = new CalendarDetailPage();

        Bundle args = new Bundle();
        args.putInt(CALENDAR_ID, calendarID);
        calendarDetailPage.setArguments(args);
        return calendarDetailPage;
    }


}
