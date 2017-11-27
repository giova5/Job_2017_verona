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
import com.emis.job2017.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.internal.Util;

/**
 * Created by jo5 on 23/11/17.
 */

public class OtherNewsAdapter extends ArrayAdapter<NewsModel> {

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

    public OtherNewsAdapter(ArrayList<NewsModel> data, Context context) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.other_news_list_item, parent, false);
            holder = new NewsViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.other_news_title);
            holder.date = (TextView) convertView.findViewById(R.id.other_news_date);
            holder.content = (TextView) convertView.findViewById(R.id.other_news_description);
            convertView.setTag(holder);
        } else {
            holder = (NewsViewHolder) convertView.getTag();
        }

        final NewsModel item = items.get(position);

        holder.title.setText(item.getTitle());
        Calendar dateCalendar = Utils.toCalendar(new Date(item.getDate()));

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        holder.date.setText(format1.format(dateCalendar.getTime()));
        holder.content.setText(Html.fromHtml(item.getContent().replace("\n", "<br>")));

        return convertView;

    }
}
