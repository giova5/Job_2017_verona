package com.emis.job2017.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.emis.job2017.MainActivity;
import com.emis.job2017.R;
import com.emis.job2017.ServerManagerService;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.emis.job2017.ServerManagerService.ACCESS_TOKEN_FAILURE;
import static com.emis.job2017.ServerManagerService.AUTHENTICATION_FAILURE;
import static com.emis.job2017.ServerManagerService.AUTHENTICATION_SUCCESS;
import static com.emis.job2017.ServerManagerService.LOGIN_FAILURE;
import static com.emis.job2017.ServerManagerService.OPERATION_FAILURE_401;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText textViewEmail;
    private AppCompatEditText textViewPassword;
    private Button buttonLogin;
    private ProgressBar loginProgressBar;

    private ResponseReceiver receiver;


    public class ResponseReceiver extends BroadcastReceiver {

        public static final String LOCAL_ACTION = "AUTHENTICATION";

        @Override
        public void onReceive(Context context, Intent intent) {

            JSONObject jsonResponse;
            String callback = intent.getStringExtra("CALLBACK");
            String responseBody = intent.getStringExtra("RESPONSE_BODY");

            try {
                jsonResponse = new JSONObject(responseBody);


                Log.d("LoginActivity", "LoginActivity response receiver called");

                switch (callback){
                    case AUTHENTICATION_SUCCESS:
                        loginProgressBar.setVisibility(View.GONE);
                        Intent mainActivityIntent = new Intent(context, MainActivity.class);
//                        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainActivityIntent);
                        finish();
                        break;
                    case AUTHENTICATION_FAILURE:
                        //Error during last getUserProfile
                        loginProgressBar.setVisibility(View.GONE);
                        showErrorDialog("Errore di connessione.");
                        break;
                    case ACCESS_TOKEN_FAILURE:
                        //Error during getAccessToken
                        loginProgressBar.setVisibility(View.GONE);
                        showErrorDialog("Errore di connessione.");
                        break;
                    case LOGIN_FAILURE:
                        loginProgressBar.setVisibility(View.GONE);
                        if(jsonResponse.getString(ServerManagerService.RESPONSE_CODE).equals(ServerManagerService.OPERATION_FAILURE_401))
                            showErrorDialog("Email o password errati.");
                        else
                            showErrorDialog("Errore di connessione.");
                        break;
            }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewEmail = (TextInputEditText) findViewById(R.id.login_email);
        textViewPassword = (AppCompatEditText) findViewById(R.id.login_password);
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
                if(Utils.isValidEmail(textViewEmail.getText().toString())) {
                    loginProgressBar.setVisibility(View.VISIBLE);
                    ServerOperations.sendUserAuthenticate(this, textViewEmail.getText().toString(), textViewPassword.getText().toString());
                }else{
                    textViewEmail.setError("Errore compilazione campo.");
                }
                break;
        }
    }
}
