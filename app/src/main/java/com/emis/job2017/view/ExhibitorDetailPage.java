package com.emis.job2017.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.utils.RealmUtils;

import javax.annotation.Nullable;

/**
 * Created by jo5 on 10/11/17.
 */

public class ExhibitorDetailPage extends Fragment {

    public static final String EXHIB_ID = "EXHIB_ID";
    public static final String QR_CODE_READER = "QR_CODE_READER";

    private int exhibID;
    private boolean qrCodeReader;
    private TextView exhibIDTextView;

    public ExhibitorDetailPage(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exhibID = getArguments().getInt(EXHIB_ID, -1);
        qrCodeReader = getArguments().getBoolean(QR_CODE_READER, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_exhib_detail, container, false);
        exhibIDTextView = (TextView) view.findViewById(R.id.exhib_id);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exhibIDTextView.setText(String.valueOf(RealmUtils.getExhibitor(exhibID).getIdExhibitor()));
    }
/**
 * @param qrCodeReader boolean that indicates if ExhibitorFragmen is open from QrCodeFragment or not.
 *
 * */
    public static ExhibitorDetailPage newInstance(int exhibID, boolean qrCodeReader){
        ExhibitorDetailPage exhibDetailPage = new ExhibitorDetailPage();

        Bundle args = new Bundle();
        args.putInt(EXHIB_ID, exhibID);
        args.putBoolean(QR_CODE_READER, qrCodeReader);
        exhibDetailPage.setArguments(args);
        return exhibDetailPage;
    }


}
