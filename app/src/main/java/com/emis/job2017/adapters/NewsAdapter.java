package com.emis.job2017.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo5 on 02/11/17.
 */

public class NewsAdapter extends ArrayAdapter<NewsModel> {

    private List<NewsModel> items;
    Context mContext;

    private static class NewsViewHolder {
        TextView idArticle;
        TextView title;
        TextView content;
        TextView contentNoHtml;
        TextView review;
        TextView author;
        TextView date;
        TextView link;
    }

    public NewsAdapter(ArrayList<NewsModel> data, Context context) {
        super(context, 0);
        this.items = data;
        this.mContext = context;
    }

    public void switchItems(List<NewsModel> newItems){
        items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
            holder = new NewsViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.news_title);
            holder.content = (TextView) convertView.findViewById(R.id.news_desc);
            convertView.setTag(holder);
        } else {
            holder = (NewsViewHolder) convertView.getTag();
        }

        final NewsModel item = items.get(position);

        holder.title.setText(Html.fromHtml(item.getTitle().replace("\n", "<br>")));
        holder.content.setText(Html.fromHtml(item.getContent().replace("\n", "<br>")));

        return convertView;

    }

}
