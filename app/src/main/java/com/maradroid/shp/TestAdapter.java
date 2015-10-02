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
public class TestAdapter {

   /* private ArrayList<Test> spomenikArray;
    private ArrayList<Test> itemsAll;
    private ArrayList<Test> suggestions;
    private int viewResourceId;
    private int previousConstraints = 0;

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
            FilterResults filterResults = new FilterResults();

            if(constraint == null){
                Log.e("maradroid","null");
                filterResults.values = itemsAll;
                filterResults.count = itemsAll.size();
            }else{
                // if no constraint is given, return the whole list
                if (constraint.length() == 0 || previousConstraints > constraint.length()) {
                    Log.e("maradroid","0");
                    previousConstraints = constraint.length();
                    filterResults.values = itemsAll;
                    filterResults.count = itemsAll.size();
                } else {
                    Log.e("maradroid","ok " + constraint);
                    previousConstraints = constraint.length();
                    suggestions.clear();
                    for (Test spomenik : spomenikArray) {
                        //String[] tempSpomenik = spomenik.ime.split(" ");
                        //String[] tempConstraint = constraint.toString().split(" ");
                        //for(String item: tempSpomenik){

                            if(spomenik.ime.toLowerCase().contains(constraint.toString().toLowerCase())){
                                suggestions.add(spomenik);
                                break;
                            }
                        //}

                    }
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            Log.e("maradroid","publish: " + results.count);

            if (results.count > 0) {
                clear();
                addAll((ArrayList<Test>) results.values);
                notifyDataSetChanged();
            }

        }
    };*/
}
