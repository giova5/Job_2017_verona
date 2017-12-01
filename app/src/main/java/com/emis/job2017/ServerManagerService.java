package com.emis.job2017;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.emis.job2017.models.UserProfileModel;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;
import com.emis.job2017.view.LoginActivity;
import com.emis.job2017.view.SplashScreen;
import com.emis.job2017.view.UserProfilePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo5 on 23/10/17.
 */

public class ServerManagerService extends IntentService {

    private static final String TAG = "ServerManagerService";

    public static final String RESPONSE_CODE = "responseCode";
    public static final String OPERATION_SUCCESS_200_OK = "200";
    public static final String OPERATION_SUCCESS_201_OK = "201";
    public static final String OPERATION_SUCCESS_202_OK = "202";
    public static final int NO_INTERNET_CONNECTION = -999;
    public static final String OPERATION_FAILURE_401 = "401";

    //commands switch case
    public static final String COMMAND = "COMMAND";
    public static final String COMMAND_AUTHENTICATE = "COMMAND_AUTHENTICATE";
    public static final String GET_JOB_CALENDAR = "COMMAND_GET_JOB_CALENDAR";
    public static final String NEWS = "COMMAND_NEWS";
    public static final String GET_EXHIBITORS_INFO = "COMMAND_EXHIBITORS_INFO";
    public static final String GET_FAVORITES_LIST = "GET_FAVORITES_LIST";
    public static final String CHECK_FAVORITE = "CHECK_FAVORITE";
    public static final String GET_ATTESTATION = "GET_ATTESTATION";
    public static final String GET_ACCESS_TOKEN = "GET_ACCESS_TOKEN";

    //Requests parameters
    public static final String AUTHENTICATE_EMAIL = "AUTHENTICATE_EMAIL";
    public static final String AUTHENTICATE_PSW = "AUTHENTICATE_PSW";
    public static final String DEALER_ID = "DEALER_ID";

