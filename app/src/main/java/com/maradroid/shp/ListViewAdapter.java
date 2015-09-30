package com.maradroid.shp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;


/**
 * Created by mara on 3/15/15.
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<String> listaSpomenika;
    private static ClickListener clickListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case

        TextView listItem;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            this.listItem = (TextView) v.findViewById(R.id.list_item_tv);

        }


        @Override
        public void onClick(View v) {
            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getPosition(), false);
        }

    }

    public interface ClickListener {

        /**
         * Called when the view is clicked.
         *
         * @param v view that is clicked
         * @param position of the clicked item
         * @param isLongClick true if long click, false otherwise
         */
        public void onClick(View v, int position, boolean isLongClick);

    }

    public void setClickListener(ClickListener clickListener) {
        if(clickListener != null)
        this.clickListener = clickListener;
    }





    // Provide a suitable constructor (depends on the kind of dataset)
    public ListViewAdapter(List<String> tags) {

        this.listaSpomenika = tags;
    }




    // Create new views (invoked by the layout manager)
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }






    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.listItem.setText(listaSpomenika.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaSpomenika.size();
    }

}
