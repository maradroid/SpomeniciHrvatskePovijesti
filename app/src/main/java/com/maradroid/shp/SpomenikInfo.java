package com.maradroid.shp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("stoljece"));
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_chevron_left_white_36dp);

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

        tag = getIntent().getStringExtra("tag");
        position = getIntent().getIntExtra("position", -1);

        dataObject = ApiSingleton.getInstance().stoljeceMap.get(tag).get(position);

        nazivSpomenika.setText(dataObject.ime);

        TextView[] text = {mjesto,godina,pismo,jezik,sadrzaj,velicina,zanimljivosti};
        TextView[] label = {mjestoLabel,godinaLabel,pismoLabel,jezikLabel,sadrzajLabel,velicinaLabel,zanimljivostiLabel};

        info = new ArrayList<String>();
        info.add(dataObject.mjesto);
        info.add(dataObject.godina);
        info.add(dataObject.pismo);
        info.add(dataObject.jezik);
        info.add(dataObject.sadrzaj);
        info.add(dataObject.velicina);
        info.add(dataObject.zanimljivosti);

        for(int i=0; i<7;i++){
            if(!info.get(i).equals("$")){
                text[i].setText(info.get(i));

            }else{

                text[i].setVisibility(View.GONE);
                label[i].setVisibility(View.GONE);
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
