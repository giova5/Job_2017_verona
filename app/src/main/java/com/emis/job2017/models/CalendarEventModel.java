package com.emis.job2017.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by jo5 on 25/10/17.
 */

public class CalendarEventModel extends RealmObject {

    //TODO: implement model

    private int idProgram;
    private double startTime;
    private double endTime;
    private String location;
    private String typeOfProgramString;
    private String title;
    private String description;

    public CalendarEventModel(){

    }

    public CalendarEventModel(int idProgram, double startTime, double endTime, String location, String typeOfProgramString, String title, String description){
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

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
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
                calendarEventModel.getTitle(),
                calendarEventModel.getDescription(),
                calendarEventModel.getTypeOfProgramString()
        );
    }
}

