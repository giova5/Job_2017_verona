package com.emis.job2017.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.UserProfileModel;
import com.emis.job2017.utils.RealmUtils;
import com.google.android.gms.common.UserRecoverableException;

/**
 * Created by jo5 on 18/11/17.
 */

public class UserProfilePage extends AppCompatActivity implements View.OnClickListener{

    TextView userProfileTopText;
    Button userFavouritesButton;
    Button userTicketButton;
    Button userAttestationButton;

    public UserProfilePage(){
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
                //
                break;
            case R.id.user_ticket_button:
                //genera qrCode da link
                Intent ticketIntent = new Intent(UserProfilePage.this, UserTicketPage.class);
                startActivity(ticketIntent);
                break;
            case R.id.user_attestation_button:
                //check e fai vedere webview
                break;
        }

    }
}
