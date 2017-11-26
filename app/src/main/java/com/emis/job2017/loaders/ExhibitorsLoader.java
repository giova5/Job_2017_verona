package com.emis.job2017.loaders;

import android.content.Context;
import android.support.annotation.Nullable;

import com.emis.job2017.BaseAsyncLoader;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;
import com.emis.job2017.models.ExhibitorsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.emis.job2017.ServerManagerService.OPERATION_SUCCESS_200_OK;
import static com.emis.job2017.ServerManagerService.RESPONSE_CODE;

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

        try {
            if(getExhibitorsResponse != null && getExhibitorsResponse.getString(RESPONSE_CODE).equals(OPERATION_SUCCESS_200_OK)) {
                exhibitorsList = parseGetExhibitorsResponse(getExhibitorsResponse);
                RealmUtils.removeAllDealerObj();
                RealmUtils.saveExhibitorsList(exhibitorsList);
                return RealmUtils.getSortedDealers();
            }else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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
                JSONObject temp = current.optJSONObject("contatti_email");
                exhibitorsModel.setEmail1((temp == null) ? "---" : temp.getString("email1"));
                exhibitorsModel.setEmail2((temp == null) ? "---" : temp.getString("email2"));
                exhibitorsModel.setEmail3((temp == null) ? "---" : temp.getString("email3"));
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
