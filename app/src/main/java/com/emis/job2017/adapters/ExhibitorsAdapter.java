package com.emis.job2017.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.emis.job2017.R;
import com.emis.job2017.models.ExhibitorsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo5 on 03/11/17.
 */

public class ExhibitorsAdapter extends ArrayAdapter<ExhibitorsModel> implements Filterable {

    private List<ExhibitorsModel> items;
    List<ExhibitorsModel> listFiltered = new ArrayList<ExhibitorsModel>();
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
        ImageView dealer_icon;
    }

        public ExhibitorsAdapter(ArrayList<ExhibitorsModel> data, Context context) {
        super(context, 0);
        this.items = data;
        this.mContext = context;
    }

    public void switchItems(List<ExhibitorsModel> newItems){
        items = newItems;
        listFiltered = newItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listFiltered != null ? listFiltered.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ExhibitorsViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exhibitors_list_item, parent, false);
            holder = new ExhibitorsViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.dealer_name);
            holder.idCategory = (TextView) convertView.findViewById(R.id.dealer_category);
            holder.dealer_icon = (ImageView) convertView.findViewById(R.id.dealer_icon);
            convertView.setTag(holder);
        } else {
            holder = (ExhibitorsViewHolder) convertView.getTag();
        }

        final ExhibitorsModel item = listFiltered.get(position);

        holder.name.setText(Html.fromHtml(item.getName().replace("\n", "<br>")));
        holder.idCategory.setText(parseCategory(item.getIdCategory()));
        holder.dealer_icon.setBackgroundColor(parseColor(item.getIdCategory()));

        return convertView;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = items.size();
                    results.values = items;
                }else{
                    List<ExhibitorsModel> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();

                    for(ExhibitorsModel current : items){
                        String exhibName = current.getName();
                        if (exhibName.toUpperCase().contains(searchStr))
                            resultsData.add(current);
                    }
                    results.count = resultsData.size();
                    results.values = resultsData;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFiltered = (ArrayList<ExhibitorsModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private String parseCategory(int category){
        switch (category){
            case 1:
                return "Lingue straniere e turismo";
            case 2:
                return "Formazione Accademica";
            case 3:
                return "Formazione Professionale";
            case 4:
                return "Educazione e Scuole medie";
            case 5:
                return "Lavoro e alta formazione";
            case 6:
                return "Tecnologia e media";
            case 7:
                return "Sala Convegni";
            default:
                return "Nessuna categoria selezionata";
        }
    }

    private int parseColor(int category){
        switch (category){
            case 1:
                return mContext.getResources().getColor(R.color.colorLingue);
            case 2:
                return mContext.getResources().getColor(R.color.colorFormazioneAccademica);
            case 3:
                return mContext.getResources().getColor(R.color.colorFormazioneProfessionale);
            case 4:
                return mContext.getResources().getColor(R.color.colorEducazione);
            case 5:
                return mContext.getResources().getColor(R.color.colorLavoro);
            case 6:
                return mContext.getResources().getColor(R.color.colorTecnologia);
            case 7:
                return mContext.getResources().getColor(R.color.colorSalaConvegni);
            default:
                return mContext.getResources().getColor(R.color.colorGreen);
        }
    }
}
