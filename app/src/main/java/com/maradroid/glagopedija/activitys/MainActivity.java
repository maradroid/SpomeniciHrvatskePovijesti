package com.maradroid.glagopedija.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.maradroid.glagopedija.adapters.ViewPagerAdapter;
import com.maradroid.glagopedija.api.JsonParser;
import com.maradroid.glagopedija.api.ParserListener;
import com.maradroid.glagopedija.dataModels.Monument;
import com.maradroid.glagopedija.dataModels.Century;
import java.util.ArrayList;



public class MainActivity extends BaseActivity implements ParserListener {

    private ArrayList<Century> glagoljicaArray;
    private ArrayList<Century> cirilicaArray;
    private ArrayList<Century> searchArray;

    private ViewPager viewPager;
    private AutoCompleteTextView searchBar;
    private LinearLayout llSearch;

    private TabLayout tabLayout;

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
        searchBar = (AutoCompleteTextView) findViewById(R.id.search);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setupViewPager();
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), glagoljicaArray, cirilicaArray);
        viewPager.setAdapter(adapter);
        setupTabLayout();
    }

    private void setupTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
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
            startActivity(intent);
            return true;
        }

        if (id == R.id.o_nama) {
            Intent intent = new Intent(this, AboutUsActivity.class);
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
    public void onParseDone(final ArrayList<Century> glagoljicaList, final ArrayList<Century> cirilicaList) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (glagoljicaList != null && cirilicaList != null) {
                    glagoljicaArray = glagoljicaList;
                    cirilicaArray = cirilicaList;

                    searchArray = new ArrayList<>();
                    searchArray.addAll(glagoljicaList);
                    searchArray.addAll(cirilicaList);

                    initViews();
                    initSearch(searchArray);
                }
            }
        });
    }
}
