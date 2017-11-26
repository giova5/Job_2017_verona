package com.emis.job2017.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jo5 on 14/11/17.
 */

public class UserProfileModel extends RealmObject {

    @PrimaryKey
    private int realmId;
    private int userID;
    private String userName;
    private String userSurname;
    private String userEmail;
    private String birthPlace;
    private String dayOfBirth;
    private String gender;
    private String city;
    private String provincia;
    private String visibleProfile;
    private String gruppoPartecipanti;
    private String titoloDiStudio;
    private String profession;
    private String urlTicket;
    private String dataRegistrazionTimestamp;
    private String refreshToken;
    private String accessToken;
    private String urlToAttestation;

    public UserProfileModel(){

    }

    public static UserProfileModel cloneObject(UserProfileModel userProfileModel) {

        UserProfileModel cloned = new UserProfileModel();

        cloned.setUserID(userProfileModel.getUserID());
        cloned.setUserName(userProfileModel.getUserName());
        cloned.setUserSurname(userProfileModel.getUserSurname());
        cloned.setUserEmail(userProfileModel.getUserEmail());
        cloned.setBirthPlace(userProfileModel.getBirthPlace());
        cloned.setDayOfBirth(userProfileModel.getDayOfBirth());
        cloned.setGender(userProfileModel.getGender());
        cloned.setCity(userProfileModel.getCity());
        cloned.setProvincia(userProfileModel.getProvincia());
        cloned.setVisibleProfile(userProfileModel.getVisibleProfile());
        cloned.setGruppoPartecipanti(userProfileModel.getGruppoPartecipanti());
        cloned.setTitoloDiStudio(userProfileModel.getTitoloDiStudio());
        cloned.setProfession(userProfileModel.getProfession());
        cloned.setUrlTicket(userProfileModel.getUrlTicket());
        cloned.setDataRegistrazionTimestamp(userProfileModel.getDataRegistrazionTimestamp());
        cloned.setRefreshToken(userProfileModel.getRefreshToken());
        cloned.setAccessToken(userProfileModel.getAccessToken());
        cloned.setUrlToAttestation(userProfileModel.getUrlToAttestation());

        return cloned;
    }

    public String getUrlToAttestation() {
        return urlToAttestation;
    }

    public void setUrlToAttestation(String urlToAttestation) {
        this.urlToAttestation = urlToAttestation;
    }

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getVisibleProfile() {
        return visibleProfile;
    }

    public void setVisibleProfile(String visibleProfile) {
        this.visibleProfile = visibleProfile;
    }

    public String getGruppoPartecipanti() {
        return gruppoPartecipanti;
    }

    public void setGruppoPartecipanti(String gruppoPartecipanti) {
        this.gruppoPartecipanti = gruppoPartecipanti;
    }

    public String getTitoloDiStudio() {
        return titoloDiStudio;
    }

    public void setTitoloDiStudio(String titoloDiStudio) {
        this.titoloDiStudio = titoloDiStudio;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUrlTicket() {
        return urlTicket;
    }

    public void setUrlTicket(String urlTicket) {
        this.urlTicket = urlTicket;
    }

    public String getDataRegistrazionTimestamp() {
        return dataRegistrazionTimestamp;
    }

    public void setDataRegistrazionTimestamp(String dataRegistrazionTimestamp) {
        this.dataRegistrazionTimestamp = dataRegistrazionTimestamp;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
