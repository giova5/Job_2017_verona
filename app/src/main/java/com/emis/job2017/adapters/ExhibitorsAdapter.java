package com.emis.job2017.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.ExhibitorsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo5 on 03/11/17.
 */

public class ExhibitorsAdapter extends ArrayAdapter<ExhibitorsModel> {

    private List<ExhibitorsModel> items;
    Context mContext;

    private static class ExhibitorsViewHolder {
        TextView idExhibitors;
        TextView idCategory;
        TextView idPadiglione;
        TextView name;
        TextView massima;
        TextView description;
        TextView standNumber;
        TextView standCoordinates;
        TextView webSite;
        TextView youtubeLink;
        TextView phone;
        TextView firstEmail;
        TextView secondEmail;
        TextView thirdEmail;
        TextView descriptionNoHtml;
        TextView logoPath;
    }

        public ExhibitorsAdapter(ArrayList<ExhibitorsModel> data, Context context) {
        super(context, 0);
        this.items = data;
        this.mContext = context;
    }

    public void switchItems(List<ExhibitorsModel> newItems){
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

        ExhibitorsViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exhibitors_list_item, parent, false);
            holder = new ExhibitorsViewHolder();
            holder.idExhibitors = (TextView) convertView.findViewById(R.id.id_exhibitor);
            holder.idCategory = (TextView) convertView.findViewById(R.id.id_category);
            holder.idPadiglione = (TextView) convertView.findViewById(R.id.idPadiglione);
            holder.name = (TextView) convertView.findViewById(R.id.exhibitor_name);
            holder.massima = (TextView) convertView.findViewById(R.id.exhibitor_massima);
            holder.description = (TextView) convertView.findViewById(R.id.exhibitor_description);
            holder.standNumber = (TextView) convertView.findViewById(R.id.stand_number);
            holder.standCoordinates = (TextView) convertView.findViewById(R.id.stand_coordinates);
            holder.webSite = (TextView) convertView.findViewById(R.id.web_site);
            holder.youtubeLink = (TextView) convertView.findViewById(R.id.youtube_link);
            holder.phone = (TextView) convertView.findViewById(R.id.phone_number);
            holder.firstEmail = (TextView) convertView.findViewById(R.id.first_email);
            holder.secondEmail = (TextView) convertView.findViewById(R.id.second_email);
            holder.thirdEmail = (TextView) convertView.findViewById(R.id.third_email);
            holder.descriptionNoHtml= (TextView) convertView.findViewById(R.id.description_no_html);
            holder.logoPath = (TextView) convertView.findViewById(R.id.logo_path);

            convertView.setTag(holder);
        } else {
            holder = (ExhibitorsViewHolder) convertView.getTag();
        }

        final ExhibitorsModel item = items.get(position);

        holder.idExhibitors.setText(String.valueOf(item.getIdExhibitor()));
        holder.idCategory.setText(String.valueOf(item.getIdCategory()));
        holder.idPadiglione.setText(String.valueOf(item.getIdPadiglione()));
        holder.name.setText(item.getName());
        holder.massima.setText(item.getMassima());
        holder.description.setText(item.getDescription());
        holder.standNumber.setText(item.getStandNumber());
        holder.standCoordinates.setText(item.getStandCoordinates());
        holder.webSite.setText(item.getWebSite());
        holder.youtubeLink.setText(item.getYoutubeLink());
        holder.phone.setText(item.getPhoneNumber());
        holder.firstEmail.setText((item.getArrayEmails() == null) ? "---" : item.getArrayEmails().get(0));
        holder.secondEmail.setText(item.getArrayEmails() == null ? "---" : item.getArrayEmails().get(1));
        holder.thirdEmail.setText(item.getArrayEmails() == null ? "---" : item.getArrayEmails().get(2));
        holder.descriptionNoHtml.setText(item.getDescriptionNoHtml());
        holder.logoPath.setText(item.getLogoPath());

        return convertView;

    }

}
