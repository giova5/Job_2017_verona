package com.emis.job2017.models;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by jo5 on 02/11/17.
 */

public class ExhibitorsModel extends RealmObject{

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
//    private List<String> arrayEmails;
    private String email1;
    private String email2;
    private String email3;

    public ExhibitorsModel(){

    }

    public ExhibitorsModel(int idExhibitor, int idCategory, int idPadiglione, String name, String massima, String description, String standNumber, String standCoordinates,
                           String webSite, String youtubeLink, String phoneNumber, String logoPath, String descriptionNoHtml, String email1, String email2, String email3){

        this.idExhibitor = idExhibitor;
        this.idCategory = idCategory;
        this.idPadiglione = idPadiglione;
        this.name = name;
        this.massima = massima;
        this.description = description;
        this.standNumber = standNumber;
        this.standCoordinates = standCoordinates;
        this.webSite = webSite;
        this.youtubeLink = youtubeLink;
        this.phoneNumber = phoneNumber;
        this.logoPath = logoPath;
        this.descriptionNoHtml = descriptionNoHtml;
        this.email1 = email1;
        this.email2 = email2;
        this.email3 = email3;

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

  /*  public List<String> getArrayEmails() {
        return arrayEmails;
    }

    public void setArrayEmails(List<String> arrayEmails) {
        this.arrayEmails = arrayEmails;
    }*/

    public String getMassima() {
        return massima;
    }

    public void setMassima(String massima) {
        this.massima = massima;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    /*
* Avoids issues with Realm's thread policy
* */
    public static ExhibitorsModel cloneObject(ExhibitorsModel exhibitorsModel) {
        return new ExhibitorsModel(
                exhibitorsModel.getIdExhibitor(),
                exhibitorsModel.getIdCategory(),
                exhibitorsModel.getIdPadiglione(),
                exhibitorsModel.getName(),
                exhibitorsModel.getMassima(),
                exhibitorsModel.getDescription(),
                exhibitorsModel.getStandNumber(),
                exhibitorsModel.getStandCoordinates(),
                exhibitorsModel.getWebSite(),
                exhibitorsModel.getStandNumber(),
                exhibitorsModel.getPhoneNumber(),
                exhibitorsModel.getLogoPath(),
                exhibitorsModel.getDescriptionNoHtml(),
                exhibitorsModel.getEmail1(),
                exhibitorsModel.getEmail2(),
                exhibitorsModel.getEmail3()
        );
    }
}
