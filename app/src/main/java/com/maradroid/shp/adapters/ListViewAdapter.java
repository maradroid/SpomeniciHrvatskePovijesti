package com.maradroid.shp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maradroid.shp.R;
import com.maradroid.shp.dataModels.Spomenik;

import java.util.ArrayList;


/**
 * Created by mara on 3/15/15.
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private ArrayList<Spomenik> listaSpomenika;
    private ClickListener clickListener;

    public ListViewAdapter(ArrayList<Spomenik> listaSpomenika) {

        this.listaSpomenika = listaSpomenika;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvListItem;

        public ViewHolder(View v) {
            super(v);

            this.tvListItem = (TextView) v.findViewById(R.id.list_item_tv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition());
        }
    }

    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvListItem.setText(listaSpomenika.get(position).getIme());
    }

    @Override
    public int getItemCount() {
        return listaSpomenika.size();
    }

    public interface ClickListener {
        public void onClick(View v, int position);

    }

    public void setClickListener(ClickListener clickListener) {

        if(clickListener != null)
            this.clickListener = clickListener;
    }

}
