package com.emis.job2017.view;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.emis.job2017.R;
import com.emis.job2017.adapters.NewsAdapter;
import com.emis.job2017.adapters.OtherNewsAdapter;
import com.emis.job2017.loaders.NewsLoader;
import com.emis.job2017.models.NewsModel;
import com.emis.job2017.utils.RealmUtils;

import java.util.LinkedList;
import java.util.List;


public class NewsPage extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsModel>> {

    private static int NEWS_LOADER_ID = 2;
    private NewsAdapter newsAdapter;
    private OtherNewsAdapter otherNewsAdapter;
    private ListView newsList;
    private ListView otherNewsList;
    private ProgressBar newsSpinner;
    private List<NewsModel> fullNewsList = new LinkedList<>();
    private List<NewsModel> fullOtherNewsList = new LinkedList<>();


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

        newsList = (ListView) view.findViewById(R.id.notifications_list);
        otherNewsList = (ListView) view.findViewById(R.id.other_news_list);

        newsSpinner = (ProgressBar) view.findViewById(R.id.news_progress_bar);
        newsSpinner.setVisibility(View.VISIBLE);

        newsAdapter = new NewsAdapter(null, getActivity());
        newsList.setAdapter(newsAdapter);

        otherNewsAdapter = new OtherNewsAdapter(null, getActivity());
        otherNewsList.setAdapter(otherNewsAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<NewsModel>> onCreateLoader(int id, Bundle args) {
        newsSpinner.setVisibility(View.VISIBLE);
        return new NewsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsModel>> loader, List<NewsModel> data) {
        newsSpinner.setVisibility(View.GONE);

        List<NewsModel> news = RealmUtils.getNotifications(true);
        List<NewsModel> otherNews = RealmUtils.getNotifications(false);

        newsAdapter.switchItems(news);
        otherNewsAdapter.switchItems(otherNews);

        fullNewsList.addAll(news);
        fullOtherNewsList.addAll(otherNews);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsModel>> loader) {

    }

    private void startFragment(int newsID){
        NewsDetailPage fragment2 = NewsDetailPage.newInstance(newsID);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
