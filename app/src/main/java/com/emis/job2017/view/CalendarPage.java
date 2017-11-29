package com.emis.job2017.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.emis.job2017.R;
import com.emis.job2017.adapters.CalendarAdapter;
import com.emis.job2017.loaders.CalendarLoader;
import com.emis.job2017.models.CalendarEventModel;
import com.emis.job2017.utils.RealmUtils;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class CalendarPage extends Fragment implements LoaderManager.LoaderCallbacks<List<CalendarEventModel>>, ListView.OnItemClickListener {

    private static int CALENDAR_LOADER_ID = 1;
    private CalendarAdapter calendarAdapter;
    private ListView calendarList;
    private ProgressBar calendarSpinner;
    private List<CalendarEventModel> fullCalendarList = new LinkedList<>();
    private EditText calendarSearchBar;


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

        Log.d("Crash test", " -------- CalendarPage onCreateView() --------");

        View view = inflater.inflate(R.layout.fragment_calendar_page, container, false);
        calendarList = (ListView) view.findViewById(R.id.calendar_list);
        calendarSearchBar = (EditText) view.findViewById(R.id.calendar_search_bar);
        calendarSpinner = (ProgressBar) view.findViewById(R.id.calendar_progress_bar);
        Log.d("Crash test", " -------- CalendarPage before create CalendarAdapter --------");
        calendarAdapter = new CalendarAdapter(null, getActivity());
        calendarList.setOnItemClickListener(this);
        Log.d("Crash test", " -------- CalendarPage before call setAdapter(...) --------");
        calendarList.setAdapter(calendarAdapter);

        setUpTextChangedListener();


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Crash test", " -------- CalendarPage before call initLoader --------");
        getActivity().getLoaderManager().restartLoader(CALENDAR_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<CalendarEventModel>> onCreateLoader(int id, Bundle args) {
        calendarSpinner.setVisibility(View.VISIBLE);
        return new CalendarLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<CalendarEventModel>> loader, List<CalendarEventModel> data) {
        calendarSpinner.setVisibility(View.GONE);
        data = (data == null) ? RealmUtils.getSortedCalendar() : data;
        calendarAdapter.switchItems(data);
        fullCalendarList.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<CalendarEventModel>> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        closeKeyboard();
        CalendarEventModel calendarEventModel = calendarAdapter.getListFiltered().get(position);
        startFragment(calendarEventModel.getIdProgram());
    }

    private void startFragment(int calendarID){
        CalendarDetailPage fragment2 = CalendarDetailPage.newInstance(calendarID);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setUpTextChangedListener(){
        calendarSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //When text changes this method is called.
                calendarAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
