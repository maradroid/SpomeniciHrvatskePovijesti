package com.maradroid.shp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mara on 9/30/15.
 */
public class TestAdapter extends ArrayAdapter<Test> {
    private final String MY_DEBUG_TAG = "CustomerAdapter";
    private ArrayList<Test> spomenikArray;
    private ArrayList<Test> itemsAll;
    private ArrayList<Test> suggestions;
    private int viewResourceId;

    public TestAdapter(Context context, int viewResourceId, ArrayList<Test> items) {
        super(context, viewResourceId, items);
        this.spomenikArray = items;
        this.itemsAll = (ArrayList<Test>) items.clone();
        this.suggestions = new ArrayList<Test>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }

        Test spomenik = spomenikArray.get(position);
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
        return nameFilter;
    }

    android.widget.Filter nameFilter = new android.widget.Filter() {
       @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Test)resultValue).ime;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Test spomenik : spomenikArray) {
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
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Test>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(itemsAll);
            }
            notifyDataSetChanged();
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
