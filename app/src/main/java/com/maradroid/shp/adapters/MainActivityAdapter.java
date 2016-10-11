package com.maradroid.shp.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maradroid.shp.R;
import com.maradroid.shp.dataModels.Century;

import java.util.ArrayList;

/**
 * Created by mara on 3/11/15.
 */
public class MainActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Century> dataArray;
    private ClickListener clickListener;
    private Resources resources;
    private String packageName;
    private int screenWidthDp;

    public MainActivityAdapter(ArrayList<Century> data, Context context, int screenWidthDp) {
        this.dataArray = data;
        this.resources = context.getResources();
        this.packageName = context.getPackageName();
        this.screenWidthDp = screenWidthDp;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_century, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.tvCentury.setText(dataArray.get(position).getCentury());
        viewHolder.tvInteresting.setText(dataArray.get(position).getRepresentatives());
        viewHolder.ivCardImage.setImageResource(resources.getIdentifier(dataArray.get(position).getImage() + "_edited" , "mipmap", packageName));

        if (screenWidthDp >= 600 && (position == dataArray.size() - 1 || position == dataArray.size() - 2)) { // tablet, last two
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHolder.view.getLayoutParams();
            params.bottomMargin = params.topMargin;

        } else if (screenWidthDp < 600 && position == dataArray.size() - 1) { //mobile, last one
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHolder.view.getLayoutParams();
            params.bottomMargin = params.topMargin;

        } else {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHolder.view.getLayoutParams();
            params.bottomMargin = 0;
        }
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivCardImage;
        private TextView tvCentury;
        private TextView tvInteresting;
        private View view;

        public ViewHolder(View v) {
            super(v);

            this.ivCardImage = (ImageView) v.findViewById(R.id.iv_card_image);
            this.tvCentury = (TextView) v.findViewById(R.id.tv_century);
            this.tvInteresting = (TextView) v.findViewById(R.id.tv_interesting);
            this.view = v;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(clickListener != null)
                clickListener.onClick(v, getAdapterPosition());
        }
    }

    public interface ClickListener {
        public void onClick(View v, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
