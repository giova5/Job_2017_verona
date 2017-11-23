package com.emis.job2017.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.UserProfileModel;
import com.emis.job2017.utils.RealmUtils;
import com.google.zxing.WriterException;

/**
 * Created by jo5 on 18/11/17.
 */

public class UserTicketPage extends AppCompatActivity {

    private ImageView ticketImageView;
    private TextView userInfo;
    private String urlToTicket;


    public UserTicketPage(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_user_ticket);

        ticketImageView = (ImageView) findViewById(R.id.ticket_imageview);
        userInfo = (TextView) findViewById(R.id.user_info);

        UserProfileModel userProfileModel = RealmUtils.getUser();
        urlToTicket = userProfileModel.getUrlTicket();
        String userInfoS = userProfileModel.getUserName() + " " + userProfileModel.getUserSurname();
        userInfo.setText(userInfoS);

        Bitmap bitmap = null;
        try {
            bitmap = RealmUtils.encodeAsBitmap(urlToTicket);
            ticketImageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
