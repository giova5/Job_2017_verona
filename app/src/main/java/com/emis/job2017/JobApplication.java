package com.emis.job2017;

import android.app.Application;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessaging;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jo5 on 07/11/17.
 */

public class JobApplication extends Application {

    private static JobApplication appInstance;
    private static Realm realmInstance;
    private static String accessToken;
    private static String refreshToken;

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        initRealm(this);

        FirebaseMessaging.getInstance().subscribeToTopic("notifiche");

    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        JobApplication.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        JobApplication.refreshToken = refreshToken;
    }

    public static JobApplication getAppInstance() {
        return appInstance;
    }

    public static Realm getRealmInstance(){
        return realmInstance;
    }

    private static void initRealm(Context context){
        Realm.init(context);
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("job.realm")
//                .schemaVersion(42)
//                .build();

//        Realm.setDefaultConfiguration(config);
        // Use the config
//        realmInstance = Realm.getInstance(config);
    }

}
