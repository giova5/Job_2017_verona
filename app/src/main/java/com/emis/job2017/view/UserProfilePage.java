package com.emis.job2017.view;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.emis.job2017.R;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.models.UserProfileModel;
import com.emis.job2017.services.GPSTracker;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;
import com.google.android.gms.common.UserRecoverableException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

import io.realm.internal.Util;

import static com.emis.job2017.ServerManagerService.GET_ATTESTATION_FAILURE;
import static com.emis.job2017.ServerManagerService.GET_ATTESTATION_SUCCESS;
import static com.emis.job2017.services.GPSTracker.VERONA_LATITUDE;
import static com.emis.job2017.services.GPSTracker.VERONA_LONGITUDE;

/**
 * Created by jo5 on 18/11/17.
 */

public class UserProfilePage extends AppCompatActivity implements View.OnClickListener{

    private TextView userProfileTopText;
    private Button userFavouritesButton;
    private Button userTicketButton;
//    private Button userAttestationButton;
    private ProgressBar userProfileProgressBar;
    Context context;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA};


    private GPSTracker gps;
    private ResponseReceiver receiver;
    String attestationLink;


    public final static int MY_PERMISSIONS_REQUEST_GPS = 2;
    private static final double ACCEPTED_METERS = 200;

    public UserProfilePage(){
    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String LOCAL_ACTION = "GET_ATTESTATION";

        @Override
        public void onReceive(Context context, Intent intent) {

            String callback = intent.getStringExtra("CALLBACK");
            String body = intent.getStringExtra("RESPONSE_BODY");

            Log.d("AttestationActivity", "AttestationActivity response receiver called");

            switch (callback){
                case GET_ATTESTATION_SUCCESS:
                    try {
                        attestationLink = parseGetAttestationResponse(new JSONObject(body));
                        userProfileProgressBar.setVisibility(View.GONE);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attestationLink));
                        startActivity(browserIntent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_ATTESTATION_FAILURE:
                    showErrorDialogSuccess("Attestato generato con successo, accedi al sito http://job2018.webiac.it dal computer per stamparlo.");
                    userProfileProgressBar.setVisibility(View.GONE);
                    break;
            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_user_profile);

        userProfileTopText = (TextView) findViewById(R.id.user_profile_top_text);
        userFavouritesButton = (Button) findViewById(R.id.user_favourites_button);
        userTicketButton = (Button) findViewById(R.id.user_ticket_button);
//        userAttestationButton = (Button) findViewById(R.id.user_attestation_button);
        userProfileProgressBar = (ProgressBar) findViewById(R.id.user_profile_pb);

        UserProfileModel userProfileModel = RealmUtils.getUser();

        String nameAndSurname = userProfileModel.getUserName() + " " + userProfileModel.getUserSurname();

        userProfileTopText.setText(nameAndSurname);

        userFavouritesButton.setOnClickListener(this);
        userTicketButton.setOnClickListener(this);
//        userAttestationButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.user_favourites_button:
                Intent favoritesIntent = new Intent(UserProfilePage.this, FavoritesPage.class);
                startActivity(favoritesIntent);
                break;
            case R.id.user_ticket_button:
                //genera qrCode da link
                Intent ticketIntent = new Intent(UserProfilePage.this, UserTicketPage.class);
                startActivity(ticketIntent);
                break;
//            case R.id.user_attestation_button:
//                if(Utils.isNetworkAvailable(this)) {
//                    if (!checkPermissions()) {
//                        startGpsLogic();
//                    }
//                }else{
//                    showErrorDialog(getResources().getString(R.string.title_no_internet_popup));
//                }
//                break;
        }

    }

    @Nullable
    private String parseGetAttestationResponse(JSONObject getAttestationResponse){

        JSONObject attestationObj = null;
        String attestationLink = null;

        try {
            attestationObj = getAttestationResponse.getJSONObject("Attestato");
            attestationLink = attestationObj.getString("attestato_link");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attestationLink;
    }

    private void startGpsLogic(){
        gps = new GPSTracker(this);

        Date currentTime = Calendar.getInstance().getTime();
        Calendar currentCalendar = Utils.toCalendar(currentTime);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;

        if(RealmUtils.getUser().getUrlToAttestation() == null) {
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                double distanceInMeters = Utils.distance(latitude, longitude, VERONA_LATITUDE, VERONA_LONGITUDE);

                if (distanceInMeters <= 200 && checkCalendarForAttestation(currentDay, currentMonth)) {
                    //Show webview attestato
                    userProfileProgressBar.setVisibility(View.VISIBLE);
                    userProfileProgressBar.bringToFront();
                    ServerOperations.sendGetAttestation(this);
                } else {
                    //Popup impossibile effettuare il check - in
                    showErrorDialog("Per ottenere l'attestato è necessario essere all'interno della fiera.");
                }

            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                //TODO: testare
                showErrorDialog("Abilitare il GPS per ottenere l'attestato.");
            }
        }else{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(RealmUtils.getUser().getUrlToAttestation()));
            startActivity(browserIntent);
        }
    }

    private boolean checkCalendarForAttestation(int currentDay, int currentMonth){

        boolean checkFirstDay = false;
        boolean checkSecondDay = false;
        boolean checkThirdDay = false;

        if(currentDay == 30 && currentMonth == 11)
            checkFirstDay = true;

        if(currentDay == 1 && currentMonth == 12)
            checkSecondDay = true;

        if(currentDay == 2 && currentMonth == 12)
            checkThirdDay = true;

        return (checkFirstDay || checkSecondDay || checkThirdDay);

    }

    private void showErrorDialog(String message){
        new AwesomeErrorDialog(this)
                .setTitle(R.string.title_popup_app)
                .setMessage(message)
                .setColoredCircle(R.color.colorYellow)
                .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                .setCancelable(true).setButtonText(getString(R.string.dialog_ok_button))
                .setButtonBackgroundColor(R.color.colorYellow)
                .setButtonText(getString(R.string.dialog_ok_button))
                .setErrorButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        // click
                    }
                })
                .show();
    }

    private void showErrorDialogSuccess(String message){
        new AwesomeErrorDialog(this)
                .setTitle(R.string.title_popup_app)
                .setMessage(message)
                .setColoredCircle(R.color.colorYellow)
                .setDialogIconAndColor(R.drawable.ic_success, R.color.white)
                .setCancelable(true).setButtonText(getString(R.string.dialog_ok_button))
                .setButtonBackgroundColor(R.color.colorYellow)
                .setButtonText(getString(R.string.dialog_ok_button))
                .setErrorButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        // click
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GPS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startGpsLogic();
                } else {
                    showErrorDialog("Per ottenere l'attestato è necessario accettare il permesso di accedere alla propria posizione. Per abilitarlo accedere alle impostazioni dell'app Job&Orienta.");
                }
            }
        }
    }


    private boolean checkPermissions() {

        boolean permissionChecked = false;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissionChecked = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_GPS);
                permissionChecked = true;
            }
        }

        return permissionChecked;
    }
}
