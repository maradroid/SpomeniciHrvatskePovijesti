package com.maradroid.glagopedija.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maradroid.glagopedija.R;
import com.maradroid.glagopedija.dataModels.Monument;

import java.util.ArrayList;


/**
 * Created by mara on 3/15/15.
 */
public class ListActivityAdapter extends RecyclerView.Adapter<ListActivityAdapter.ViewHolder> {

    private ArrayList<Monument> dataArray;
    private ClickListener clickListener;

    public ListActivityAdapter(ArrayList<Monument> listaSpomenika) {

        this.dataArray = listaSpomenika;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvListItem;

        public ViewHolder(View v) {
            super(v);

            this.tvListItem = (TextView) v.findViewById(R.id.tv_item_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition());
        }
    }

    @Override
    public ListActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monument_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvListItem.setText(dataArray.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    public interface ClickListener {
        public void onClick(View v, int position);

    }

    public void setClickListener(ClickListener clickListener) {

        if(clickListener != null)
            this.clickListener = clickListener;
    }

}
