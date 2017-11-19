package com.emis.job2017;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.emis.job2017.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.emis.job2017.ServerRequestController.GET_METHOD;
import static com.emis.job2017.ServerRequestController.POST_METHOD;
import static com.emis.job2017.ServerRequestController.RETRIES;

/**
 * Created by jo5 on 23/10/17.
 * This class contains all server requests as static method.
 */

public class ServerOperations {

    private URL url;
    private static String urlHeaderAsString = "http://job2017.webiac.it/access/";
    private static String ACORGPROFILO = "acvisprofilo/";
    private static String ACVISLOGIN = "acvislogin/";
    private static String ACVISPROGRAMMA = "acvisprogramma/";
    private static String ACVISQUESTIONARIO = "acvisquestionario/";
    private static String ACORGVISINGRESSOREGISTRA = "acorgvisingressoregistra/";
    private static String ACVISPROFILO = "acvisprofilo/";
    private static String ACORGNOTIFICHE = "acorgnotifiche/";
    private static String ACVISESPELENCO = "acvisespelenco/";

    private static String APP_1 = "?app=1";

    private static String AUTHENTICATE = "json_login.php";
    private static String GET_ACCESS_TOKEN = "json_conversione_token.php";
    private static String GET_EVENT_INFO = "json.php?app=1";
    private static String GET_EXHIBITORS_CATEGORIES = "json_categorie.php?app=1";
    private static String GET_EXHIBITORS_PADIGLIONI = "json_padiglioni.php?app=1";
    private static String GET_EXHIBITORS_INFO = "json_espositori.php?app=1";
    private static String GET_PROGRAM = "json.php?app=1";
    private static String NEWS = "json.php";
    private static String PROFILE_LOGGED_USER = "json_profilo_loggato.php";
    private static String PREFERITI_ELENCO = "json_preferiti_elenco.php";
    private static String PREFERITI_CONTROLLA = "json_preferiti.php";
    private static String ATTESTATION = "attestato.php";



