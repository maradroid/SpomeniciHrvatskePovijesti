package com.maradroid.shp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by mara on 9/30/15.
 */
public class CustomSearchAdapter extends ArrayAdapter<Spomenik>{

    private ArrayList<Spomenik> spomenikArray;
    private ArrayList<Spomenik> itemsAll;
    private ArrayList<Spomenik> suggestions;
    private int viewResourceId;
    private int previousConstraints = 0;

    public CustomSearchAdapter(Context context, int viewResourceId, ArrayList<Spomenik> items) {
        super(context, viewResourceId, items);
        this.spomenikArray = items;
        this.itemsAll = new ArrayList<Spomenik>(items);
        this.suggestions = new ArrayList<Spomenik>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }

        Spomenik spomenik = spomenikArray.get(position);
        if (spomenik != null) {
            TextView ime = (TextView) v.findViewById(R.id.ime);
            TextView stoljece = (TextView) v.findViewById(R.id.stoljece);

            ime.setText(spomenik.ime);
            stoljece.setText(spomenik.stoljece + ". stoljeće");

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
                    spomenikArray.clear();
                    spomenikArray.addAll(itemsAll);
                    suggestions.clear();
                    for (Spomenik spomenik : spomenikArray) {

                        if(spomenik.ime.toLowerCase().contains(constraint.toString().toLowerCase())){
                            suggestions.add(spomenik);
                        }
                    }
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();

                } else {
                    previousConstraints = constraint.length();
                    suggestions.clear();
                    for (Spomenik spomenik : spomenikArray) {

                        if(spomenik.ime.toLowerCase().contains(constraint.toString().toLowerCase())){
                            suggestions.add(spomenik);
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
                addAll((ArrayList<Spomenik>) results.values);
                notifyDataSetChanged();
            }
        }
    };

}