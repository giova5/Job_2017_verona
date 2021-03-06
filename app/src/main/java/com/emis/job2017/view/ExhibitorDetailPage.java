package com.emis.job2017.view;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.ServerOperations;
import com.emis.job2017.models.ExhibitorsModel;
import com.emis.job2017.utils.RealmUtils;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

/**
 * Created by jo5 on 10/11/17.
 */

public class ExhibitorDetailPage extends Fragment implements View.OnClickListener{

    public static final String EXHIB_ID = "EXHIB_ID";
    public static final String QR_CODE_READER = "QR_CODE_READER";
    public static final String IS_FAVORITES = "IS_FAVORITES";

    private TextView dealerName;
    private TextView dealerDescription;
    private TextView dealerMassima;
    private TextView dealerEmail;
    private TextView dealerWebsite;
    private TextView dealerPosition;
    private TextView dealerLocationToWebView;

    private ImageView dealerIcon;

    private int exhibID;
    private boolean qrCodeReader;
    private boolean isFavorites;
    private ExhibitorsModel currentDealer;

    public ExhibitorDetailPage(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exhibID = getArguments().getInt(EXHIB_ID, -1);
        qrCodeReader = getArguments().getBoolean(QR_CODE_READER, false);
        isFavorites = getArguments().getBoolean(IS_FAVORITES, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_exhib_detail, container, false);

        dealerName = (TextView) view.findViewById(R.id.dealer_name);
        dealerDescription = (TextView) view.findViewById(R.id.dealer_description);

        dealerMassima = (TextView) view.findViewById(R.id.dealer_massima);
        dealerEmail = (TextView) view.findViewById(R.id.dealer_email);
        dealerWebsite = (TextView) view.findViewById(R.id.dealer_website);
        dealerPosition = (TextView) view.findViewById(R.id.dealer_location);
        dealerIcon = (ImageView) view.findViewById(R.id.dealer_icon);
        dealerLocationToWebView = (TextView) view.findViewById(R.id.position_button);

        dealerLocationToWebView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ServerOperations.sendGetExhibitorsWithParams(getActivity(), String.valueOf(exhibID));

        currentDealer = RealmUtils.getExhibitor(exhibID);

        String dealerNameContent = (!currentDealer.getName().isEmpty()) ? currentDealer.getName() : "Nessuna informazione";
        String dealerDescriptionContent = currentDealer.getDescription();
        String dealerMassimaContent = (!currentDealer.getMassima().isEmpty()) ? currentDealer.getMassima() : "Nessuna informazione";
        String dealerEmailContent = (!currentDealer.getEmail1().isEmpty()) ? currentDealer.getEmail1() : "Nessuna informazione";
        String dealerWebSiteContent = (!currentDealer.getWebSite().isEmpty()) ? currentDealer.getWebSite() : "Nessuna informazione";
        String stand = (!currentDealer.getPhoneNumber().isEmpty()) ? currentDealer.getPhoneNumber() : "Nessuna informazione";

        dealerName.setText(Html.fromHtml(dealerNameContent));
        dealerDescription.setText(Html.fromHtml(dealerDescriptionContent.replace("\n", "<br>")));
        dealerMassima.setText(Html.fromHtml(dealerMassimaContent.replace("\n", "<br>")));
        dealerEmail.setText(dealerEmailContent);
        dealerWebsite.setText(dealerWebSiteContent);
        dealerPosition.setText(stand);

        if(currentDealer.getStandCoordinates().isEmpty()){
            dealerLocationToWebView.setVisibility(View.GONE);
        }

        Picasso.with(getContext())
                .load(currentDealer.getLogoPath())
                .placeholder(R.drawable.logo_job)
                .error(R.drawable.logo_job)
                .into(dealerIcon);

    }
/**
 * @param qrCodeReader boolean that indicates if ExhibitorFragmen is open from QrCodeFragment or not.
 *
 * */
    public static ExhibitorDetailPage newInstance(int exhibID, boolean qrCodeReader, boolean isFavorites){
        ExhibitorDetailPage exhibDetailPage = new ExhibitorDetailPage();

        Bundle args = new Bundle();
        args.putInt(EXHIB_ID, exhibID);
        args.putBoolean(QR_CODE_READER, qrCodeReader);
        args.putBoolean(IS_FAVORITES, isFavorites);
        exhibDetailPage.setArguments(args);
        return exhibDetailPage;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.position_button:
                String link = "http://job2019.webiac.it/index.php?s=31&app=1&idesp=" + currentDealer.getIdExhibitor() + "#mapplic";
                startGeneralWebviewFragment(link);
                break;
        }

    }

    private void startGeneralWebviewFragment(String link){
        if(!isFavorites) {
            GeneralWebView fragment2 = GeneralWebView.newInstance(link);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, fragment2);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            GeneralWebView fragment2 = GeneralWebView.newInstance(link);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, fragment2);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
