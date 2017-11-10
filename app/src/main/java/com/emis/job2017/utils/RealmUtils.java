package com.emis.job2017.utils;

import android.util.Log;

import com.emis.job2017.JobApplication;
import com.emis.job2017.models.CalendarEventModel;
import com.emis.job2017.models.ExhibitorsModel;
import com.emis.job2017.models.NewsModel;

import java.util.Iterator;
import java.util.List;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmResults;

/**
 * Created by jo5 on 07/11/17.
 */

public class RealmUtils {

    /**
     * ********************* Methods for saving contents to Realm *********************
     */

    public static void saveCalendarList(List<CalendarEventModel> calendarList){

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        Iterator<CalendarEventModel> iterator = calendarList.iterator();
        while(iterator.hasNext()) {
            CalendarEventModel currentCalendarEventModel = iterator.next();

            CalendarEventModel calendarEventModel = realm.createObject(CalendarEventModel.class);
            calendarEventModel.setIdProgram(currentCalendarEventModel.getIdProgram());
            calendarEventModel.setStartTime(currentCalendarEventModel.getStartTime());
            calendarEventModel.setEndTime(currentCalendarEventModel.getEndTime());
            calendarEventModel.setLocation(currentCalendarEventModel.getLocation());
            calendarEventModel.setTypeOfProgramString(currentCalendarEventModel.getTypeOfProgramString());
            calendarEventModel.setTitle(currentCalendarEventModel.getTitle());
            calendarEventModel.setDescription(currentCalendarEventModel.getDescription());
        }
        realm.commitTransaction();
        realm.close();
    }

    public static void saveNewsList(List<NewsModel> newsList){

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        Iterator<NewsModel> iterator = newsList.iterator();
        while(iterator.hasNext()) {

            NewsModel currentCalendarEventModel = iterator.next();
            NewsModel newsModel= realm.createObject(NewsModel.class);

            newsModel.setIdArticle(currentCalendarEventModel.getIdArticle());
            newsModel.setTitle(currentCalendarEventModel.getTitle());
            newsModel.setContent(currentCalendarEventModel.getContent());
            newsModel.setContentNoHtml(currentCalendarEventModel.getContentNoHtml());
            newsModel.setPreview(currentCalendarEventModel.getPreview());
            newsModel.setAuthor(currentCalendarEventModel.getAuthor());
            newsModel.setDate(currentCalendarEventModel.getDate());
            newsModel.setLink(currentCalendarEventModel.getLink());
        }
        realm.commitTransaction();
        realm.close();
    }

    public static void saveExhibitorsList(List<ExhibitorsModel> newsExhibitors){
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        Iterator<ExhibitorsModel> iterator = newsExhibitors.iterator();
        while(iterator.hasNext()) {

            ExhibitorsModel currentExhibitorModel = iterator.next();

            ExhibitorsModel exhibitorsModel = realm.createObject(ExhibitorsModel.class);

            exhibitorsModel.setIdExhibitor(currentExhibitorModel.getIdExhibitor());
            exhibitorsModel.setIdCategory(currentExhibitorModel.getIdCategory());
            exhibitorsModel.setIdPadiglione(currentExhibitorModel.getIdPadiglione());
            exhibitorsModel.setName(currentExhibitorModel.getName());
            exhibitorsModel.setMassima(currentExhibitorModel.getMassima());
            exhibitorsModel.setDescription(currentExhibitorModel.getDescription());
            exhibitorsModel.setStandNumber(currentExhibitorModel.getStandNumber());
            exhibitorsModel.setStandCoordinates((currentExhibitorModel.getStandCoordinates()));
            exhibitorsModel.setWebSite(currentExhibitorModel.getWebSite());
            exhibitorsModel.setYoutubeLink(currentExhibitorModel.getYoutubeLink());
            exhibitorsModel.setPhoneNumber(currentExhibitorModel.getPhoneNumber());
            exhibitorsModel.setLogoPath(currentExhibitorModel.getLogoPath());
            exhibitorsModel.setDescriptionNoHtml(currentExhibitorModel.getDescriptionNoHtml());
            exhibitorsModel.setEmail1(currentExhibitorModel.getEmail1());
            exhibitorsModel.setEmail2(currentExhibitorModel.getEmail2());
            exhibitorsModel.setEmail3(currentExhibitorModel.getEmail3());
        }
        realm.commitTransaction();
        realm.close();
    }

    /**
     * ********************* Methods for getting contents from Realm  *********************
     */

    public static CalendarEventModel getCalendarPrograms(int calendarID){
        Realm realm = Realm.getDefaultInstance();
        CalendarEventModel calendarEventModel = realm.where(CalendarEventModel.class).equalTo("idProgram", calendarID).findFirst();
        CalendarEventModel cloned = CalendarEventModel.cloneObject(calendarEventModel);
        realm.close();
        return cloned;
    }

    public static NewsModel getNews(int idArticle){
        Realm realm = Realm.getDefaultInstance();
        NewsModel newsModel = realm.where(NewsModel.class).equalTo("idArticle", idArticle).findFirst();
        NewsModel cloned = NewsModel.cloneObject(newsModel);
        realm.close();
        return cloned;
    }

    public static ExhibitorsModel getExhibitors(int idExhib){
        Realm realm = Realm.getDefaultInstance();
        ExhibitorsModel exhibModel = realm.where(ExhibitorsModel.class).equalTo("idExhibitor", idExhib).findFirst();
        ExhibitorsModel cloned = ExhibitorsModel.cloneObject(exhibModel);
        realm.close();

        return cloned;
    }

//    public static void showCalendarRealmList(){
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        RealmResults<CalendarEventModel> calendarEventModels = realm.where(CalendarEventModel.class).findAll();
//        for(CalendarEventModel current : calendarEventModels){
//            Log.d("CalendarList from Realm", String.valueOf(current.getIdProgram()));
//        }
//    }

}


