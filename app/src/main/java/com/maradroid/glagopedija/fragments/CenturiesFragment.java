package com.maradroid.glagopedija.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maradroid.glagopedija.R;
import com.maradroid.glagopedija.activitys.ListActivity;
import com.maradroid.glagopedija.adapters.MainActivityAdapter;
import com.maradroid.glagopedija.dataModels.Century;

import java.util.ArrayList;

/**
 * Created by mara on 10/13/16.
 */

public class CenturiesFragment extends Fragment implements MainActivityAdapter.ClickListener{

    private static final String CENTURIES = "centuries";

    private RecyclerView recyclerView;

    private ArrayList<Century> centuriesArray;

    public static CenturiesFragment newInstance(ArrayList<Century> centuries) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(CENTURIES, centuries);

        CenturiesFragment fragment = new CenturiesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        centuriesArray = getArguments().getParcelableArrayList(CENTURIES);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    private void initRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        Configuration configuration = this.getResources().getConfiguration();
        final int screenWidthDp = configuration.screenWidthDp;
        MainActivityAdapter adapter = new MainActivityAdapter(centuriesArray, getActivity(), screenWidthDp);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (screenWidthDp >= 600) {
                    return 1;

                } else {
                    return 2;
                }
            }
        });
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("stoljece", centuriesArray.get(position).getCentury());
        intent.putParcelableArrayListExtra("monumentList", centuriesArray.get(position).getMonumentList());
        startActivity(intent);
    }
}
