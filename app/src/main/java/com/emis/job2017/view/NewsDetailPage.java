package com.emis.job2017.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.utils.RealmUtils;

/**
 * Created by jo5 on 10/11/17.
 */

public class NewsDetailPage extends Fragment{

    public static final String NEWS_ID = "NEWS_ID";
    public int newsID;
    private TextView newsIDTextView;

    public NewsDetailPage(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsID = getArguments().getInt(NEWS_ID, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        newsIDTextView = (TextView) view.findViewById(R.id.news_id);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsIDTextView.setText(String.valueOf(RealmUtils.getNews(newsID).getIdArticle()));
    }

    public static NewsDetailPage newInstance(int calendarID){
        NewsDetailPage newsDetailPage = new NewsDetailPage();

        Bundle args = new Bundle();
        args.putInt(NEWS_ID, calendarID);
        newsDetailPage.setArguments(args);
        return newsDetailPage;
    }

}
