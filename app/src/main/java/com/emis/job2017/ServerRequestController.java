package com.emis.job2017;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by jo5 on 17/10/17.
 */

public class ServerRequestController {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";
    public static final int RETRIES = 3;
    private final static int CONNECTION_REFUSED = -999;
    private final static int SERVER_ERRORS = 500;

    private Utils.EventType requestName;
    private String method;
    private String body;
    private int maxRetries;
    private HttpURLConnection conn;
    private int responseCode;
    private int currentRetry = 3;
    private JSONObject jsonResponse;
    private String accessToken;


    public ServerRequestController(Utils.EventType requestName, String method, String body, int maxRetries, String accessToken){
        this.requestName = requestName;
        this.method = method;
        this.body = body;
        this.maxRetries = maxRetries;
        this.accessToken = accessToken;
    }


    @Nullable
    private static HttpURLConnection getHTTPConnectionObject(URL url) {

        HttpURLConnection cxn;
        try {
            cxn = (HttpURLConnection) url.openConnection();
            return cxn;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONObject performConnection(URL url) {

        Log.d("ServerRequestController", "Request Body -- " + body);

        try {

            conn = getHTTPConnectionObject(url);

            if(method.equals(POST_METHOD)) {
                conn.setDoInput(true);
                conn.setDoOutput(true);
            }

            conn.setRequestMethod(method);
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(5000);

            //TODO: check headers
//            conn.setRequestProperty("Content-Type", contentType);
//
//            conn.setRequestProperty("Accept-Charset", "utf-8");
//            conn.setRequestProperty("Connection", "keep-alive");
////            conn.setRequestProperty("market", "");
//            conn.setRequestProperty("app_uuid", Utils.getPhoneUuid());
//            conn.setRequestProperty("os", "android");
//            conn.setRequestProperty("os_version", Build.VERSION.RELEASE);
//            conn.setRequestProperty("app_version", context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
//            conn.setRequestProperty("X-Authorization", accessToken);

            conn.connect();

            responseCode = conn.getResponseCode();

            Log.d("ServerRequestControl","Request response code: " + String.valueOf(responseCode));

            String response = receivedResponse();
            if(response == null)
                return null;

            Log.d("ServerRequestController", "Request response: " + response);

            if (responseCode >= SERVER_ERRORS)
                retry(url);

            jsonResponse = (!response.isEmpty()) ? new JSONObject(response) : new JSONObject();
            jsonResponse.put("responseCode", responseCode);

        }catch(java.net.SocketTimeoutException e) {
            e.printStackTrace();
            retry(url);
        }catch (UnknownHostException e){
            retry(url);
        } catch (IOException e) {
            try {
                jsonResponse = new JSONObject();
                jsonResponse.put("responseCode", CONNECTION_REFUSED);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        catch (JSONException e) {
            try {
                jsonResponse = new JSONObject();
                jsonResponse.put("responseCode", CONNECTION_REFUSED);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        return jsonResponse;
    }

    public JSONObject sendRequest(URL url){

        //TODO: switch - case for access_token

        return performConnection(url);

    }

    private String receivedResponse() throws IOException {
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        // retrieve the response from server
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            if(responseCode == 200 || responseCode == 201 || responseCode == 202 || responseCode == 206)
                is = conn.getInputStream();
            else
                is = conn.getErrorStream();

            if(is == null)
                return null;

            isr = new InputStreamReader(is, "UTF-8");

            int ch;
            StringBuffer sb = new StringBuffer();
            while ((ch = isr.read()) != -1) {
                sb.append((char) ch);
            }
            return sb.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
            if (isr != null) {
                isr.close();
            }
        }
    }

    private void retry(URL url){
        if (currentRetry <= maxRetries - 1) {
            Log.d("ServerRequestController", "Retry number " + currentRetry);
            SystemClock.sleep(2 ^ currentRetry * 1000);
            currentRetry++;
            performConnection(url);
        }
    }

}