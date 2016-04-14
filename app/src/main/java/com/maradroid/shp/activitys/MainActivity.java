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
import com.maradroid.shp.adapters.RecyclerViewAdapter;
import com.maradroid.shp.api.ApiSingleton;
import com.maradroid.shp.api.SpomenikEvent;
import com.maradroid.shp.dataModels.Spomenik;
import com.maradroid.shp.dataModels.Stoljece;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements RecyclerViewAdapter.ClickListener, SpomenikEvent {

    private static final int SEARCH_ITEM_TAG = -2;

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;

    private ArrayList<Stoljece> listaStoljeca;
    private ArrayList<Spomenik> searchArray;

    private AutoCompleteTextView searchBar;
    private LinearLayout search_ll;

    private boolean isSearching;
    private boolean isSearchSet;

    private InputMethodManager keyboardManager;

    private Spomenik searchSpomenik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiSingleton.getNewInstance().getJSON(getApplicationContext());

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

        search_ll = (LinearLayout) findViewById(R.id.search_ll);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        searchBar = (AutoCompleteTextView) findViewById(R.id.search);

    }

    private void initRecyclerView() {

        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(listaStoljeca);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    private void initRecyclerData() {

        listaStoljeca = new ArrayList<Stoljece>();
        listaStoljeca.add(new Stoljece("11. stoljeće", "11", "Pariški abecedarij...", R.mipmap.jedanaest_edited));
        listaStoljeca.add(new Stoljece("12. stoljeće", "12", "Bašćanska ploča...", R.mipmap.dvanaest_edited));
        listaStoljeca.add(new Stoljece("13. stoljeće", "13", "Vinodolski zakon...", R.mipmap.trinaest_edited));
        listaStoljeca.add(new Stoljece("14. stoljeće", "14", "Pašmanski brevijar...", R.mipmap.cetrnaest_edited));
        listaStoljeca.add(new Stoljece("15. stoljeće", "15", "Lička listina...", R.mipmap.petnaest_edited));
        listaStoljeca.add(new Stoljece("16. stoljeće", "16", "Klimantovićev ritual...", R.mipmap.sesnaest_edited));
        listaStoljeca.add(new Stoljece("17. stoljeće", "17", "Fatevićev zbornik...", R.mipmap.sedamnaest_edited));
        listaStoljeca.add(new Stoljece("18. stoljeće", "18", "Karta sv. Bonifacija...", R.mipmap.osamnaest_edited));
        listaStoljeca.add(new Stoljece("19. stoljeće", "19", "Čini i pravilo misli...", R.mipmap.devetnaest_edited));
        listaStoljeca.add(new Stoljece("20. stoljeće", "20", "Rimski misal slověnskim jezikom...", R.mipmap.dvadeset_edited));
    }

    private void initSearch() {

        keyboardManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //searchArray = new ArrayList<Spomenik>();

        CustomSearchAdapter adapter = new CustomSearchAdapter(getApplicationContext(),R.layout.search_item, ApiSingleton.getInstance().getSpomenikArray());
        searchBar.setAdapter(adapter);

        setSearchClickListener();

    }

    private void setSearchClickListener() {

        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                searchSpomenik = (Spomenik) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this, SpomenikInfo.class);
                intent.putExtra("position", SEARCH_ITEM_TAG);

                if (keyboardManager.isAcceptingText()) {
                    keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                searchBar.setText("");
                search_ll.setAlpha(0);
                search_ll.setVisibility(View.GONE);

                ApiSingleton.getInstance().setSpomenikEvent(MainActivity.this);

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

        if (ApiSingleton.getInstance().isDataReady()) {
            Intent intent = new Intent(this, ListViewActivity.class);
            intent.putExtra("tag", listaStoljeca.get(position).getStoljeceTag());
            intent.putExtra("stoljece", listaStoljeca.get(position).getCardStoljece());
            startActivity(intent);

        } else {

            Toast.makeText(this, "Podatci nisu spremni, pokušajte ponovno", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.search_icon) {

            if (ApiSingleton.getInstance().isDataReady() && !isSearchSet) {
                initSearch();
                isSearchSet = true;
            }

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

        if (id == R.id.literatura) {
            Intent intent = new Intent(this, LiteraturaActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.o_aplikaciji) {
            Intent intent = new Intent(this, OAplikcijiActivity.class);
            intent.putExtra("activity","O aplikaciji");
            startActivity(intent);
            return true;
        }

        if (id == R.id.o_nama) {
            Intent intent = new Intent(this, OAplikcijiActivity.class);
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
    protected void onResume() {
        super.onResume();

        ApiSingleton.getInstance().removeSpomenikEvent();

        if (ApiSingleton.getInstance().isDataReady() && !isSearchSet) {
            initSearch();
            isSearchSet = true;
        }
    }


    @Override
    public Spomenik getSpomenikById(int position) {

        if (position == SEARCH_ITEM_TAG && searchSpomenik != null) {
            return searchSpomenik;
        }

        return null;
    }
}
