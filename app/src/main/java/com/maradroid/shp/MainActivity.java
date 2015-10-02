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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements RecyclerViewAdapter.ClickListener {

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<Stoljece> listaStoljeca;
    private ArrayList<Spomenik> searchArray;
    private Intent intent;
    private AutoCompleteTextView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
//json_adaption branch
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

        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(listaStoljeca);
        mAdapter.setClickListener(this);
        mRecycler.setAdapter(mAdapter);

        ApiSingleton.getNewInstance().getJSON(getApplicationContext());

        searchArray = (ArrayList<Spomenik>) ApiSingleton.getInstance().spomenikArray.clone();

        searchBar = (AutoCompleteTextView) findViewById(R.id.search);
        /*ArrayList<Test> test = new ArrayList<Test>();
        test.add(new Test("jedan","1"));
        test.add(new Test("jeden","12"));
        test.add(new Test("dva","2"));
        test.add(new Test("tri","3"));
        test.add(new Test("jedan dva","4"));
        test.add(new Test("tri cetiri pet","5"));*/
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, test);
        CustomSearchAdapter adapter = new CustomSearchAdapter(getApplicationContext(),R.layout.search_item, searchArray);
        searchBar.setAdapter(adapter);

        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //LinearLayout ll = (LinearLayout) view;
                TextView tv = (TextView) view;
                Log.e("maradroid", "" + tv.getText());
            }
        });

    }

    @Override
    public void onClick(View v, int position) {

            intent = new Intent(this, ListViewActivity.class);
            intent.putExtra("tag", listaStoljeca.get(position).getStoljeceTag());
            intent.putExtra("stoljece", listaStoljeca.get(position).getCardStoljece());
            startActivity(intent);
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

        //noinspection SimplifiableIfStatement
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

        return super.onOptionsItemSelected(item);
    }
}
