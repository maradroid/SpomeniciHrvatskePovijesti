package com.maradroid.shp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mara on 3/16/15.
 */
public class SpomenikInfo extends ActionBarActivity{

    private String[] info;
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
    //private TextView[] text = {mjesto,godina,pismo,jezik,sadrzaj,velicina,zanimljivosti};
    //private TextView[] label = {mjestoLabel,godinaLabel,pismoLabel,jezikLabel,sadrzajLabel,velicinaLabel,zanimljivostiLabel};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spomenici_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

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

        TextView[] text = {mjesto,godina,pismo,jezik,sadrzaj,velicina,zanimljivosti};
        TextView[] label = {mjestoLabel,godinaLabel,pismoLabel,jezikLabel,sadrzajLabel,velicinaLabel,zanimljivostiLabel};

        info = getResources().getStringArray(getResources().getIdentifier(getIntent().getStringExtra("tag_tag"), "array", this.getPackageName()));

        nazivSpomenika.setText(getIntent().getStringExtra("ime_spomenika"));


        for(int i=0; i<7;i++){
            if(!info[i].equals("$")){
                text[i].setText(info[i]);
                Log.e("dsfgsdsf",""+info[i]);
            }else{

                text[i].setVisibility(View.GONE);
                label[i].setVisibility(View.GONE);
            }
        }


    }
}