    public ServerOperations(Utils.EventType eventType) {

        String currentEndPointForRequest = null;

        try {

            switch (eventType) {
                case GET_ACCESS_TOKEN:
                    currentEndPointForRequest = urlHeaderAsString + ACVISLOGIN + GET_ACCESS_TOKEN;
                    break;
                case USER_AUTHENTICATE:
                    currentEndPointForRequest = urlHeaderAsString + ACVISLOGIN + AUTHENTICATE;
                    break;
                case GET_EVENT_INFO:
                    currentEndPointForRequest = urlHeaderAsString + ACORGPROFILO + GET_EVENT_INFO;
                    break;
                case GET_EXHIBITORS_CATEGORIES:
                    currentEndPointForRequest = urlHeaderAsString + ACVISESPELENCO + GET_EXHIBITORS_CATEGORIES;
                    break;
                case GET_EXHIBITORS_PADIGLIONI:
                    currentEndPointForRequest = urlHeaderAsString + ACVISESPELENCO + GET_EXHIBITORS_PADIGLIONI;
                    break;
                case GET_EXHIBITORS_INFO:
                    currentEndPointForRequest = urlHeaderAsString + ACVISESPELENCO + GET_EXHIBITORS_INFO;
                    break;
                case GET_PROGRAM:
                    currentEndPointForRequest = urlHeaderAsString + ACVISPROGRAMMA + GET_PROGRAM;
                    break;
                case NEWS:
                    currentEndPointForRequest = urlHeaderAsString + ACORGNOTIFICHE + NEWS;
                    break;
                case GET_USER_PROFILE_INFO:
                    currentEndPointForRequest = urlHeaderAsString + ACORGPROFILO + PROFILE_LOGGED_USER;
                    break;
                case PREFERITI_ELENCO:
                    currentEndPointForRequest = urlHeaderAsString + ACVISESPELENCO + PREFERITI_ELENCO;
                    break;
                case PREFERITI_CONTROLLA:
                    currentEndPointForRequest = urlHeaderAsString + ACVISESPELENCO + PREFERITI_CONTROLLA;
                    break;
                case GET_ATTESTATION:
                    currentEndPointForRequest = urlHeaderAsString + ACVISQUESTIONARIO + ATTESTATION + APP_1;
                    break;
                default:
                    currentEndPointForRequest = null;
                    break;

            }

            url = new URL(currentEndPointForRequest);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /*********************************** USER AUTHENTICATE METHODS ************************************/

    public static void sendUserAuthenticate(Context context, String email, String psw){
        Intent smIntent = new Intent(context, ServerManagerService.class);
        smIntent.putExtra(ServerManagerService.COMMAND, ServerManagerService.COMMAND_AUTHENTICATE);
        smIntent.putExtra(ServerManagerService.AUTHENTICATE_EMAIL, email);
        smIntent.putExtra(ServerManagerService.AUTHENTICATE_PSW, psw);
        context.startService(smIntent);
    }

    public JSONObject userAuthenticate(String email, String psw) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonResponse = new JSONObject();

        byte[] emailByteArray, pswByteArray;

        try {
            emailByteArray = Utils.encryptMsg(email, Utils.generateKey());
            pswByteArray = Utils.encryptMsg(psw, Utils.generateKey());

            String hexEmail = Utils.toHexString(emailByteArray);
            String hexPsw = Utils.toHexString(pswByteArray);

            Log.d("Encrypt", hexEmail);
            Log.d("Encrypt", hexPsw);

            jsonObject.put("acemail", hexEmail);
            jsonObject.put("acpass", hexPsw);

            jsonResponse = sendRequest(Utils.EventType.USER_AUTHENTICATE, POST_METHOD, jsonObject.toString(), RETRIES);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return jsonResponse;

    }

    /*********************************** USER GET PROFILE METHODS ************************************/


    public JSONObject getUserProfile(){
        return sendRequest(Utils.EventType.GET_USER_PROFILE_INFO, GET_METHOD, null, RETRIES);
    }

    /*********************************** GET ACCESS TOKEN METHODS ************************************/

    public JSONObject getAccessToken(String refreshToken) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonObject.put("refresh_token", refreshToken);
            jsonResponse = sendGetRequestWithParams(Utils.EventType.GET_ACCESS_TOKEN, GET_METHOD, jsonObject.toString(), RETRIES);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }


    /*********************************** GET JOB CALENDAR METHODS ************************************/

    public JSONObject getJobCalendar(){
        return sendRequest(Utils.EventType.USER_AUTHENTICATE, GET_METHOD, null, RETRIES);
    }

    public void sendGetJobCalendar(Context context){
        Intent smIntent = new Intent(context, ServerManagerService.class);
        smIntent.putExtra(ServerManagerService.COMMAND, ServerManagerService.GET_JOB_CALENDAR);
        context.startService(smIntent);
    }

    /*********************************** GET NEWS METHODS ************************************/

    public JSONObject getNews(){
        return sendRequest(Utils.EventType.NEWS, GET_METHOD, null, RETRIES);
    }

    public void sendGetNews(Context context){
        Intent smIntent = new Intent(context, ServerManagerService.class);
        smIntent.putExtra(ServerManagerService.COMMAND, ServerManagerService.NEWS);
        context.startService(smIntent);
    }

    /*********************************** GET EXHIBITORS METHODS ************************************/

    public JSONObject getExhibitors(){
        return sendRequest(Utils.EventType.GET_EXHIBITORS_INFO, GET_METHOD, null, RETRIES);
    }

    public void sendGetExhibitors(Context context){
        Intent smIntent = new Intent(context, ServerManagerService.class);
        smIntent.putExtra(ServerManagerService.COMMAND, ServerManagerService.GET_EXHIBITORS_INFO);
        context.startService(smIntent);
    }

    /*********************************** GET PREFERITI METHODS ************************************/

    public JSONObject getFavoritesList(){
        return sendRequest(Utils.EventType.PREFERITI_ELENCO, GET_METHOD, null, RETRIES);
    }

    public static void sendGetFavoritesList(Context context){
        Intent smIntent = new Intent(context, ServerManagerService.class);
        smIntent.putExtra(ServerManagerService.COMMAND, ServerManagerService.GET_FAVORITES_LIST);
        context.startService(smIntent);
    }

    public JSONObject checkFavorite(int exhibitorID){

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonObject.put("exhibitorID", exhibitorID);
            jsonResponse = sendGetRequestWithParams(Utils.EventType.PREFERITI_CONTROLLA, GET_METHOD, jsonObject.toString(), RETRIES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public static void sendCheckFavorites(Context context, int exhibitorID){
        Intent smIntent = new Intent(context, ServerManagerService.class);
        smIntent.putExtra(ServerManagerService.COMMAND, ServerManagerService.GET_FAVORITES_LIST);
        context.startService(smIntent);
    }

    /*********************************** GET ATTESTATION METHODS ************************************/

    public JSONObject getAttestation(){
        return sendRequest(Utils.EventType.GET_ATTESTATION, GET_METHOD, null, RETRIES);
    }

    public static void sendGetAttestation(Context context){
        Intent smIntent = new Intent(context, ServerManagerService.class);
        smIntent.putExtra(ServerManagerService.COMMAND, ServerManagerService.GET_ATTESTATION);
        context.startService(smIntent);
    }

    /*********************************** SEND REQUEST METHOD ************************************/

    synchronized private JSONObject sendRequest(Utils.EventType eventType, String method, String jsonObject, int maxRetries) {

        String accessToken = null;

        switch (eventType) {
            case GET_USER_PROFILE_INFO:
            case PREFERITI_ELENCO:
            case PREFERITI_CONTROLLA:
            case GET_ATTESTATION:
                accessToken = JobApplication.getAccessToken();
                break;
        }

        ServerRequestController serverRequestManager = new ServerRequestController(eventType, method, jsonObject, maxRetries, accessToken);
        return serverRequestManager.sendRequest(url, accessToken);
    }

    synchronized private JSONObject sendGetRequestWithParams(Utils.EventType eventType, String method, String jsonObject, int maxRetries) {

        String accessToken = null;

        switch (eventType) {
            case PREFERITI_CONTROLLA:
                accessToken = JobApplication.getAccessToken();
                break;
        }
        ServerRequestController serverRequestManager = new ServerRequestController(eventType, method, jsonObject, maxRetries, accessToken);
        return serverRequestManager.sendGetRequestWithParams(url);
    }

}
