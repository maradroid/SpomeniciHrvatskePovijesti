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
import android.view.View;

/**
 * Created by mara on 3/15/15.
 */
public class ListViewActivity extends ActionBarActivity implements ListViewAdapter.ClickListener{

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;
    private String[] listaSpomenika, spomenik;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        listaSpomenika = getResources().getStringArray(getResources().getIdentifier(getIntent().getStringExtra("tag"), "array", this.getPackageName()));
        spomenik = getResources().getStringArray(getResources().getIdentifier(getIntent().getStringExtra("tag")+"_tag", "array", this.getPackageName()));

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

        intent = new Intent(this, SpomenikInfo.class);
        intent.putExtra("tag_tag",spomenik[position]);
        intent.putExtra("ime_spomenika",listaSpomenika[position]);
        startActivity(intent);

    }
}
