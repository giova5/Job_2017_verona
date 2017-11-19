package com.emis.job2017.view;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.emis.job2017.R;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.models.UserProfileModel;
import com.emis.job2017.services.GPSTracker;
import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.utils.Utils;
import com.google.android.gms.common.UserRecoverableException;

import org.json.JSONException;
import org.json.JSONObject;

import static com.emis.job2017.ServerManagerService.GET_ATTESTATION_FAILURE;
import static com.emis.job2017.ServerManagerService.GET_ATTESTATION_SUCCESS;
import static com.emis.job2017.services.GPSTracker.TEST_LATITUDE;
import static com.emis.job2017.services.GPSTracker.TEST_LONGITUDE;

/**
 * Created by jo5 on 18/11/17.
 */

public class UserProfilePage extends AppCompatActivity implements View.OnClickListener{

    TextView userProfileTopText;
    Button userFavouritesButton;
    Button userTicketButton;
    Button userAttestationButton;
    Context context;

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

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attestationLink));
                        startActivity(browserIntent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_ATTESTATION_FAILURE:
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
        userAttestationButton = (Button) findViewById(R.id.user_attestation_button);

        UserProfileModel userProfileModel = RealmUtils.getUser();

        String nameAndSurname = userProfileModel.getUserName() + " " + userProfileModel.getUserSurname();

        userProfileTopText.setText(nameAndSurname);

        userFavouritesButton.setOnClickListener(this);
        userTicketButton.setOnClickListener(this);
        userAttestationButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.user_favourites_button:
                ServerOperations.sendGetFavoritesList(this);
                break;
            case R.id.user_ticket_button:
                //genera qrCode da link
                Intent ticketIntent = new Intent(UserProfilePage.this, UserTicketPage.class);
                startActivity(ticketIntent);
                break;
            case R.id.user_attestation_button:
                //check e fai vedere webview
                if(!checkPermissions()) {
                    startGpsLogic();
                }
                break;
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

        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            double distanceInMeters = Utils.distance(latitude, longitude, TEST_LATITUDE, TEST_LONGITUDE);

            if(distanceInMeters <= 200){
                //TODO: show webview attestato
                ServerOperations.sendGetAttestation(this);
            }else{
                //TODO: Popup impossibile effettuare il check - in
            }

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            //TODO: testare
            gps.showSettingsAlert();
        }
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
                    //TODO: disable camera (popup)
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
