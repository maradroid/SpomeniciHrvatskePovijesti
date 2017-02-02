package com.maradroid.glagopedija.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maradroid.glagopedija.R;
import com.maradroid.glagopedija.fragments.AboutFragment;

/**
 * Created by mara on 1/8/17.
 */

public class AboutAppAdapter extends FragmentPagerAdapter {

    private String[] tabNames  = {"Glagoljica","Ćirilica"};

    public AboutAppAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return AboutFragment.newInstance(R.string.about_app_glagoljica);

            default:
                return AboutFragment.newInstance(R.string.about_app_cirilica);
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
