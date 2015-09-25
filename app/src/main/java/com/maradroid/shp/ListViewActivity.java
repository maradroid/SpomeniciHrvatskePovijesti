package com.maradroid.shp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mara on 3/15/15.
 */
public class ListViewActivity extends ActionBarActivity implements ListViewAdapter.ClickListener{

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;
    private String[] listaSpomenika, spomenik;
    private Intent intent;
    private Map<String, Integer> map;
    private String stoljece, tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        stoljece = getIntent().getStringExtra("stoljece");
        tag = getIntent().getStringExtra("tag");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(stoljece);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_chevron_left_white_36dp);

        listaSpomenika = getResources().getStringArray(getResources().getIdentifier(tag, "array", this.getPackageName()));
        spomenik = getResources().getStringArray(getResources().getIdentifier(tag + "_tag", "array", this.getPackageName()));

        map = new HashMap<String, Integer>();

        for(int i = 0; i < listaSpomenika.length; i++){
            map.put(listaSpomenika[i], i);
        }

        Collections.sort(Arrays.asList(listaSpomenika), Collator.getInstance(new Locale("hr_HR")));

        mRecycler = (RecyclerView) findViewById(R.id.list_view);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new ListViewAdapter(listaSpomenika);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v, int position, boolean isLongClick) {

        LinearLayout ll = (LinearLayout) v;
        TextView tv = (TextView) ll.getChildAt(0);

        intent = new Intent(this, SpomenikInfo.class);
        intent.putExtra("tag_tag", spomenik[map.get(tv.getText())]);
        intent.putExtra("ime_spomenika", listaSpomenika[position]);
        intent.putExtra("stoljece", stoljece);
        startActivity(intent);

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
