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
    private int exhibID;
    private TextView exhibIDTextView;

    public ExhibitorDetailPage(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exhibID = getArguments().getInt(EXHIB_ID, -1);
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

        exhibIDTextView.setText(String.valueOf(RealmUtils.getExhibitors(exhibID).getIdExhibitor()));
    }

    public static ExhibitorDetailPage newInstance(int exhibID){
        ExhibitorDetailPage exhibDetailPage = new ExhibitorDetailPage();

        Bundle args = new Bundle();
        args.putInt(EXHIB_ID, exhibID);
        exhibDetailPage.setArguments(args);
        return exhibDetailPage;
    }


}
