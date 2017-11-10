package com.emis.job2017.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.utils.RealmUtils;

import javax.annotation.Nullable;

/**
 * Created by jo5 on 10/11/17.
 */

public class CalendarDetailPage extends Fragment{

    private int calendarID;
    TextView calendarIDTextView;
    public static final String CALENDAR_ID = "CALENDAR_ID";

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
        calendarIDTextView = (TextView) view.findViewById(R.id.calendar_id);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarIDTextView.setText(String.valueOf(RealmUtils.getCalendarPrograms(calendarID).getIdProgram()));
    }

    public static CalendarDetailPage newInstance(int calendarID){
        CalendarDetailPage calendarDetailPage = new CalendarDetailPage();

        Bundle args = new Bundle();
        args.putInt(CALENDAR_ID, calendarID);
        calendarDetailPage.setArguments(args);
        return calendarDetailPage;
    }


}
