package com.emis.job2017.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.emis.job2017.R;
import com.emis.job2017.adapters.ExhibitorsAdapter;
import com.emis.job2017.loaders.ExhibitorsLoader;
import com.emis.job2017.loaders.FavoritesLoader;
import com.emis.job2017.models.ExhibitorsModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jo5 on 26/11/17.
 */

public class FavoritesPage extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<ExhibitorsModel>>, ListView.OnItemClickListener {

    private ExhibitorsAdapter exhibitorsAdapter;
    private ProgressBar exhibitorsSpinner;
    private ListView exhibitorsList;
    private static int EXHIBITORS_LOADER_ID = 4;
    private List<ExhibitorsModel> fullExhibitorsList = new LinkedList<>();
    private EditText search;

    public FavoritesPage(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_exhibitors_page);

        exhibitorsList = (ListView) findViewById(R.id.exhibitors_list);
        exhibitorsList.setOnItemClickListener(this);
        exhibitorsSpinner = (ProgressBar) findViewById(R.id.exhibitors_progress_bar);
        search = (EditText) findViewById(R.id.exhibitors_search_bar);
        search.setVisibility(View.GONE);
        exhibitorsSpinner.setVisibility(View.VISIBLE);
        exhibitorsAdapter = new ExhibitorsAdapter(null, this);
        exhibitorsList.setAdapter(exhibitorsAdapter);

        getLoaderManager().restartLoader(EXHIBITORS_LOADER_ID, null, this);

    }

    @Override
    public Loader<List<ExhibitorsModel>> onCreateLoader(int id, Bundle args) {
        exhibitorsSpinner.setVisibility(View.VISIBLE);
        return new FavoritesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<ExhibitorsModel>> loader, List<ExhibitorsModel> data) {
        exhibitorsSpinner.setVisibility(View.GONE);
        exhibitorsAdapter.switchItems(data);
        fullExhibitorsList.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ExhibitorsModel>> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ExhibitorsModel exhibitorsModel = exhibitorsAdapter.getListFiltered().get(position);
        startFragment(exhibitorsModel.getIdExhibitor());
    }

    private void startFragment(int exhibID){
        ExhibitorDetailPage fragment2 = ExhibitorDetailPage.newInstance(exhibID, false, true);
        android.support.v4.app.FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
