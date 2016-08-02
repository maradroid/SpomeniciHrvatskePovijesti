package com.maradroid.shp.activitys;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maradroid.shp.R;
import com.maradroid.shp.api.ApiSingleton;
import com.maradroid.shp.dataModels.MapPointer;

import java.util.ArrayList;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<MapPointer> pointersArray;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getData();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getData() {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id = extras.getString("id", null);
        }

        pointersArray = ApiSingleton.getInstance().getPointersArray();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (id != null && pointersArray != null && pointersArray.size() != 0) {

            for (MapPointer pointer : pointersArray) {

                if (id.equals(pointer.getId())) {
                    setMarker(pointer, BitmapDescriptorFactory.HUE_RED, true);

                } else {
                    setMarker(pointer, BitmapDescriptorFactory.HUE_AZURE, false);
                }
            }
        } else {
            Toast.makeText(MapsActivity.this, getResources().getString(R.string.maps_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void setMarker(MapPointer pointer, float color, boolean setCamera) {

        LatLng point = new LatLng(pointer.getLatitude(), pointer.getLongitude());

        Marker marker = mMap.addMarker(new MarkerOptions().position(point).title(pointer.getName()).icon(BitmapDescriptorFactory.defaultMarker(color)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        if (setCamera) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            marker.showInfoWindow();
        }
    }

}
