package com.emis.job2017.loaders;

import android.content.Context;

import com.emis.job2017.BaseAsyncLoader;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.models.ExhibitorsModel;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo5 on 29/11/17.
 */

public class FavoritesLoader extends BaseAsyncLoader<List<ExhibitorsModel>> {

    public FavoritesLoader(Context c) {
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
        ServerOperations getExhibitors = new ServerOperations(Utils.EventType.PREFERITI_ELENCO);
        JSONObject getFavoritesResponse = getExhibitors.getFavoritesList();

        List<String> dealersID = parseGetFavorites(getFavoritesResponse);

        return RealmUtils.getFavoritesFromIDs(dealersID);
    }

    private List<String> parseGetFavorites(JSONObject getFavoritesResponse){

        List<String> dealersID = new ArrayList<>();

        try {
            JSONArray getFavoritesJsonArray = getFavoritesResponse.getJSONArray("EspositoriPreferiti");
            for(int i = 0; i < getFavoritesJsonArray.length(); i++) {
                JSONObject current = getFavoritesJsonArray.getJSONObject(i);
                dealersID.add(current.getString("idespositore"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dealersID;
    }
}
