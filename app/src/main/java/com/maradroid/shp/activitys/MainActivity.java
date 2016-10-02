package com.maradroid.shp.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import com.maradroid.shp.R;
import com.maradroid.shp.adapters.CustomSearchAdapter;
import com.maradroid.shp.adapters.MainActivityAdapter;
import com.maradroid.shp.api.JsonParser;
import com.maradroid.shp.api.ParserListener;
import com.maradroid.shp.dataModels.Monument;
import com.maradroid.shp.dataModels.Century;
import java.util.ArrayList;



public class MainActivity extends BaseActivity implements MainActivityAdapter.ClickListener, ParserListener {

    private static final int SEARCH_ITEM_TAG = -2;

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainActivityAdapter mAdapter;

    private ArrayList<Century> centuriesArray;

    private AutoCompleteTextView searchBar;
    private LinearLayout llSearch;

    private boolean isSearching;

    private InputMethodManager keyboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JsonParser().parse(this);
        initToolbar();


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
        mAdapter = new MainActivityAdapter(centuriesArray, this);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    private void initSearch(ArrayList<Century> centuryList) {

        ArrayList<Monument> monuments = new ArrayList<>();

        for (Century century : centuryList) {
            monuments.addAll(century.getMonumentList());
        }

        keyboardManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        CustomSearchAdapter adapter = new CustomSearchAdapter(getApplicationContext(), R.layout.item_search, monuments);
        searchBar.setAdapter(adapter);

        setSearchClickListener();

    }

    private void setSearchClickListener() {

        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Monument searchMonument = (Monument) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this, MonumentInfoActivity.class);
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
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("stoljece", centuriesArray.get(position).getCentury());
        intent.putParcelableArrayListExtra("monumentList", centuriesArray.get(position).getMonumentList());
        startActivity(intent);
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
    public void onParseDone(final ArrayList<Century> centuryList) {

        initViews();
        initSearch(centuryList);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (centuryList != null) {
                    //Type type = new TypeToken<List<Monument>>() {}.getType();
                    //String sortedJson = new Gson().toJson(monumentList, type);

                    centuriesArray = centuryList;
                    initRecyclerView();
                    Log.e("maradroid", "data ok!");
                }
            }
        });
    }
}
