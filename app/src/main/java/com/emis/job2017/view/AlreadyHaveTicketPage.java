package com.emis.job2017.view;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.emis.job2017.R;
import com.emis.job2017.utils.Utils;

/**
 * Created by jo5 on 16/11/17.
 */

public class AlreadyHaveTicketPage extends Fragment {

    private WebView webView;
    private ProgressBar progressBar;
    private static final String alreadyHaveTicketUrl = "http://job2017.webiac.it/index.php?s=85";
    public static final String ALREADY_HAVE_TICKET_ID = "ALREADY_HAVE_TICKET_ID";

    public AlreadyHaveTicketPage(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_already_have_ticket, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.already_have_ticket_pb);

        webView = (WebView) view.findViewById(R.id.already_have_ticket_webview);

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
                .setCancelable(true).setButtonText(getString(R.string.dialog_ok_button))
                .setButtonBackgroundColor(R.color.colorYellow)
                .setButtonText(getString(R.string.dialog_ok_button))
                .setErrorButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        // click
                        getActivity().onBackPressed();                    }
                })
                .show();
    }

    private void setupWebview(){
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClientImpl());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(alreadyHaveTicketUrl);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
