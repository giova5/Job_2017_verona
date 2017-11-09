package com.emis.job2017;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jo5 on 07/11/17.
 */

public class JobApplication extends Application {

    private static JobApplication appInstance;
    private static Realm realmInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        initRealm(this);

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
