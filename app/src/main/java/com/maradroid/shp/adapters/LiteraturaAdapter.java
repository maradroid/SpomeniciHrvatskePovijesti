package com.maradroid.shp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maradroid.shp.R;


/**
 * Created by mara on 9/23/15.
 */
public class LiteraturaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String[] literaturaArray;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case

        TextView text;

        public ViewHolder(View v) {
            super(v);

            this.text = (TextView) v.findViewById(R.id.literatura_tv);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LiteraturaAdapter(String[] array) {

        this.literaturaArray = array;
    }




    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.literatura_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }






    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        ((ViewHolder)holder).text.setText(literaturaArray[position]);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return literaturaArray.length;
    }

}
