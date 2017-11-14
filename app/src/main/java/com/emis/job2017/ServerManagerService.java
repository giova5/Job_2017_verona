package com.emis.job2017;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;

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

    //Utils
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServerManagerService(String name) {
        super(name);
    }

    public ServerManagerService(){
        super("ServerManagerService");
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
                try {
                    ServerOperations authenticateRequest = new ServerOperations(Utils.EventType.USER_AUTHENTICATE);
                    JSONObject authenticateResponse = authenticateRequest.userAuthenticate(email, psw);
                    authenticateResponse = checkIfInitResponse(authenticateResponse);
                    if(authenticateResponse.getString(RESPONSE_CODE).equals(OPERATION_SUCCESS_200_OK)) {
                        parseAndSaveCommandAuthenticate(authenticateResponse, email);
                        ServerOperations getAccessTokenRequest = new ServerOperations(Utils.EventType.GET_ACCESS_TOKEN);
                        String refreshToken = getRefreshTokenFromResponse(authenticateResponse);
                        JSONObject getAccessTokenResponse = getAccessTokenRequest.getAccessToken(refreshToken);
                        getAccessTokenResponse = checkIfInitResponse(getAccessTokenResponse);
                        if(getAccessTokenResponse.getString(RESPONSE_CODE).equals(OPERATION_SUCCESS_200_OK)){
                            //save the access token
                            String accessToken = getAccessTokenFromResponse(getAccessTokenResponse);
                            RealmUtils.saveAccessToken(accessToken);

                        //sendCallbackToListener();
                        }
                    }
                    //TODO: manage !200 responses.

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case GET_JOB_CALENDAR:
                ServerOperations getJobCalendarRequest = new ServerOperations(Utils.EventType.GET_PROGRAM);
                JSONObject getJobCalendarResponse = getJobCalendarRequest.getJobCalendar();
                //TODO: manage response
                break;

        }

    }

    private String getRefreshTokenFromResponse(JSONObject jsonResponse){

        JSONObject loginObj;
        String refreshToken = null;

        try {
            loginObj = jsonResponse.getJSONObject("Login");
            refreshToken = loginObj.getString("refresh_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return refreshToken;
    }

    private String getAccessTokenFromResponse(JSONObject jsonResponse){

        JSONObject loginObj;
        String accessToken = null;

        try {
            loginObj = jsonResponse.getJSONObject("AccessToken");
            accessToken = loginObj.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return accessToken;
    }


    /** ************************************** REQUESTS PARSING METHODS ************************************** */


    private void parseAndSaveCommandAuthenticate(JSONObject authenticateResponse, String userEmail){

        String refreshToken;

        try {
            JSONObject loginObj = authenticateResponse.getJSONObject("Login");
            refreshToken = loginObj.getString("refresh_token");
            RealmUtils.firstUserCreation(userEmail, refreshToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /** ************************************** END REQUESTS PARSING METHODS ************************************** */


    /**
     * This method create a jsonObject if response is null.
     * */
    private JSONObject checkIfInitResponse(JSONObject jsonResponse){
        try {
            if (jsonResponse == null) {
                jsonResponse = new JSONObject();
                jsonResponse.put(RESPONSE_CODE, NO_INTERNET_CONNECTION);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    private void sendCallbackToListener(String action, String callbackString, JSONObject responseObject){

        Intent broadcastIntentSuccess = new Intent();
        broadcastIntentSuccess.setAction(action);

        try {
            if(responseObject != null){
                broadcastIntentSuccess.putExtra("CODE", responseObject.get("responseCode").toString());
                broadcastIntentSuccess.putExtra("RESPONSE_BODY", responseObject.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.sendBroadcast(broadcastIntentSuccess);
    }
}
