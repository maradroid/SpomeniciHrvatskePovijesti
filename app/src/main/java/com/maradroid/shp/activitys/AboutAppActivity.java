package com.maradroid.shp.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maradroid.shp.R;

/**
 * Created by mara on 9/24/15.
 */
public class AboutAppActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContent();
        initToolbar();

    }

    private void setContent() {

        String extra = getExtra();

        if (extra != null) {

            if (extra.equals("O aplikaciji")) {
                setContentView(R.layout.activity_about_app);

            } else {
                setContentView(R.layout.activity_about_us);
            }
        }
    }

    private String getExtra() {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            return extras.getString("activity", null);
        }

        return null;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("activity"));
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_chevron_left_white_36dp);
    }

    public void Link(View v){

        int id = v.getId();

        if(id == R.id.etfos) {

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.etfos.unios.hr/"));
            startActivity(i);

        }else if(id == R.id.ffos) {

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://web.ffos.hr/"));
            startActivity(i);

        }else if(id == R.id.vera) {

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ffos.unios.hr/hrvatski/vera-blazevic-krezic-znanstvena-novakinja"));
            startActivity(i);

        }else if(id == R.id.milica) {

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ffos.unios.hr/hrvatski/izv-prof-dr-sc-milica-lukic"));
            startActivity(i);

        }else if(id == R.id.balen) {

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.etfos.unios.hr/fakultet/imenik-djelatnika/jbalen"));
            startActivity(i);

        }

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
