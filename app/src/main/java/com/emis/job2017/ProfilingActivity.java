package com.emis.job2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emis.job2017.view.LoginActivity;

public class ProfilingActivity extends AppCompatActivity implements View.OnClickListener{

    Button loginBtn;
    Button registrationBtn;
    Button alreadyHaveTicketBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registrationBtn = (Button) findViewById(R.id.registerBtn);
        alreadyHaveTicketBtn = (Button) findViewById(R.id.alreadyHaveTicket);

        loginBtn.setOnClickListener(this);
        registrationBtn.setOnClickListener(this);
        alreadyHaveTicketBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                Intent loginIntent = new Intent(ProfilingActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.registerBtn:
//                Intent registrationIntent = new Intent(ProfilingActivity.this, RegistrationActivity.class);
//                startActivity(loginIntent);
                break;
            case R.id.alreadyHaveTicket:
//                Intent alreadyHaveTicketIntent = new Intent(ProfilingActivity.this, AlreadyHaveTicketActivity.class);
//                startActivity(alreadyHaveTicketIntent);
                break;
        }
    }
}
