package com.emis.job2017;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo5 on 03/11/17.
 */

public class ExhibitorsLoader extends BaseAsyncLoader<List<ExhibitorsModel>> {

    public ExhibitorsLoader(Context c) {
        super(c);
    }

    @Override
    protected void releaseResources(List<ExhibitorsModel> dataTobeReleased) {

    }

    @Override
    protected void registerDataObserver() {

    }

    @Override
    protected void unregisterDataObserver() {

    }

    @Override
    public List<ExhibitorsModel> loadInBackground() {
        List<ExhibitorsModel> exhibitorsList;

        ServerOperations getExhibitors = new ServerOperations(Utils.EventType.GET_EXHIBITORS_INFO);
        JSONObject getExhibitorsResponse = getExhibitors.getExhibitors();

        exhibitorsList = parseGetExhibitorsResponse(getExhibitorsResponse);

        return exhibitorsList;
    }

    private List<ExhibitorsModel> parseGetExhibitorsResponse(JSONObject getExhibitorsResponse) {

        List<ExhibitorsModel> exhibitorsModelList = new ArrayList<>();

        try {

            JSONArray getExhibitorResponseArray = getExhibitorsResponse.getJSONArray("Espositori");

            for(int i = 0; i < getExhibitorResponseArray.length(); i++){
                JSONObject current = getExhibitorResponseArray.getJSONObject(i);

                ExhibitorsModel exhibitorsModel = new ExhibitorsModel();
                exhibitorsModel.setIdExhibitor(current.getInt("idespositore"));
                exhibitorsModel.setIdCategory(current.getInt("idcategoria"));
                exhibitorsModel.setIdPadiglione(current.getInt("idpadiglione"));
                exhibitorsModel.setName(current.getString("nome"));
                exhibitorsModel.setMassima(current.getString("massima"));
                exhibitorsModel.setDescription(current.getString("descrizione"));
                exhibitorsModel.setStandNumber(current.getString("stand_numero"));
                exhibitorsModel.setStandCoordinates(current.getString("stand_coordinate"));
                exhibitorsModel.setWebSite(current.getString("sito"));
                exhibitorsModel.setPhoneNumber(current.getString("contatto_telefonico"));
                exhibitorsModel.setArrayEmails(getEmails(getExhibitorsResponse));
                exhibitorsModel.setDescriptionNoHtml(current.getString("descrizione_nohtml"));
                exhibitorsModel.setLogoPath(current.getString("logo_path"));

                exhibitorsModelList.add(exhibitorsModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exhibitorsModelList;
    }

    /**
     * This method gets a list of email from the JsonArray derived from server response.
     * */
    @Nullable
    private List<String> getEmails(JSONObject getExhibitorsResponse){

        List<String> emailList = new ArrayList<>();

        try {
            JSONObject emailsObject = getExhibitorsResponse.optJSONObject("contatti_email");
            if (emailsObject == null)
                return null;
            String firstEmail = emailsObject.getString("email1");
            String secondEmail = emailsObject.getString("email2");
            String thirdEmail = emailsObject.getString("email3");

            emailList.add(firstEmail);
            emailList.add(secondEmail);
            emailList.add(thirdEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return emailList;

    }
}
