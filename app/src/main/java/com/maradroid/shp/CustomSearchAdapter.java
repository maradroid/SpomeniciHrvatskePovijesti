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
    private final String MY_DEBUG_TAG = "CustomerAdapter";
    private ArrayList<Spomenik> spomenikArray;
    //private ArrayList<Spomenik> itemsAll;
    private ArrayList<Spomenik> suggestions;
    private int viewResourceId;

    public CustomSearchAdapter(Context context, int viewResourceId, ArrayList<Spomenik> items) {
        super(context, viewResourceId, items);
        this.spomenikArray = items;
        //this.itemsAll = (ArrayList<Customer>) items.clone();
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
            stoljece.setText(spomenik.stoljece);

        }
        return v;
    }

    @Override
    public android.widget.Filter getFilter() {
        return super.getFilter();
    }

    android.widget.Filter nameFilter = new android.widget.Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Spomenik)resultValue).ime;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Spomenik spomenik : spomenikArray) {
                    if(spomenik.ime.toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(spomenik);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            ArrayList<Spomenik> filteredList = (ArrayList<Spomenik>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Spomenik spomenik : filteredList) {
                    add(spomenik);
                }
                notifyDataSetChanged();
            }
        }
    };

    /*{
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Customer)(resultValue)).getName();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Customer customer : itemsAll) {
                    if(customer.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Customer> filteredList = (ArrayList<Customer>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Customer c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };*/

}