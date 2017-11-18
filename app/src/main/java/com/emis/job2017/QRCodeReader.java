package com.emis.job2017;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.emis.job2017.utils.RealmUtils;
import com.emis.job2017.view.ExhibitorDetailPage;
import com.emis.job2017.view.GeneralWebView;
import com.google.zxing.Result;

import java.net.MalformedURLException;
import java.net.URL;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRCodeReader extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private LinearLayout qrCameraLayout;
    private static final String HOST_JOB = "job2017.webiac.it";

    public QRCodeReader() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment QRCodeReader.
     */
    // TODO: Rename and change types and number of parameters
    public static QRCodeReader newInstance(String param1, String param2) {
        QRCodeReader fragment = new QRCodeReader();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_qrcode_reader, container, false);
        qrCameraLayout = (LinearLayout) fragmentView.findViewById(R.id.qr_linear_layout);
        mScannerView = new ZXingScannerView(getActivity());
        mScannerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        qrCameraLayout.addView(mScannerView);

        return fragmentView;
    }

    @Override
    public void onPause() {
        Log.d("QRCodeReader", "onPause()");
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        Log.d("QRCodeReader", "onResume()");
        super.onResume();
        if(mScannerView != null){
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.d("QRCodeReader", "setUserVisibleHint() --> " + String.valueOf(isVisibleToUser));

        if(mScannerView != null) {
            if (isVisibleToUser) {
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                mScannerView.stopCamera();
            }
        }
    }

    @Override
    public void handleResult(Result result) {

        Log.d("QrCodeReader ", result.getText());
        //TOdo: check onBack

        Uri uri = Uri.parse(result.getText());
        String idEsp = uri.getQueryParameter("idesp");

        if(checkJobLink(uri)) {
            //Link contains job2017
            if (RealmUtils.getExhibitor(Integer.valueOf(idEsp)) == null) {
                //webview
                mScannerView.stopCamera();
                startGeneralWebviewFragment(result.getText());
            } else {
                //startFragment(Integer.valueOf(idEsp));
                mScannerView.stopCamera();
                startFragment(60);
            }
        }else{
            //Link does not contain job2017
            //webView
            startGeneralWebviewFragment(result.getText());
        }


    }

    private boolean checkJobLink(Uri link){
        return (link.getHost().equals(HOST_JOB));
    }

    private void startFragment(int exhibID){
        ExhibitorDetailPage fragment2 = ExhibitorDetailPage.newInstance(exhibID, true);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void startGeneralWebviewFragment(String qrCodeLink){
        GeneralWebView fragment2 = GeneralWebView.newInstance(qrCodeLink);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
