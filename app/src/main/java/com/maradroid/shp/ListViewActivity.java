package com.maradroid.shp;

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
import android.widget.TextView;
import java.text.Collator;
import java.util.ArrayList;
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
    private ArrayList<Spomenik> spomenikArray;
    private Intent intent;
    private Map<String, Integer> map;
    private List<String> listaSpomenika;
    private String stoljece, tag;
    private AutoCompleteTextView searchBar;
    private LinearLayout search_ll;
    private boolean isSearching = false;
    private InputMethodManager keyboardManager;

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

        search_ll = (LinearLayout) findViewById(R.id.search_ll);
        keyboardManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        searchBar = (AutoCompleteTextView) findViewById(R.id.search);

        spomenikArray = new ArrayList<Spomenik>();
        spomenikArray = ApiSingleton.getInstance().stoljeceMap.get(tag);

        map = new HashMap<String, Integer>();
        listaSpomenika = new ArrayList<String>();

        for(int i = 0; i < spomenikArray.size(); i++){
            map.put(spomenikArray.get(i).ime, i);
            listaSpomenika.add(spomenikArray.get(i).ime);
        }

        Collections.sort(listaSpomenika, Collator.getInstance(new Locale("hr_HR")));

        mRecycler = (RecyclerView) findViewById(R.id.list_view);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new ListViewAdapter(listaSpomenika);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);

        CustomSearchAdapter adapter = new CustomSearchAdapter(getApplicationContext(),R.layout.search_item, spomenikArray);
        searchBar.setAdapter(adapter);

        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //LinearLayout ll = (LinearLayout) view;
                Spomenik tempObject = (Spomenik) adapterView.getItemAtPosition(i);
                ArrayList<Spomenik> tempArray = ApiSingleton.getInstance().stoljeceMap.get(tempObject.stoljece);

                for (int j = 0; j < tempArray.size(); j++) {
                    if (tempArray.get(j).id.equals(tempObject.id)) {
                        Intent intent = new Intent(ListViewActivity.this, SpomenikInfo.class);
                        intent.putExtra("stoljece", stoljece);
                        intent.putExtra("tag", tag);
                        intent.putExtra("position", j);

                        if (keyboardManager.isAcceptingText()) {
                            keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }

                        searchBar.setText("");
                        search_ll.setAlpha(0);
                        search_ll.setVisibility(View.GONE);

                        startActivity(intent);


                        break;
                    }
                }
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
    public void onClick(View v, int position, boolean isLongClick) {

        LinearLayout ll = (LinearLayout) v;
        TextView tv = (TextView) ll.getChildAt(0);

        intent = new Intent(this, SpomenikInfo.class);
        intent.putExtra("position", map.get(tv.getText()));
        intent.putExtra("tag", tag);
        intent.putExtra("stoljece", stoljece);
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
}
