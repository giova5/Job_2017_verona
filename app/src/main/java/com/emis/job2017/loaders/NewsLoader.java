package com.emis.job2017.loaders;

import android.content.Context;

import com.emis.job2017.BaseAsyncLoader;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;
import com.emis.job2017.models.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

/**
 * Created by jo5 on 02/11/17.
 */

public class NewsLoader extends BaseAsyncLoader<List<NewsModel>> {

    private static final String NOTIFICHE = "Notifiche";

    public NewsLoader(Context c) {
        super(c);
    }

    @Override
    protected void releaseResources(List<NewsModel> dataTobeReleased) {

    }

    @Override
    protected void registerDataObserver() {

    }

    @Override
    protected void unregisterDataObserver() {

    }

    @Override
    public List<NewsModel> loadInBackground() {

        List<NewsModel> newsList;

        ServerOperations getNews = new ServerOperations(Utils.EventType.NEWS);
        JSONObject getNewsResponse = getNews.getNews();

        newsList = parseGetNewsResponse(getNewsResponse);

        RealmUtils.removeAllNewsObj();

        RealmUtils.saveNewsList(newsList);

        //TODO:Order

        return newsList;
    }

    private List<NewsModel> parseGetNewsResponse(JSONObject getNewsResponse) {

        List<NewsModel> newsModelList = new ArrayList<>();

        try {
            JSONArray getNewsResponseArray = getNewsResponse.getJSONArray("News");

            for(int i = 0; i < getNewsResponseArray.length(); i++){
                JSONObject current = getNewsResponseArray.getJSONObject(i);

                NewsModel newsModel = new NewsModel();
                newsModel.setIdArticle(current.getInt("idarticolo"));
                newsModel.setNotification(checkIfIsNotification(current));
                newsModel.setTitle(current.getString("titolo"));
                newsModel.setContent(current.getString("testo"));
                newsModel.setContentNoHtml(current.getString("testo_nohtml"));
                newsModel.setPreview(current.getString("anteprima"));
                newsModel.setAuthor(current.getString("autore"));
                //TODO: check date 1970
                Date date = new Date(Long.valueOf(current.getString("data")));
                newsModel.setDate(date);
                newsModel.setLink(current.getString("link"));

                newsModelList.add(newsModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsModelList;
    }

    private boolean checkIfIsNotification(JSONObject currentNews){

        boolean isNotification = false;
        try {
            JSONArray categories = currentNews.getJSONArray("categorie_stringhe");
            for(int i = 0; i < categories.length(); i++){
                String currentCategory = categories.getString(i);
                if(currentCategory.equals(NOTIFICHE))
                    isNotification = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isNotification;
    }

}
