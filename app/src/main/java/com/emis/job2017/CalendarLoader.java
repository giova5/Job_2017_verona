package com.emis.job2017;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jo5 on 25/10/17.
 */

public class CalendarLoader extends BaseAsyncLoader<List<CalendarEventModel>>{

    public CalendarLoader(Context c) {
        super(c);
    }

    @Override
    public List<CalendarEventModel> loadInBackground() {

        List<CalendarEventModel> calendarList;

        ServerOperations getProgram = new ServerOperations(Utils.EventType.GET_PROGRAM);
        JSONObject getProgramResponse = getProgram.getJobCalendar();

        calendarList = parseGetProgramResponse(getProgramResponse);

        return calendarList;
    }

    @Override
    protected void releaseResources(List<CalendarEventModel> dataTobeReleased) {

    }

    @Override
    protected void registerDataObserver() {

    }

    @Override
    protected void unregisterDataObserver() {

    }

    private List<CalendarEventModel> parseGetProgramResponse(JSONObject getProgramResponse){

        List<CalendarEventModel> eventModelList = new ArrayList<>();

        try {

            JSONArray getProgramResponseArray = getProgramResponse.getJSONArray("Programma");

            for(int i = 0; i < getProgramResponseArray.length(); i++){
                JSONObject current = getProgramResponseArray.getJSONObject(i);

                CalendarEventModel calendarEventModel = new CalendarEventModel();
                calendarEventModel.setIdProgram(current.getInt("idprogramma"));
                calendarEventModel.setStartTime(Double.valueOf(current.getString("dataora_inizio_timestamp")));
                calendarEventModel.setEndTime(Double.valueOf(current.getString("dataora_fine_timestamp")));
                calendarEventModel.setLocation(current.getString("location"));
                calendarEventModel.setTypeOfProgramString(current.getString("tipi_stringa"));
                calendarEventModel.setTitle(current.getString("titolo"));
                calendarEventModel.setDescription(current.getString("descrizione"));
                eventModelList.add(calendarEventModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventModelList;
    }



}
