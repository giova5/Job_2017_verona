package com.emis.job2017.models;

/**
 * Created by jo5 on 02/11/17.
 */

public class ExhibitorsCategory {

    private int idCategory;
    private String title;
    private String color;

    public ExhibitorsCategory(){

    }

    public ExhibitorsCategory(int idCategory, String title, String color){
        this.idCategory = idCategory;
        this.title = title;
        this.color = color;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
