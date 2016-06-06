package com.maradroid.shp.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.maradroid.shp.R;
import com.maradroid.shp.api.ApiSingleton;
import com.maradroid.shp.api.MonumentEvent;
import com.maradroid.shp.dataModels.Monument;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mara on 3/16/15.
 */
public class MonumentInfoActivity extends ActionBarActivity{

    private List<String> info;

    private TextView tvMonumentName;
    private TextView tvPlaceLabel;
    private TextView tvPlace;
    private TextView tvYearLabel;
    private TextView tvYear;
    private TextView tvTypeLabel;
    private TextView tvType;
    private TextView tvLanguageLabel;
    private TextView tvLanguage;
    private TextView tvContentLabel;
    private TextView tvContent;
    private TextView tvSizeLabel;
    private TextView tvSize;
    private TextView tvInterestingLabel;
    private TextView tvInteresting;

    private Monument dataObject;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_info);
        Log.e("maradroid","onCreate");
        getData();
        initToolbar();
        initViews();
        setData();

    }

    public void getData() {

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            position = extra.getInt("position", -1);
        }

        if (position != -1) {

            MonumentEvent event = ApiSingleton.getInstance().getMonumentEvent();

            if (event != null) {
                dataObject = event.getMonumentById(position);
            }
        }
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_chevron_left_white_36dp);

        if (dataObject != null) {
            getSupportActionBar().setTitle(dataObject.getCentury() + ". stoljeće");
        }
    }

    private void initViews() {

        tvMonumentName = (TextView) findViewById(R.id.tv_monument_name);
        tvPlaceLabel = (TextView) findViewById(R.id.tv_place_label);
        tvPlace = (TextView) findViewById(R.id.tv_place);
        tvYearLabel = (TextView) findViewById(R.id.tv_year_label);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvTypeLabel = (TextView) findViewById(R.id.tv_type_label);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvLanguageLabel = (TextView) findViewById(R.id.tv_language_label);
        tvLanguage = (TextView) findViewById(R.id.tv_language);
        tvContentLabel = (TextView) findViewById(R.id.tv_content_label);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvSizeLabel = (TextView) findViewById(R.id.tv_size_label);
        tvSize = (TextView) findViewById(R.id.tv_size);
        tvInterestingLabel = (TextView) findViewById(R.id.tv_interesting_label);
        tvInteresting = (TextView) findViewById(R.id.tv_interesting);
    }

    private void setData() {

        if (dataObject != null) {

            tvMonumentName.setText(dataObject.getName());

            TextView[] text = {tvPlace, tvYear, tvType, tvLanguage, tvContent, tvSize, tvInteresting};
            TextView[] label = {tvPlaceLabel, tvYearLabel, tvTypeLabel, tvLanguageLabel, tvContentLabel, tvSizeLabel, tvInterestingLabel};

            info = new ArrayList<String>();
            info.add(dataObject.getPlace());
            info.add(dataObject.getYear());
            info.add(dataObject.getType());
            info.add(dataObject.getLanguage());
            info.add(dataObject.getContent());
            info.add(dataObject.getSize());
            info.add(dataObject.getInteresting());

            for(int i=0; i<7;i++){
                if(!info.get(i).equals("$")){
                    text[i].setText(info.get(i));

                }else{

                    text[i].setVisibility(View.GONE);
                    label[i].setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("maradroid","onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_monument_info, menu);

        MenuItem item = menu.getItem(R.id.ic_map);

        // hide menu if no coordinates
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {Log.e("maradroid","onOptionsItemSelected");

        int id = item.getItemId();

        if(id == android.R.id.home) {
            onBackPressed();
            return true;

        } else if (id == R.id.ic_map) {
            //call maps
        }

        return super.onOptionsItemSelected(item);
    }
}
