package com.maradroid.shp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.maradroid.shp.R;
import com.maradroid.shp.dataModels.Monument;

import java.util.ArrayList;


/**
 * Created by mara on 9/30/15.
 */
public class CustomSearchAdapter extends ArrayAdapter<Monument>{

    private ArrayList<Monument> monumentArray;
    private ArrayList<Monument> itemsAll;
    private ArrayList<Monument> suggestions;

    private int viewResourceId;
    private int previousConstraints = 0;

    public CustomSearchAdapter(Context context, int viewResourceId, ArrayList<Monument> items) {
        super(context, viewResourceId, items);
        this.monumentArray = items;
        this.itemsAll = new ArrayList<Monument>(items);
        this.suggestions = new ArrayList<Monument>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }

        Monument monument = monumentArray.get(position);
        if (monument != null) {
            TextView tvName = (TextView) v.findViewById(R.id.tv_name);
            TextView tvCentury = (TextView) v.findViewById(R.id.tv_century);

            tvName.setText(monument.getName());
            tvCentury.setText(monument.getCentury() + ". stoljeće");

        }
        return v;
    }

    @Override
    public android.widget.Filter getFilter() {
        return nameFilter;
    }

    android.widget.Filter nameFilter = new android.widget.Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if(constraint == null){
                filterResults.values = itemsAll;
                filterResults.count = itemsAll.size();

            }else{
                // if no constraint is given, return the whole list
                if (constraint.length() == 0 || previousConstraints > constraint.length()) {
                    previousConstraints = constraint.length();
                    //filterResults.values = itemsAll;
                    //filterResults.count = itemsAll.size();
                    monumentArray.clear();
                    monumentArray.addAll(itemsAll);
                    suggestions.clear();

                    for (Monument monument : monumentArray) {

                        if(monument.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                            suggestions.add(monument);
                        }
                    }

                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();

                } else {

                    previousConstraints = constraint.length();
                    suggestions.clear();

                    for (Monument monument : monumentArray) {

                        if(monument.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                            suggestions.add(monument);
                        }
                    }

                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            if (results.count > 0) {
                clear();
                addAll((ArrayList<Monument>) results.values);
                notifyDataSetChanged();
            }
        }
    };
}