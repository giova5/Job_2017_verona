package com.emis.job2017;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.emis.job2017.adapters.PagerAdapter;
import com.emis.job2017.view.ExhibitorDetailPage;
import com.emis.job2017.view.UserProfilePage;
import com.emis.job2017.view.UserTicketPage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private ImageButton ticketBtn;
    private ImageButton profileBtn;

    //TODO: change images.
    private int[] imageResId = {
            R.drawable.news,
            R.drawable.espositori,
            R.drawable.qrcode,
            R.drawable.calendar,
            R.drawable.mappa};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        ticketBtn = (ImageButton) findViewById(R.id.user_ticket_btn);
        profileBtn = (ImageButton) findViewById(R.id.profile_page_btn);

        ticketBtn.setOnClickListener(this);
        profileBtn.setOnClickListener(this);

        // Get the ViewPager and set it's PagerAdapter so that it can display items.
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), MainActivity.this));

        // Give the TabLayout the ViewPager.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    //TODO: disable camera (popup)
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void checkPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.profile_page_btn:
                Intent profileIntent = new Intent(MainActivity.this, UserProfilePage.class);
                startActivity(profileIntent);
                break;
            case R.id.user_ticket_btn:
                Intent ticketIntent = new Intent(MainActivity.this, UserTicketPage.class);
                startActivity(ticketIntent);
                break;
        }

    }
}
