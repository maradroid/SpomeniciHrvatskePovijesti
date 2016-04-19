package com.maradroid.shp.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maradroid.shp.R;
import com.maradroid.shp.adapters.LiteratureAdapter;

/**
 * Created by mara on 9/23/15.
 */
public class LiteratureActivity extends ActionBarActivity {

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private LiteratureAdapter mAdapter;

    private String[] literaturaArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literature);

        initToolbar();
        getData();
        setupRecyclerView();
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Literatura");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_chevron_left_white_36dp);
    }

    private void getData() {
        literaturaArray = getResources().getStringArray(R.array.literatura_array);
    }

    private void setupRecyclerView() {

        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new LiteratureAdapter(literaturaArray);
        mRecycler.setAdapter(mAdapter);
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
