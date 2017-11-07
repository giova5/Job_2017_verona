package com.emis.job2017;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;


public class NewsPage extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsModel>>, ListView.OnItemClickListener {

    private static int NEWS_LOADER_ID = 2;
    private NewsAdapter newsAdapter;
    private ListView newsList;
    private ProgressBar newsSpinner;


    public NewsPage(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_page, container, false);
        newsList = (ListView) view.findViewById(R.id.news_list);
        newsSpinner = (ProgressBar) view.findViewById(R.id.news_progress_bar);
        newsSpinner.setVisibility(View.VISIBLE);
        newsAdapter = new NewsAdapter(null, getActivity());
        newsList.setOnItemClickListener(this);
        newsList.setAdapter(newsAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<NewsModel>> onCreateLoader(int id, Bundle args) {
        newsSpinner.setVisibility(View.VISIBLE);
        return new NewsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsModel>> loader, List<NewsModel> data) {
        newsSpinner.setVisibility(View.GONE);
        newsAdapter.switchItems(data);

    }

    @Override
    public void onLoaderReset(Loader<List<NewsModel>> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
