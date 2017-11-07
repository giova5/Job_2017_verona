package com.emis.job2017;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jo5 on 23/10/17.
 */

public class ServerManagerService extends IntentService {

    private static final String TAG = "ServerManagerService";

    private static final String RESPONSE_CODE = "responseCode";
    public static final String OPERATION_SUCCESS_200_OK = "200";
    public static final String OPERATION_SUCCESS_201_OK = "201";
    public static final String OPERATION_SUCCESS_202_OK = "202";
    private static final int NO_INTERNET_CONNECTION = -1000;

    //commands switch case
    public static final String COMMAND = "COMMAND";
    public static final String COMMAND_AUTHENTICATE = "COMMAND_AUTHENTICATE";
    public static final String GET_JOB_CALENDAR = "COMMAND_GET_JOB_CALENDAR";
    public static final String NEWS = "COMMAND_NEWS";
    public static final String GET_EXHIBITORS_INFO = "COMMAND_EXHIBITORS_INFO";

    //Requests parameters
    public static final String AUTHENTICATE_EMAIL = "AUTHENTICATE_EMAIL";
    public static final String AUTHENTICATE_PSW = "AUTHENTICATE_PSW";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServerManagerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent == null) {
            Log.d(TAG, "onHandleIntent called with null intent, probably has been restarted...");
            // Without params -> do nothing
            return;
        }

        String command = intent.getStringExtra(COMMAND);

        switch (command){
            //All requests
            case COMMAND_AUTHENTICATE:
                String email = intent.getStringExtra(AUTHENTICATE_EMAIL);
                String psw = intent.getStringExtra(AUTHENTICATE_PSW);

                ServerOperations authenticateRequest = new ServerOperations(Utils.EventType.USER_AUTHENTICATE);
                JSONObject authenticateResponse = authenticateRequest.userAuthenticate(email, psw);
                //TODO: manage response
                break;

            case GET_JOB_CALENDAR:
                ServerOperations getJobCalendarRequest = new ServerOperations(Utils.EventType.GET_PROGRAM);
                JSONObject getJobCalendarResponse = getJobCalendarRequest.getJobCalendar();
                //TODO: manage response
                break;
        }

    }

    private void manageResponse(JSONObject jsonResponse){

        try {

            if (jsonResponse == null) {
                jsonResponse = new JSONObject();
                jsonResponse.put(RESPONSE_CODE, NO_INTERNET_CONNECTION);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
