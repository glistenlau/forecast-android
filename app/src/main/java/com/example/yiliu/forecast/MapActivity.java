package com.example.yiliu.forecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;

import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (findViewById(R.id.map_container) != null) {
            InteractiveMapFragment mapFragment = new InteractiveMapFragment();

            mapFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction().add(R.id.map_container, mapFragment).commit();
        }
    }
}
