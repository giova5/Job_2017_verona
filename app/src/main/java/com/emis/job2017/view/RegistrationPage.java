package com.emis.job2017.view;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.emis.job2017.R;
import com.emis.job2017.utils.Utils;

/**
 * Created by jo5 on 17/11/17.
 */

public class RegistrationPage extends Fragment {

    private WebView webView;
    private ProgressBar progressBar;
    private static final String registrationUrl = "http://job2017.webiac.it/index.php?s=83";

    public RegistrationPage(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration_page, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.registration_page_pb) ;

        webView = (WebView) view.findViewById(R.id.registration_page_webview);

        if(Utils.isNetworkAvailable(getActivity()))
            setupWebview();
        else
            showErrorDialog();

        return view;
    }

    private void showErrorDialog(){

        new AwesomeErrorDialog(getActivity())
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
                        getActivity().onBackPressed();
                    }
                })
                .show();
    }


    private void setupWebview(){
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                if(progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(registrationUrl);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

//    private class WebViewClientImpl extends android.webkit.WebViewClient {
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            progressBar.setVisibility(View.VISIBLE);
//            super.onPageStarted(view, url, favicon);
//        }
//
//        public void onPageFinished(WebView view, String url) {
//            progressBar.setVisibility(View.GONE);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // When user clicks a hyperlink, load in the existing WebView
//            view.loadUrl(url);
//            return true;
//        }
//
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
//        }
//
//    }
}
