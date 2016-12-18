package com.maradroid.glagopedija.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maradroid.glagopedija.dataModels.Century;
import com.maradroid.glagopedija.fragments.CenturiesFragment;

import java.util.ArrayList;

/**
 * Created by mara on 10/13/16.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private String[] tabNames  = {"Glagoljica","Ćirilica"};

    private ArrayList<Century> glagoljicaArray;
    private ArrayList<Century> cirilicaArray;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Century> glagoljicaArray, ArrayList<Century> cirilicaArray) {
        super(fm);
        this.glagoljicaArray = glagoljicaArray;
        this.cirilicaArray = cirilicaArray;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return CenturiesFragment.newInstance(glagoljicaArray);

            default:
                return CenturiesFragment.newInstance(cirilicaArray);
        }
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
