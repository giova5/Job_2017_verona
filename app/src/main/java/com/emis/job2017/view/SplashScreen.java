package com.emis.job2017.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.emis.job2017.MainActivity;
import com.emis.job2017.ProfilingActivity;
import com.emis.job2017.R;
import com.emis.job2017.ServerManagerService;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;

import org.json.JSONObject;

import io.realm.internal.Util;

import static com.emis.job2017.ServerManagerService.GET_ACCESS_TOKEN_FAILURE;
import static com.emis.job2017.ServerManagerService.GET_ACCESS_TOKEN_SUCCESS;

/**
 * Created by jo5 on 24/11/17.
 */

public class SplashScreen extends Activity {

    private ResponseReceiver receiver;
    Context context;

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String LOCAL_ACTION = "GET_ACCESS_TOKEN";

        @Override
        public void onReceive(Context context, Intent intent) {

            String callback = intent.getStringExtra("CALLBACK");
            switch (callback) {
                case GET_ACCESS_TOKEN_SUCCESS:
                    startMainActivity();
                    break;
                case GET_ACCESS_TOKEN_FAILURE:
                    startProfilingActivity();
                    break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        context = this;
        //TODO: check why popup is not showed (Needs a timer!?)
        if (RealmUtils.getUser() == null) {
            startProfilingActivity();
        } else {
            ServerOperations.sendGetAccessToken(context);
        }
    }

    private void showErrorDialog(String message){
        new AwesomeErrorDialog(context)
                .setTitle(R.string.title_popup_app)
                .setMessage(message)
                .setColoredCircle(R.color.colorYellow)
                .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                .setCancelable(false).setButtonText(getString(R.string.dialog_ok_button))
                .setButtonBackgroundColor(R.color.colorYellow)
                .setButtonText(getString(R.string.dialog_ok_button))
                .setErrorButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter BroadcastFilter = new IntentFilter(ResponseReceiver.LOCAL_ACTION);
        receiver = new ResponseReceiver();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, BroadcastFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.unregisterReceiver(receiver);
    }

    private void startProfilingActivity(){
        Intent profilingIntent = new Intent(this, ProfilingActivity.class);
        startActivity(profilingIntent);
    }

    private void startMainActivity(){
        Intent mainActIntent = new Intent(this, MainActivity.class);
//        mainActIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        mainActIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActIntent);
//        finish();
    }

}
