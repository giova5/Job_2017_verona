package com.emis.job2017.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.ServerOperations;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText textViewEmail;
    TextView textViewPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewEmail = (TextInputEditText) findViewById(R.id.login_email);
        textViewPassword = (TextInputEditText) findViewById(R.id.login_password);
        buttonLogin = (Button) findViewById(R.id.login_button);

        buttonLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_button:
                //API login
                ServerOperations.sendUserAuthenticate(this, textViewEmail.getText().toString(), textViewPassword.getText().toString());
                break;
        }
    }
}
