package com.emis.job2017.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.emis.job2017.MainActivity;
import com.emis.job2017.R;
import com.emis.job2017.ServerOperations;

import static com.emis.job2017.ServerManagerService.ACCESS_TOKEN_FAILURE;
import static com.emis.job2017.ServerManagerService.AUTHENTICATION_FAILURE;
import static com.emis.job2017.ServerManagerService.AUTHENTICATION_SUCCESS;
import static com.emis.job2017.ServerManagerService.LOGIN_FAILURE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText textViewEmail;
    private TextView textViewPassword;
    private Button buttonLogin;
    private ProgressBar loginProgressBar;

    private ResponseReceiver receiver;


    public class ResponseReceiver extends BroadcastReceiver {

        public static final String LOCAL_ACTION = "AUTHENTICATION";

        @Override
        public void onReceive(Context context, Intent intent) {

            String callback = intent.getStringExtra("CALLBACK");

            Log.d("LoginActivity", "LoginActivity response receiver called");

            switch (callback){
                case AUTHENTICATION_SUCCESS:
                    loginProgressBar.setVisibility(View.GONE);
                    Intent mainActivityIntent = new Intent(context, MainActivity.class);
                    startActivity(mainActivityIntent);
                    break;
                case AUTHENTICATION_FAILURE:
                    //Error during last getUserProfile
                    break;
                case ACCESS_TOKEN_FAILURE:
                    //Error during getAccessToken
                    break;
                case LOGIN_FAILURE:
                    //Error during login with username and psw
                    break;
            }

        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewEmail = (TextInputEditText) findViewById(R.id.login_email);
        textViewPassword = (TextInputEditText) findViewById(R.id.login_password);
        buttonLogin = (Button) findViewById(R.id.login_button);
        loginProgressBar = (ProgressBar) findViewById(R.id.login_progress_bar);

        buttonLogin.setOnClickListener(this);

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
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_button:
                //API login
                loginProgressBar.setVisibility(View.VISIBLE);
                ServerOperations.sendUserAuthenticate(this, textViewEmail.getText().toString(), textViewPassword.getText().toString());
                break;
        }
    }
}
