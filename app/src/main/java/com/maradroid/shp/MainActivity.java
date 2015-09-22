package com.maradroid.shp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements RecyclerViewAdapter.ClickListener {

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<Stoljece> listaStoljeca = new ArrayList<Stoljece>();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        listaStoljeca.add(new Stoljece("11. stoljeće", "stoljece_11", "Pariški abecedarij...", R.mipmap.jedanaest));
        listaStoljeca.add(new Stoljece("12. stoljeće", "stoljece_12", "Bašćanska ploča...", R.mipmap.bascanska_plocaxx));
        listaStoljeca.add(new Stoljece("13. stoljeće","stoljece_13","Vinodolski zakon...",R.mipmap.vinodol3x));
        listaStoljeca.add(new Stoljece("14. stoljeće","stoljece_14","Pašmanski brevijar...",R.mipmap.cetrnaest));
        listaStoljeca.add(new Stoljece("15. stoljeće","stoljece_15","Lička listina...",R.mipmap.petnaest));
        listaStoljeca.add(new Stoljece("16. stoljeće","stoljece_16","Klimantovićev ritual...",R.mipmap.sesnaest));
        listaStoljeca.add(new Stoljece("17. stoljeće","stoljece_17","Fatevićev zbornik...",R.mipmap.sedamnaest));
        listaStoljeca.add(new Stoljece("18. stoljeće","stoljece_18","Karta sv. Bonifacija...",R.mipmap.osamnaest));
        listaStoljeca.add(new Stoljece("19. stoljeće","stoljece_19","Čini i pravilo misli...",R.mipmap.devetnaest));
        listaStoljeca.add(new Stoljece("20. stoljeće","stoljece_20","Rimski misal slověnskim jezikom...",R.mipmap.dvadeset));

        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(listaStoljeca);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);


    }

    @Override
    public void onClick(View v, int position) {

            intent = new Intent(this, ListViewActivity.class);
            intent.putExtra("tag",listaStoljeca.get(position).getStoljeceTag());
            startActivity(intent);
        Log.e("maradroid", "imageHeight: " + mAdapter.Mara().getHeight());
        Log.e("maradroid", "imageWidth: " + mAdapter.Mara().getWidth());
    }


    /*@Override
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
