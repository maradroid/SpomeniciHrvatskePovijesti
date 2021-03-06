package com.maradroid.glagopedija.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.maradroid.glagopedija.R;
import com.maradroid.glagopedija.adapters.CustomSearchAdapter;
import com.maradroid.glagopedija.adapters.ListActivityAdapter;
import com.maradroid.glagopedija.api.CroatianComparator;
import com.maradroid.glagopedija.dataModels.Monument;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mara on 3/15/15.
 */
public class ListActivity extends BaseActivity implements ListActivityAdapter.ClickListener {

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListActivityAdapter mAdapter;

    private ArrayList<Monument> monumentArray;
    private String century;

    private AutoCompleteTextView searchBar;
    private LinearLayout llSearch;

    private boolean isSearching = false;

    private InputMethodManager keyboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_list);

        getExtra();
        initToolbar();
        initViews();
        initRecyclerView();
        initSearch();

    }

    private void getExtra() {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            century = extras.getString("stoljece", null);
            monumentArray = extras.getParcelableArrayList("monumentList");
            Collections.sort(monumentArray, new CroatianComparator());
        }
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_chevron_left_white_36dp);

        if (century != null) {
            getSupportActionBar().setTitle(century);
        }
    }

    private void initViews() {

        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        searchBar = (AutoCompleteTextView) findViewById(R.id.search);
        mRecycler = (RecyclerView) findViewById(R.id.list_view);
    }

    private void initRecyclerView() {

        if (monumentArray != null) {
            mRecycler.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecycler.setLayoutManager(mLayoutManager);
            mAdapter = new ListActivityAdapter(monumentArray);
            mAdapter.setClickListener(this);
            mRecycler.setAdapter(mAdapter);
        }
    }

    private void initSearch() {

        if (monumentArray != null) {
            keyboardManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            CustomSearchAdapter adapter = new CustomSearchAdapter(getApplicationContext(),R.layout.item_search, monumentArray);
            searchBar.setAdapter(adapter);

            setSearchClickListener();
        }
    }

    private void setSearchClickListener() {

        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Monument searchMonument = (Monument) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(ListActivity.this, MonumentInfoActivity.class);
                intent.putExtra("monument", searchMonument);

                if (keyboardManager.isAcceptingText()) {
                    keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                searchBar.setText("");
                llSearch.setAlpha(0);
                llSearch.setVisibility(View.GONE);

                startActivity(intent);

            }
        });
    }

    public void SearchButtonsClick(View v){

        int id = v.getId();

        if(id == R.id.close_search){

            llSearch.clearAnimation();
            llSearch.animate().alpha(0).setDuration(700).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    llSearch.setVisibility(View.GONE);
                }
            });
            searchBar.setText("");
            isSearching = false;

            if(keyboardManager.isAcceptingText()) {
                keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

        }else if(id == R.id.clear_search){

            searchBar.setText("");

        }else if(id == R.id.ll_search){

            llSearch.clearAnimation();
            llSearch.animate().alpha(0).setDuration(700).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    llSearch.setVisibility(View.GONE);
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

        Intent intent = new Intent(this, MonumentInfoActivity.class);
        intent.putExtra("monument", monumentArray.get(position));
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
            llSearch.clearAnimation();
            llSearch.animate().alpha(1).setDuration(700).setListener(null);
            llSearch.setVisibility(View.VISIBLE);
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
            llSearch.clearAnimation();
            llSearch.animate().alpha(0).setDuration(700).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    llSearch.setVisibility(View.GONE);
                }
            });

            searchBar.setText("");
            isSearching = false;

        }else{
            super.onBackPressed();
        }

    }
}
