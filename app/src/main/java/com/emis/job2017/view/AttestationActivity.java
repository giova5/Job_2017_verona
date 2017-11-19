package com.emis.job2017.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.emis.job2017.R;
import com.emis.job2017.services.GPSTracker;
import com.emis.job2017.utils.Utils;

import static com.emis.job2017.services.GPSTracker.TEST_LATITUDE;
import static com.emis.job2017.services.GPSTracker.TEST_LONGITUDE;
//import static com.emis.job2017.services.GPSTracker.VERONA_LATIDUDE;
//import static com.emis.job2017.services.GPSTracker.VERONA_LONGITUDE;

/**
 * Created by jo5 on 18/11/17.
 */

public class AttestationActivity extends AppCompatActivity {

    private GPSTracker gps;
    private TextView latitudeText;
    private TextView longitudeText;

    public final static int MY_PERMISSIONS_REQUEST_GPS = 2;
    private static final double ACCEPTED_METERS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attestation);

        latitudeText = (TextView) findViewById(R.id.latitude);
        longitudeText = (TextView) findViewById(R.id.longitude);

        if(!checkPermissions()) {
            startGpsLogic();
        }

    }

    private void startGpsLogic(){
        gps = new GPSTracker(this);

        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            latitudeText.setText(String.valueOf(latitude));
            longitudeText.setText(String.valueOf(longitude));

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            double distanceInMeters = Utils.distance(latitude, longitude, TEST_LATITUDE, TEST_LONGITUDE);

            if(distanceInMeters <= 200){
                //show webview attestato
            }else{
                //Popup impossibile effettuare il check - in
            }

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
