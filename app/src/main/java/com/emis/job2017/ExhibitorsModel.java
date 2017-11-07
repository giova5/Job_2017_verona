package com.emis.job2017;

import java.util.List;

/**
 * Created by jo5 on 02/11/17.
 */

public class ExhibitorsModel {

    private int idExhibitor;
    private int idCategory;
    private int idPadiglione;
    private String name;
    private String massima;
    private String description;
    private String standNumber;
    //(X,Y)
    private String standCoordinates;
    private String webSite;
    private String youtubeLink;
    private String phoneNumber;
//    private List<ExhibitorsCategory> categoryList;
//    private List<ExhibitorsPadiglione> padiglioneList;
    private String logoPath;
    private String descriptionNoHtml;
    private List<String> arrayEmails;

    public ExhibitorsModel(){

    }

    public int getIdExhibitor() {
        return idExhibitor;
    }

    public void setIdExhibitor(int idExhibitor) {
        this.idExhibitor = idExhibitor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdPadiglione() {
        return idPadiglione;
    }

    public void setIdPadiglione(int idPadiglione) {
        this.idPadiglione = idPadiglione;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStandNumber() {
        return standNumber;
    }

    public void setStandNumber(String standNumber) {
        this.standNumber = standNumber;
    }

    public String getStandCoordinates() {
        return standCoordinates;
    }

    public void setStandCoordinates(String standCoordinates) {
        this.standCoordinates = standCoordinates;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


//    public List<ExhibitorsCategory> getCategoryList() {
//        return categoryList;
//    }
//
//    public void setCategoryList(List<ExhibitorsCategory> categoryList) {
//        this.categoryList = categoryList;
//    }

//    public List<ExhibitorsPadiglione> getPadiglioneList() {
//        return padiglioneList;
//    }
//
//    public void setPadiglioneList(List<ExhibitorsPadiglione> padiglioneList) {
//        this.padiglioneList = padiglioneList;
//    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getDescriptionNoHtml() {
        return descriptionNoHtml;
    }

    public void setDescriptionNoHtml(String descriptionNoHtml) {
        this.descriptionNoHtml = descriptionNoHtml;
    }

    public List<String> getArrayEmails() {
        return arrayEmails;
    }

    public void setArrayEmails(List<String> arrayEmails) {
        this.arrayEmails = arrayEmails;
    }

    public String getMassima() {
        return massima;
    }

    public void setMassima(String massima) {
        this.massima = massima;
    }
}
