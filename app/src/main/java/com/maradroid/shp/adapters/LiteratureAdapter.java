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
public class LiteratureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] dataArray;

    public LiteratureAdapter(String[] array) {

        this.dataArray = array;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_literature, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.tvText.setText(dataArray[position]);

        if (position == dataArray.length - 1) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHolder.view.getLayoutParams();
            params.bottomMargin = params.topMargin;

        } else {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHolder.view.getLayoutParams();
            params.bottomMargin = 0;
        }
    }

    @Override
    public int getItemCount() {
        return dataArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvText;
        private View view;

        public ViewHolder(View v) {
            super(v);

            this.view = v;
            this.tvText = (TextView) v.findViewById(R.id.tv_literature);
        }
    }
}
