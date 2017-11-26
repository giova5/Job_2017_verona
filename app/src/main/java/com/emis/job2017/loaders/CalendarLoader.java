package com.emis.job2017.loaders;

import android.content.Context;
import android.util.Log;

import com.emis.job2017.BaseAsyncLoader;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;
import com.emis.job2017.models.CalendarEventModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.emis.job2017.ServerManagerService.OPERATION_SUCCESS_200_OK;
import static com.emis.job2017.ServerManagerService.RESPONSE_CODE;

/**
 * Created by jo5 on 25/10/17.
 */

public class CalendarLoader extends BaseAsyncLoader<List<CalendarEventModel>> {

    public CalendarLoader(Context c) {
        super(c);
    }

    @Override
    public List<CalendarEventModel> loadInBackground() {
        Log.d("Crash test", " -------- CalendarLoader class --------");
        List<CalendarEventModel> calendarList;

        ServerOperations getProgram = new ServerOperations(Utils.EventType.GET_PROGRAM);
        JSONObject getProgramResponse = getProgram.getJobCalendar();

        try {
            if(getProgramResponse != null && getProgramResponse.getString(RESPONSE_CODE).equals(OPERATION_SUCCESS_200_OK)) {
                calendarList = parseGetProgramResponse(getProgramResponse);
                RealmUtils.removeAllCalendarObj();
                RealmUtils.saveCalendarList(calendarList);
                return RealmUtils.getSortedCalendar();
            }else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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
                calendarEventModel.setStartTime(current.getLong("dataora_inizio_timestamp"));
                calendarEventModel.setEndTime(current.getLong("dataora_fine_timestamp"));
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
