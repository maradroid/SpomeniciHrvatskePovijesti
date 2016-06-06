package com.maradroid.shp.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.Toast;

import com.maradroid.shp.R;
import com.maradroid.shp.adapters.CustomSearchAdapter;
import com.maradroid.shp.adapters.MainActivityAdapter;
import com.maradroid.shp.api.ApiSingleton;
import com.maradroid.shp.api.MonumentEvent;
import com.maradroid.shp.dataModels.Monument;
import com.maradroid.shp.dataModels.Century;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements MainActivityAdapter.ClickListener, MonumentEvent {

    private static final int SEARCH_ITEM_TAG = -2;

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainActivityAdapter mAdapter;

    private ArrayList<Century> centuriesArray;

    private AutoCompleteTextView searchBar;
    private LinearLayout llSearch;

    private boolean isSearching;
    private boolean isSearchSet;

    private InputMethodManager keyboardManager;

    private Monument searchMonument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiSingleton.getInstance().getJSON(getApplicationContext());

        initToolbar();
        initViews();
        initRecyclerData();
        initRecyclerView();

        if (ApiSingleton.getInstance().isDataReady()) {
            initSearch();
            isSearchSet = true;
        }


    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void initViews() {

        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        searchBar = (AutoCompleteTextView) findViewById(R.id.search);

    }

    private void initRecyclerView() {

        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new MainActivityAdapter(centuriesArray);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    private void initRecyclerData() {

        centuriesArray = new ArrayList<Century>();
        centuriesArray.add(new Century("11. stoljeće", "11", "Pariški abecedarij...", R.mipmap.jedanaest_edited));
        centuriesArray.add(new Century("12. stoljeće", "12", "Bašćanska ploča...", R.mipmap.dvanaest_edited));
        centuriesArray.add(new Century("13. stoljeće", "13", "Vinodolski zakon...", R.mipmap.trinaest_edited));
        centuriesArray.add(new Century("14. stoljeće", "14", "Pašmanski brevijar...", R.mipmap.cetrnaest_edited));
        centuriesArray.add(new Century("15. stoljeće", "15", "Lička listina...", R.mipmap.petnaest_edited));
        centuriesArray.add(new Century("16. stoljeće", "16", "Klimantovićev ritual...", R.mipmap.sesnaest_edited));
        centuriesArray.add(new Century("17. stoljeće", "17", "Fatevićev zbornik...", R.mipmap.sedamnaest_edited));
        centuriesArray.add(new Century("18. stoljeće", "18", "Karta sv. Bonifacija...", R.mipmap.osamnaest_edited));
        centuriesArray.add(new Century("19. stoljeće", "19", "Čini i pravilo misli...", R.mipmap.devetnaest_edited));
        centuriesArray.add(new Century("20. stoljeće", "20", "Rimski misal slověnskim jezikom...", R.mipmap.dvadeset_edited));
    }

    private void initSearch() {

        keyboardManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        CustomSearchAdapter adapter = new CustomSearchAdapter(getApplicationContext(),R.layout.item_search, ApiSingleton.getInstance().getMonumentArray());
        searchBar.setAdapter(adapter);

        setSearchClickListener();

    }

    private void setSearchClickListener() {

        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                searchMonument = (Monument) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this, MonumentInfoActivity.class);
                intent.putExtra("position", SEARCH_ITEM_TAG);

                if (keyboardManager.isAcceptingText()) {
                    keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                searchBar.setText("");
                llSearch.setAlpha(0);
                llSearch.setVisibility(View.GONE);

                ApiSingleton.getInstance().setMonumentEvent(MainActivity.this);

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

        if (ApiSingleton.getInstance().isDataReady()) {
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("tag", centuriesArray.get(position).getCenturyTag());
            intent.putExtra("stoljece", centuriesArray.get(position).getCardCentury());
            startActivity(intent);

        } else {

            Toast.makeText(this, getResources().getString(R.string.data_not_ready), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.search_icon) {

            if (ApiSingleton.getInstance().isDataReady() && !isSearchSet) {
                initSearch();
                isSearchSet = true;
            }

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

        if (id == R.id.literatura) {
            Intent intent = new Intent(this, LiteratureActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.o_aplikaciji) {
            Intent intent = new Intent(this, AboutAppActivity.class);
            intent.putExtra("activity","O aplikaciji");
            startActivity(intent);
            return true;
        }

        if (id == R.id.o_nama) {
            Intent intent = new Intent(this, AboutAppActivity.class);
            intent.putExtra("activity","O nama");
            startActivity(intent);
            return true;
        }

        if (id == R.id.translate) {
            Intent intent = new Intent(this, TranslateActivity.class);
            startActivity(intent);
            return true;
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

    @Override
    protected void onResume() {
        super.onResume();

        ApiSingleton.getInstance().removeMonumentEvent();

        if (ApiSingleton.getInstance().isDataReady() && !isSearchSet) {
            initSearch();
            isSearchSet = true;
        }
    }


    @Override
    public Monument getMonumentById(int position) {

        if (position == SEARCH_ITEM_TAG && searchMonument != null) {
            return searchMonument;
        }

        return null;
    }
}
