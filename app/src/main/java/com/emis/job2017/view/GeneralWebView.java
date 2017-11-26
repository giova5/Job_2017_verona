package com.emis.job2017.view;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.emis.job2017.R;

/**
 * Created by jo5 on 17/11/17.
 */

public class GeneralWebView extends Fragment {

    private String generalUrl;
    private static final String QR_CODE_LINK = "QR_CODE_LINK";
    private WebView webView;
    private ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generalUrl = getArguments().getString(QR_CODE_LINK);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_webview, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.general_webview_pb) ;

        webView = (WebView) view.findViewById(R.id.general_webview);

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClientImpl());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(generalUrl);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

    public static GeneralWebView newInstance(String qrCodeReader){
        GeneralWebView generalWebView = new GeneralWebView();
        Bundle args = new Bundle();
        args.putString(QR_CODE_LINK, qrCodeReader);
        generalWebView.setArguments(args);
        return generalWebView;
    }
}
