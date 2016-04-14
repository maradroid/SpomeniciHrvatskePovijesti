package com.maradroid.shp.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.maradroid.shp.R;
import com.maradroid.shp.adapters.CustomSearchAdapter;
import com.maradroid.shp.adapters.ListViewAdapter;
import com.maradroid.shp.api.ApiSingleton;
import com.maradroid.shp.api.SpomenikEvent;
import com.maradroid.shp.dataModels.Spomenik;

import java.util.ArrayList;

/**
 * Created by mara on 3/15/15.
 */
public class ListViewActivity extends ActionBarActivity implements ListViewAdapter.ClickListener, SpomenikEvent{

    private static final int SEARCH_ITEM_TAG = -2;

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;

    private ArrayList<Spomenik> spomenikArray;

    private String stoljece;
    private String tag;

    private AutoCompleteTextView searchBar;
    private LinearLayout search_ll;
    private boolean isSearching = false;
    private InputMethodManager keyboardManager;

    private Spomenik searchSpomenik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        ApiSingleton.getInstance().setSpomenikEvent(this);

        getExtra();
        initToolbar();
        initViews();
        getData();
        initRecyclerView();
        initSearch();

    }

    private void getExtra() {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            stoljece = extras.getString("stoljece", null);
            tag = extras.getString("tag", null);
        }
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_chevron_left_white_36dp);

        if (stoljece != null) {
            getSupportActionBar().setTitle(stoljece);
        }
    }

    private void initViews() {

        search_ll = (LinearLayout) findViewById(R.id.search_ll);
        searchBar = (AutoCompleteTextView) findViewById(R.id.search);
        mRecycler = (RecyclerView) findViewById(R.id.list_view);
    }

    private void getData() {

        spomenikArray = new ArrayList<Spomenik>();

        if (tag != null) {
            spomenikArray = ApiSingleton.getInstance().getArrayListByCentury(tag);
        }

    }

    private void initRecyclerView() {

        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new ListViewAdapter(spomenikArray);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    private void initSearch() {

        keyboardManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        CustomSearchAdapter adapter = new CustomSearchAdapter(getApplicationContext(),R.layout.search_item, spomenikArray);
        searchBar.setAdapter(adapter);

        setSearchClickListener();

    }

    private void setSearchClickListener() {

        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                searchSpomenik = (Spomenik) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(ListViewActivity.this, SpomenikInfo.class);
                intent.putExtra("position", SEARCH_ITEM_TAG);

                if (keyboardManager.isAcceptingText()) {
                    keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                searchBar.setText("");
                search_ll.setAlpha(0);
                search_ll.setVisibility(View.GONE);

                startActivity(intent);

            }
        });
    }

    public void SearchButtonsClick(View v){

        int id = v.getId();

        if(id == R.id.close_search){

            search_ll.clearAnimation();
            search_ll.animate().alpha(0).setDuration(700).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    search_ll.setVisibility(View.GONE);
                }
            });
            searchBar.setText("");
            isSearching = false;

            if(keyboardManager.isAcceptingText()) {
                keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

        }else if(id == R.id.clear_search){

            searchBar.setText("");

        }else if(id == R.id.search_ll){

            search_ll.clearAnimation();
            search_ll.animate().alpha(0).setDuration(700).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    search_ll.setVisibility(View.GONE);
                }
            });

            searchBar.setText("");
            isSearching = false;

            if(keyboardManager.isAcceptingText()) {
                keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    @Override
    public void onClick(View v, int position) {

        Intent intent = new Intent(this, SpomenikInfo.class);
        intent.putExtra("position", position);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home) {
            onBackPressed();
            return true;

        }

        if (id == R.id.search_icon) {
            search_ll.clearAnimation();
            search_ll.animate().alpha(1).setDuration(700).setListener(null);
            search_ll.setVisibility(View.VISIBLE);
            isSearching = true;

            if (searchBar.requestFocus()) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(isSearching){
            search_ll.clearAnimation();
            search_ll.animate().alpha(0).setDuration(700).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    search_ll.setVisibility(View.GONE);
                }
            });

            searchBar.setText("");
            isSearching = false;

        }else{
            super.onBackPressed();
        }

    }

    @Override
    public Spomenik getSpomenikById(int position) {

        if (position == SEARCH_ITEM_TAG && searchSpomenik != null) {
            return searchSpomenik;

        } else if (position != SEARCH_ITEM_TAG && position != -1) {
            return spomenikArray.get(position);
        }

        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ApiSingleton.getInstance().removeSpomenikEvent();
    }
}
