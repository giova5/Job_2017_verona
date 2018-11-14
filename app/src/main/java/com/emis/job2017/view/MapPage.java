package com.emis.job2017.view;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.emis.job2017.R;
import com.emis.job2017.utils.Utils;

import io.realm.internal.Util;

/**
 * Created by jo5 on 17/11/17.
 */

public class MapPage extends Activity {

    private WebView webView;
    private ProgressBar progressBar;
    private static final String mapUrl = "http://job2018.webiac.it/access/acvisespmappa/mappa_mobile.php?app=1";

    public MapPage(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_map_page);

        progressBar = (ProgressBar) findViewById(R.id.map_page_pb) ;

        webView = (WebView) findViewById(R.id.map_page_webview);


        if(Utils.isNetworkAvailable(this)) {
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setWebViewClient(new WebViewClientImpl());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.loadUrl(mapUrl);
        }else{
            showErrorDialog();
        }
    }

    private void showErrorDialog(){

        new AwesomeErrorDialog(this)
                .setTitle(R.string.title_popup_app)
                .setMessage(R.string.title_no_internet_popup)
                .setColoredCircle(R.color.colorYellow)
                .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                .setCancelable(false).setButtonText(getString(R.string.dialog_ok_button))
                .setButtonBackgroundColor(R.color.colorYellow)
                .setButtonText(getString(R.string.dialog_ok_button))
                .setErrorButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        // click
                        onBackPressed();
                    }
                })
                .show();
    }

    private class WebViewClientImpl extends android.webkit.WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // When user clicks a hyperlink, load in the existing WebView
            view.loadUrl(url);
            return true;
        }
    }
}
