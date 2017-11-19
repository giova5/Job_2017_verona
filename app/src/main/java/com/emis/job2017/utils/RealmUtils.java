package com.emis.job2017.utils;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;

import com.emis.job2017.JobApplication;
import com.emis.job2017.models.CalendarEventModel;
import com.emis.job2017.models.ExhibitorsModel;
import com.emis.job2017.models.NewsModel;
import com.emis.job2017.models.UserProfileModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Iterator;
import java.util.List;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmResults;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

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

    @Nullable
    public static ExhibitorsModel getExhibitor(int idExhib){

        ExhibitorsModel cloned;
        Realm realm = Realm.getDefaultInstance();
        ExhibitorsModel exhibModel = realm.where(ExhibitorsModel.class).equalTo("idExhibitor", idExhib).findFirst();
        if(exhibModel != null) {
            cloned = ExhibitorsModel.cloneObject(exhibModel);
            realm.close();
            return cloned;
        }else {
            realm.close();
            return null;
        }
    }

    public static UserProfileModel getUser(){
        Realm realm = Realm.getDefaultInstance();
        UserProfileModel userProfileModel= realm.where(UserProfileModel.class).findFirst();
        UserProfileModel cloned = UserProfileModel.cloneObject(userProfileModel);
        realm.close();
        return cloned;
    }

    /**
     * ********************* END Methods for getting contents from Realm  *********************
     */

    public static void firstUserCreation(String email, String refreshToken){

        Realm realm = Realm.getDefaultInstance();

        UserProfileModel userProfileModel = new UserProfileModel();
        userProfileModel.setUserEmail(email);
        userProfileModel.setRefreshToken(refreshToken);

        realm.beginTransaction();
        realm.commitTransaction();
        realm.close();
    }

    public static void saveAccessToken(String accessToken){
        Realm realm = Realm.getDefaultInstance();

        UserProfileModel userProfileModel = new UserProfileModel();
        userProfileModel.setAccessToken(accessToken);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(userProfileModel);
        realm.commitTransaction();
        realm.close();
    }

    public static void saveUserProfileInfo(UserProfileModel userFromJson){
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(userFromJson);
        realm.commitTransaction();
        realm.close();
    }



    public static Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 100, 100, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 100, 0, 0, w, h);
        return bitmap;
    }

}


