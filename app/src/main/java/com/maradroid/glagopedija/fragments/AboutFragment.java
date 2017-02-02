package com.maradroid.glagopedija.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maradroid.glagopedija.R;

/**
 * Created by mara on 1/8/17.
 */

public class AboutFragment extends Fragment {

    public static final String STRING_ID = "stringId";

    private int stringId = -1;

    public static AboutFragment newInstance(int stringId) {

        Bundle args = new Bundle();
        args.putInt(STRING_ID, stringId);
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stringId = getArguments().getInt(STRING_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_about);
        textView.setText(getString(stringId));
        return view;
    }
}