    //Utils
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    public static final String AUTHENTICATION_SUCCESS = "AUTHENTICATION_SUCCESS";
    public static final String AUTHENTICATION_FAILURE = "AUTHENTICATION_FAILURE";
    public static final String ACCESS_TOKEN_FAILURE = "ACCESS_TOKEN_FAILURE";
    public static final String LOGIN_FAILURE = "LOGIN_FAILURE";
    public static final String GET_ATTESTATION_SUCCESS = "GET_ATTESTATION_SUCCESS";
    public static final String GET_ATTESTATION_FAILURE = "GET_ATTESTATION_FAILURE";
    public static final String GET_ACCESS_TOKEN_SUCCESS = "GET_ACCESS_TOKEN_SUCCESS";
    public static final String GET_ACCESS_TOKEN_FAILURE = "GET_ACCESS_TOKEN_FAILURE";

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
                            JobApplication.setAccessToken(accessToken);
                            ServerOperations getUserProfileRequest = new ServerOperations(Utils.EventType.GET_USER_PROFILE_INFO);
                            JSONObject getUserProfileResponse = getUserProfileRequest.getUserProfile();
                            getUserProfileResponse = checkIfInitResponse(getUserProfileResponse);
                            if(getUserProfileResponse.getString(RESPONSE_CODE).equals(OPERATION_SUCCESS_200_OK)) {
                                parseAndSaveGetUserProfileResponse(getUserProfileResponse);
                                sendCallbackToListener(LoginActivity.ResponseReceiver.LOCAL_ACTION, AUTHENTICATION_SUCCESS, getUserProfileResponse);
                            }else{
                                sendCallbackToListener(LoginActivity.ResponseReceiver.LOCAL_ACTION, AUTHENTICATION_FAILURE, getUserProfileResponse);
                            }
                        }else{
                            sendCallbackToListener(LoginActivity.ResponseReceiver.LOCAL_ACTION, ACCESS_TOKEN_FAILURE, getAccessTokenResponse);
                        }
                    }else {
                        sendCallbackToListener(LoginActivity.ResponseReceiver.LOCAL_ACTION, LOGIN_FAILURE, authenticateResponse);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case GET_ACCESS_TOKEN:
                try {
                    ServerOperations getAccessTokenRequest = new ServerOperations(Utils.EventType.GET_ACCESS_TOKEN);
                    JSONObject getAccessTokenResponse = getAccessTokenRequest.getAccessToken(RealmUtils.getUser().getRefreshToken());
                    getAccessTokenResponse = checkIfInitResponse(getAccessTokenResponse);
                    if(getAccessTokenResponse.getString(RESPONSE_CODE).equals(OPERATION_SUCCESS_200_OK)) {
                        String accessToken = getAccessTokenFromResponse(getAccessTokenResponse);
                        JobApplication.setAccessToken(accessToken);
                        sendCallbackToListener(SplashScreen.ResponseReceiver.LOCAL_ACTION, GET_ACCESS_TOKEN_SUCCESS, getAccessTokenResponse);
                    }else{
                        sendCallbackToListener(SplashScreen.ResponseReceiver.LOCAL_ACTION, GET_ACCESS_TOKEN_FAILURE, getAccessTokenResponse);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case GET_JOB_CALENDAR:
                ServerOperations getJobCalendarRequest = new ServerOperations(Utils.EventType.GET_PROGRAM);
                JSONObject getJobCalendarResponse = getJobCalendarRequest.getJobCalendar();
                //TODO: manage response
                break;

            case GET_FAVORITES_LIST:

                try {
                    ServerOperations getFavoritesRequest = new ServerOperations(Utils.EventType.PREFERITI_ELENCO);
                    JSONObject getFavoritesResponse = getFavoritesRequest.getFavoritesList();
                    getFavoritesResponse = checkIfInitResponse(getFavoritesResponse);
                    if(getFavoritesResponse.getString(RESPONSE_CODE).equals(OPERATION_SUCCESS_200_OK)) {
                        parseAndSaveGetFavoritesList(getFavoritesResponse);
                    }else{
                        //TODO: !200ok
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case CHECK_FAVORITE:
                break;

            case GET_ATTESTATION:
                try {
                    ServerOperations getAttestationRequest = new ServerOperations(Utils.EventType.GET_ATTESTATION);
                    JSONObject getAttestationResponse = getAttestationRequest.getAttestation();
                    getAttestationResponse = checkIfInitResponse(getAttestationResponse);
                    if(getAttestationResponse.getString(RESPONSE_CODE).equals(OPERATION_SUCCESS_200_OK)) {
                        String attestationLink = parseGetAttestationResponse(getAttestationResponse);
                        RealmUtils.setUserAttestationUrl(attestationLink);
                        sendCallbackToListener(UserProfilePage.ResponseReceiver.LOCAL_ACTION, GET_ATTESTATION_SUCCESS, getAttestationResponse);
                    }else{
                        sendCallbackToListener(UserProfilePage.ResponseReceiver.LOCAL_ACTION, GET_ATTESTATION_FAILURE, getAttestationResponse);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case GET_EXHIBITORS_INFO:
                String dealerID = intent.getStringExtra(DEALER_ID);

                ServerOperations getDealersInfo = new ServerOperations(Utils.EventType.GET_EXHIBITORS_INFO_WITH_PARAMS);
                JSONObject getDealersResponse = getDealersInfo.getExhibitorsWithParams(dealerID);

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

    private void parseAndSaveGetUserProfileResponse(JSONObject getUserProfileResponse){

        UserProfileModel userProfileModel = RealmUtils.getUser();

        try {

            JSONObject profileObj = getUserProfileResponse.getJSONObject("DatiVisitatore");
            userProfileModel.setUserID(profileObj.getInt("idvisitatore"));
            userProfileModel.setUserName(profileObj.getString("nome"));
            userProfileModel.setUserSurname(profileObj.getString("cognome"));
            userProfileModel.setUserEmail(profileObj.getString("email"));
            userProfileModel.setBirthPlace(profileObj.getString("nascita_luogo"));
            userProfileModel.setDayOfBirth(profileObj.getString("nascita_dataf"));
            userProfileModel.setGender(profileObj.getString("sesso"));
            userProfileModel.setCity(profileObj.getString("citta"));
            userProfileModel.setProvincia(profileObj.getString("provincia"));
            userProfileModel.setVisibleProfile(profileObj.getString("profilo_visibile"));
            userProfileModel.setGruppoPartecipanti(profileObj.getString("gruppo_partecipanti"));
            userProfileModel.setTitoloDiStudio(profileObj.getString("titolo_studio"));
            userProfileModel.setProfession(profileObj.getString("professione"));
            userProfileModel.setUrlTicket(profileObj.getString("url_biglietto"));
            userProfileModel.setDataRegistrazionTimestamp(profileObj.getString("registrazione_data_timestamp"));

            RealmUtils.saveUserProfileInfo(userProfileModel);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseAndSaveGetFavoritesList(JSONObject getFavoritesListResponse){

        List<Integer> favoritesIDs = new ArrayList<>();

        try {
            JSONArray favoritesJsonArray = getFavoritesListResponse.getJSONArray("EspositoriPreferiti");

            for(int i = 0; i < favoritesJsonArray.length(); i++){
                JSONObject current = favoritesJsonArray.getJSONObject(i);
                favoritesIDs.add(current.getInt("idespositore"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    private String parseGetAttestationResponse(JSONObject getAttestationResponse){

        JSONObject attestationObj = null;
        String attestationLink = null;

        try {
            attestationObj = getAttestationResponse.getJSONObject("Attestato");
            attestationLink = attestationObj.getString("attestato_link");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attestationLink;
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
                broadcastIntentSuccess.putExtra("CALLBACK", callbackString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.sendBroadcast(broadcastIntentSuccess);
    }
}
