package com.example.yiliu.forecast;

// import the AerisMapView & components
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.model.AerisResponse;

public class InteractiveMapFragment extends MapViewFragment implements AerisCallback
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
        // setting up secret key and client id for oauth to aeris
        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id),
                this.getString(R.string.aeris_client_secret), getActivity());
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);

        moveToLocation();

        return view;
    }

    @Override
    public void onResult(EndpointType endpointType, AerisResponse aerisResponse) {

    }

    private void moveToLocation() {
        double lat = Double.parseDouble(getArguments().getString("latitude"));
        double lon = Double.parseDouble(getArguments().getString("longitude"));

        mapView.moveToLocation(new LatLng(lat, lon), 9);
    }
}
