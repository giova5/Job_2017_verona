package com.emis.job2017;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jo5 on 23/10/17.
 */

public class ServerManagerService extends IntentService {

    private static final String TAG = "ServerManagerService";
    public static final String COMMAND = "COMMAND";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServerManagerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent == null) {
            Log.d(TAG, "onHandleIntent called with null intent, probably has been restarted...");
            // Without params -> do nothing
            return;
        }

        String command = intent.getStringExtra(COMMAND);

        switch (command){
            //All requests
        }

    }
}
