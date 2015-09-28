package com.maradroid.shp;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mara on 3/11/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Stoljece> listaStoljeca = new ArrayList<Stoljece>();
    private static ClickListener clickListener;
    private ImageView img;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case

        ImageView cardImage;
        TextView cardStoljece;
        TextView cardZnamenitosti;
        View view;


        public ViewHolder(View v) {
            super(v);

            this.cardImage = (ImageView) v.findViewById(R.id.card_iv);
            this.cardStoljece = (TextView) v.findViewById(R.id.card_stoljece_tv);
            this.cardZnamenitosti = (TextView) v.findViewById(R.id.card_znamenitosti_tv);
            v.setOnClickListener(this);
            RecyclerViewAdapter.this.img = this.cardImage;
        }

        public ImageView getCardImage() {
            return cardImage;
        }

        @Override
        public void onClick(View v) {
            // If not long clicked, pass last variable as false.
            if(clickListener != null)
                clickListener.onClick(v, getPosition());
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
        public void onClick(View v, int position);

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }




    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(ArrayList<Stoljece> lista) {
        this.listaStoljeca = lista;

    }




    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }






    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        ((ViewHolder)holder).cardStoljece.setText(listaStoljeca.get(position).getCardStoljece());
        ((ViewHolder)holder).cardZnamenitosti.setText(listaStoljeca.get(position).getCardZanimljivosti());
        ((ViewHolder)holder).cardImage.setImageResource(listaStoljeca.get(position).getCardImage());
    }

    public ImageView Mara(){
        return img;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaStoljeca.size();
    }

}
