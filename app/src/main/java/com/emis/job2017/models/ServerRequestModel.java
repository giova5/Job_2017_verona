package com.emis.job2017.models;

import com.emis.job2017.Utils;

/**
 * Created by jo5 on 17/10/17.
 */

public class ServerRequestModel {

    private Utils.EventType eventType;
    private String response;
    private String responseCode;

    public ServerRequestModel(){

    }

    public ServerRequestModel(Utils.EventType eventType, String response, String responseCode){
        this.eventType = eventType;
        this.response = response;
        this.responseCode = responseCode;
    }

    public void updateServerRequestModel(Utils.EventType eventType, String response, String responseCode){
        this.eventType = eventType;
        this.response = response;
        this.responseCode = responseCode;
    }

    public Utils.EventType getEventType() {
        return eventType;
    }

    public void setEventType(Utils.EventType eventType) {
        this.eventType = eventType;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }


}
