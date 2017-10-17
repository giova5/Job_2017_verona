package com.emis.job2017;

import android.content.Context;
import android.net.Uri;
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
 * Activities that contain this fragment must implement the
 * {@link QRCodeReader.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QRCodeReader#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRCodeReader extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private LinearLayout qrCameraLayout;
    private OnFragmentInteractionListener mListener;

    public QRCodeReader() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void handleResult(Result result) {

        Log.d("QrCodeReader ", result.getText());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
