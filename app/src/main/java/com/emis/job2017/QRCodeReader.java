package com.emis.job2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRCodeReader extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private LinearLayout qrCameraLayout;

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
    }
}
