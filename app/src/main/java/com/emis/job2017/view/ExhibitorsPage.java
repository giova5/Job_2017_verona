package com.emis.job2017.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.emis.job2017.R;
import com.emis.job2017.adapters.ExhibitorsAdapter;
import com.emis.job2017.loaders.ExhibitorsLoader;
import com.emis.job2017.models.ExhibitorsModel;
import com.emis.job2017.utils.RealmUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jo5 on 03/11/17.
 */

public class ExhibitorsPage extends Fragment implements LoaderManager.LoaderCallbacks<List<ExhibitorsModel>>, ListView.OnItemClickListener {

    private ListView exhibitorsList;
    private EditText exhibitorsSearchBar;
    private static int EXHIBITORS_LOADER_ID = 3;
    private ExhibitorsAdapter exhibitorsAdapter;
    private ProgressBar exhibitorsSpinner;
    private List<ExhibitorsModel> fullExhibitorsList = new LinkedList<>();


    public ExhibitorsPage(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exhibitors_page, container, false);
        exhibitorsList = (ListView) view.findViewById(R.id.exhibitors_list);
        exhibitorsSearchBar = (EditText) view.findViewById(R.id.exhibitors_search_bar);
        exhibitorsSpinner = (ProgressBar) view.findViewById(R.id.exhibitors_progress_bar);
        exhibitorsSpinner.setVisibility(View.VISIBLE);
        exhibitorsAdapter = new ExhibitorsAdapter(null, getActivity());
        exhibitorsList.setTextFilterEnabled(true);
        exhibitorsList.setOnItemClickListener(this);
        exhibitorsList.setAdapter(exhibitorsAdapter);

        setUpTextChangedListener();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getLoaderManager().restartLoader(EXHIBITORS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<ExhibitorsModel>> onCreateLoader(int id, Bundle args) {
        exhibitorsSpinner.setVisibility(View.VISIBLE);
        return new ExhibitorsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<ExhibitorsModel>> loader, List<ExhibitorsModel> data) {
        exhibitorsSpinner.setVisibility(View.GONE);
        data = (data == null) ? RealmUtils.getSortedDealers() : data;
        exhibitorsAdapter.switchItems(data);
        fullExhibitorsList.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ExhibitorsModel>> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        closeKeyboard();
        ExhibitorsModel exhibitorsModel = exhibitorsAdapter.getListFiltered().get(position);
        startFragment(exhibitorsModel.getIdExhibitor());
    }

    private void startFragment(int exhibID){
        ExhibitorDetailPage fragment2 = ExhibitorDetailPage.newInstance(exhibID, false, false);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setUpTextChangedListener(){
        exhibitorsSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //When text changes this method is called.
                exhibitorsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
