package com.maradroid.shp.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.maradroid.shp.R;
import com.maradroid.shp.api.ApiSingleton;
import com.maradroid.shp.api.SpomenikEvent;
import com.maradroid.shp.dataModels.Spomenik;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mara on 3/16/15.
 */
public class SpomenikInfo extends ActionBarActivity{

    private List<String> info;
    private TextView nazivSpomenika;
    private TextView mjestoLabel;
    private TextView mjesto;
    private TextView godinaLabel;
    private TextView godina;
    private TextView pismoLabel;
    private TextView pismo;
    private TextView jezikLabel;
    private TextView jezik;
    private TextView sadrzajLabel;
    private TextView sadrzaj;
    private TextView velicinaLabel;
    private TextView velicina;
    private TextView zanimljivostiLabel;
    private TextView zanimljivosti;
    private Spomenik dataObject;
    private String tag;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spomenici_info);

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

            SpomenikEvent event = ApiSingleton.getInstance().getSpomenikEvent();

            if (event != null) {
                dataObject = event.getSpomenikById(position);
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
            getSupportActionBar().setTitle(dataObject.getStoljece() + ". stoljeće");
        }
    }

    private void initViews() {

        nazivSpomenika = (TextView) findViewById(R.id.ime_spomenika_tv);
        mjestoLabel = (TextView) findViewById(R.id.mjesto_label_tv);
        mjesto = (TextView) findViewById(R.id.mjesto_tv);
        godinaLabel = (TextView) findViewById(R.id.godina_label_tv);
        godina = (TextView) findViewById(R.id.godina_tv);
        pismoLabel = (TextView) findViewById(R.id.pismo_label_tv);
        pismo = (TextView) findViewById(R.id.pismo_tv);
        jezikLabel = (TextView) findViewById(R.id.jezik_label_tv);
        jezik = (TextView) findViewById(R.id.jezik_tv);
        sadrzajLabel = (TextView) findViewById(R.id.sadrzaj_label_tv);
        sadrzaj = (TextView) findViewById(R.id.sadrzaj_tv);
        velicinaLabel = (TextView) findViewById(R.id.velicina_label_tv);
        velicina = (TextView) findViewById(R.id.velicina_tv);
        zanimljivostiLabel = (TextView) findViewById(R.id.zanimljivosti_label_tv);
        zanimljivosti = (TextView) findViewById(R.id.zanimljivosti_tv);
    }

    private void setData() {

        if (dataObject != null) {

            nazivSpomenika.setText(dataObject.getIme());

            TextView[] text = {mjesto,godina,pismo,jezik,sadrzaj,velicina,zanimljivosti};
            TextView[] label = {mjestoLabel,godinaLabel,pismoLabel,jezikLabel,sadrzajLabel,velicinaLabel,zanimljivostiLabel};

            info = new ArrayList<String>();
            info.add(dataObject.getMjesto());
            info.add(dataObject.getGodina());
            info.add(dataObject.getPismo());
            info.add(dataObject.getJezik());
            info.add(dataObject.getSadrzaj());
            info.add(dataObject.getVelicina());
            info.add(dataObject.getZanimljivosti());

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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home) {
            onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
