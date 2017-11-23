package com.emis.job2017.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emis.job2017.MainActivity;
import com.emis.job2017.R;
import com.emis.job2017.services.GPSTracker;

import java.util.Locale;

/**
 * Created by jo5 on 23/11/17.
 */

public class VenuesPage extends Fragment implements View.OnClickListener{

    Button comeRaggiungerciBtn;
    Button mapsBtn;
    Button ticketBtn;

    public VenuesPage(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venues, container, false);

        comeRaggiungerciBtn = (Button) view.findViewById(R.id.come_raggiungerci_btn);
        mapsBtn = (Button) view.findViewById(R.id.mappa_padiglioni_btn);
        ticketBtn = (Button) view.findViewById(R.id.biglietto_btn);

        comeRaggiungerciBtn.setOnClickListener(this);
        mapsBtn.setOnClickListener(this);
        ticketBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.come_raggiungerci_btn:
                startMaps(getContext());
                break;
            case R.id.mappa_padiglioni_btn:
                startMapPage();
                break;
            case R.id.biglietto_btn:
                Intent ticketIntent = new Intent(getActivity(), UserTicketPage.class);
                startActivity(ticketIntent);
                break;
        }
    }

    private void startMaps(Context context){
        Uri gmmIntentUri = Uri.parse("geo:" + GPSTracker.VERONA_LATIDUDE +"," + GPSTracker.VERONA_LONGITUDE + "?q=" + GPSTracker.VERONA_LATIDUDE + "," + GPSTracker.VERONA_LONGITUDE);
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
        context.startActivity(intent);
    }

    private void startMapPage(){
        Intent mapIntent = new Intent(getActivity(), MapPage.class);
        startActivity(mapIntent);
    }
}
