package com.emis.job2017;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jo5 on 02/11/17.
 */

public class NewsLoader extends BaseAsyncLoader<List<NewsModel>> {

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

        return newsList;
    }

    private List<NewsModel> parseGetNewsResponse(JSONObject getNewsResponse) {

        List<NewsModel> newsModelList = new ArrayList<>();

        try {
            JSONArray getNewsResponseArray = getNewsResponse.getJSONArray("News");

            for(int i = 0; i < getNewsResponse.length(); i++){
                JSONObject current = getNewsResponseArray.getJSONObject(i);

                NewsModel newsModel = new NewsModel();
                newsModel.setIdArticle(current.getInt("idarticolo"));
                newsModel.setTitle(current.getString("titolo"));
                newsModel.setContent(current.getString("testo"));
                newsModel.setContentNoHtml(current.getString("testo_nohtml"));
                newsModel.setPreview(current.getString("anteprima"));
                newsModel.setAuthor(current.getString("autore"));
                //TODO: check date 1970
                Date date = new Date(Long.valueOf(current.getString("data")));
                newsModel.setDate(date);
                newsModel.setLink(new URL(current.getString("link")));

                newsModelList.add(newsModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return newsModelList;
    }

}
