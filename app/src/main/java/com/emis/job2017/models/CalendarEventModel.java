package com.emis.job2017.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by jo5 on 25/10/17.
 */

public class CalendarEventModel extends RealmObject {

    //TODO: implement model

    private int idProgram;
    private long startTime;
    private long endTime;
    private String location;
    private String typeOfProgramString;
    private String title;
    private String description;

    public CalendarEventModel(){

    }

    public CalendarEventModel(int idProgram, long startTime, long endTime, String location, String typeOfProgramString, String title, String description){
        this.idProgram = idProgram;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.title = title;
        this.description = description;
        this.typeOfProgramString = typeOfProgramString;

    }

    public int getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeOfProgramString() {
        return typeOfProgramString;
    }

    public void setTypeOfProgramString(String typeOfProgramString) {
        this.typeOfProgramString = typeOfProgramString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*
    * Avoids issues with Realm's thread policy
    * */
    public static CalendarEventModel cloneObject(CalendarEventModel calendarEventModel) {
        return new CalendarEventModel(
                calendarEventModel.getIdProgram(),
                calendarEventModel.getStartTime(),
                calendarEventModel.getEndTime(),
                calendarEventModel.getLocation(),
                calendarEventModel.getTypeOfProgramString(),
                calendarEventModel.getTitle(),
                calendarEventModel.getDescription()
        );
    }
}

