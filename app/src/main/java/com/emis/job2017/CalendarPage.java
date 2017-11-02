package com.emis.job2017;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class CalendarPage extends Fragment implements LoaderManager.LoaderCallbacks<List<CalendarEventModel>>, ListView.OnItemClickListener {

    private static int CALENDAR_LOADER_ID = 1;
    private CalendarAdapter calendarAdapter;
    private ListView calendarList;
    private ProgressBar calendarSpinner;

    public CalendarPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calendar_page, container, false);
        calendarList = (ListView) view.findViewById(R.id.calendar_list);
        calendarSpinner = (ProgressBar) view.findViewById(R.id.calendar_progress_bar);
        calendarAdapter = new CalendarAdapter(null, getActivity());
        calendarList.setOnItemClickListener(this);
        calendarList.setAdapter(calendarAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getLoaderManager().initLoader(CALENDAR_LOADER_ID, null, this);

    }

    @Override
    public Loader<List<CalendarEventModel>> onCreateLoader(int id, Bundle args) {
        //TODO: put start spinner rotation
        calendarSpinner.setVisibility(View.VISIBLE);
        return new CalendarLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<CalendarEventModel>> loader, List<CalendarEventModel> data) {
        //TODO: put in adapter
        calendarSpinner.setVisibility(View.GONE);
        calendarAdapter.switchItems(data);
    }

    @Override
    public void onLoaderReset(Loader<List<CalendarEventModel>> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO: go to
    }
}
