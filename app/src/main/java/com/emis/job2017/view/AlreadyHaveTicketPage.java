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

import com.emis.job2017.R;

/**
 * Created by jo5 on 16/11/17.
 */

public class AlreadyHaveTicketPage extends Fragment {

    private WebView webView;
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

        webView = (WebView) view.findViewById(R.id.already_have_ticket_webview);

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClientImpl());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(alreadyHaveTicketUrl);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class WebViewClientImpl extends android.webkit.WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            requestShowLoadingView();
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
//            requestHideLoadingView();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // When user clicks a hyperlink, load in the existing WebView
            view.loadUrl(url);
            return true;

        }

    }

    public static AlreadyHaveTicketPage newInstance(int calendarID){
        AlreadyHaveTicketPage alreadyHaveTicketPage = new AlreadyHaveTicketPage();
        Bundle args = new Bundle();
        args.putInt(ALREADY_HAVE_TICKET_ID, calendarID);
        alreadyHaveTicketPage.setArguments(args);
        return alreadyHaveTicketPage;
    }
}
